package controller;

import dal.*;
import models.*;
import utils.VnPayUtil;
import utils.EmailUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class PlaceOrderServlet extends HttpServlet {
    private static final String VNP_TMNCODE = "QLU5NB23";
    private static final String VNP_HASHSECRET = "ICZ6BNCMQGJ03JQ2AZK56D2D95IROPF0";
    private static final String VNP_RETURN_URL = "http://localhost:9999/TrainTicketBooking/paymentResult";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        int ticketCount = Integer.parseInt(req.getParameter("ticketCount"));
        String buyerName = req.getParameter("buyerName");
        String buyerEmail = req.getParameter("buyerEmail");
        String buyerPhone = req.getParameter("buyerPhone");
        String promotionCode = req.getParameter("promotionCode");
        String paymentMethod = req.getParameter("paymentMethod");

        // 1. Sinh mã đơn hàng
        String orderCode = generateOrderCode();
        Order order = new Order();
        order.setOrderCode(orderCode);
        User user = (User) req.getSession().getAttribute("user");
        order.setUserId(user != null ? user.getId() : null);
        order.setEmail(buyerEmail);
        order.setPhoneNumber(buyerPhone);
        order.setOrderTime(new java.sql.Timestamp(System.currentTimeMillis()).toString());
        order.setStatus("pending"); // Đúng với constraint
        order.setIsDeleted(false);

        // 2. Chuẩn bị list vé
        double totalPrice = 0;
        List<Ticket> ticketList = new ArrayList<>();
        for (int i = 0; i < ticketCount; i++) {
            Ticket t = new Ticket();
            t.setTicketCode(generateTicketCode());
            t.setOrderId(null); // set sau khi insert order

            // Parse int an toàn và log dữ liệu
            int scheduleId = parseIntSafe(req.getParameter("scheduleId" + i));
            int depStopId = parseIntSafe(req.getParameter("departureStopId" + i));
            int arrStopId = parseIntSafe(req.getParameter("arrivalStopId" + i));
            int seatId = parseIntSafe(req.getParameter("seatId" + i));
            int passengerTypeId = parseIntSafe(req.getParameter("passengerTypeId" + i));

            t.setScheduleId(scheduleId);
            t.setDepartureStopId(depStopId);
            t.setArrivalStopId(arrStopId);
            t.setSeatId(seatId);
            t.setPassengerTypeId(passengerTypeId);
            t.setPassengerName(req.getParameter("passengerName" + i));
            t.setPassengerIdNumber(req.getParameter("passengerIdNumber" + i));
            t.setPassengerDateOfBirth(req.getParameter("passengerDateOfBirth" + i));
            t.setTicketStatus("pending_payment");
            t.setIsDeleted(false);

            // Lấy và chuẩn hóa giá vé
            String priceStr = req.getParameter("ticketPrice" + i);
            if (priceStr != null) {
                priceStr = priceStr.replaceAll("[^\\d.]", "");
                int idx = priceStr.indexOf('.');
                if (idx != priceStr.lastIndexOf('.')) {
                    String before = priceStr.substring(0, priceStr.lastIndexOf('.')).replace(".", "");
                    priceStr = before + priceStr.substring(priceStr.lastIndexOf('.'));
                }
            }
            double basePrice = 0;
            try { basePrice = Double.parseDouble(priceStr); } catch (Exception ex) { basePrice = 0; }

            // Áp dụng giảm giá loại khách
            PassengerTypeRuleRow rule = new PassengerTypeDAO().getRuleByTypeId(passengerTypeId);
            double price = basePrice;
            if (rule != null && rule.getDiscountValue() != null && Boolean.TRUE.equals(rule.getRuleActive())) {
                if ("percent".equalsIgnoreCase(rule.getDiscountType()) || "percentage".equalsIgnoreCase(rule.getDiscountType())) {
                    price = price * (100.0 - rule.getDiscountValue()) / 100.0;
                } else if ("fixed".equalsIgnoreCase(rule.getDiscountType())) {
                    price = price - rule.getDiscountValue();
                }
            }
            t.setPrice(Math.max(price, 0));
            totalPrice += Math.max(price, 0);

            // Log các trường
            System.out.println("Ticket row: scheduleId=" + scheduleId
                    + ", depStopId=" + depStopId
                    + ", arrStopId=" + arrStopId
                    + ", seatId=" + seatId
                    + ", passengerTypeId=" + passengerTypeId);

            // Validate dữ liệu trước khi add vào list
            if (scheduleId > 0 && depStopId > 0 && arrStopId > 0 && seatId > 0 && passengerTypeId > 0) {
                ticketList.add(t);
            } else {
                System.out.println("Invalid ticket info, bỏ qua vé này!");
            }
        }
        order.setOriginalAmount(totalPrice);
        order.setDiscountAmount(0);

        // 3. Áp dụng khuyến mãi nếu có
        if (promotionCode != null && !promotionCode.trim().isEmpty()) {
            Promotion promo = new PromotionDAO().getByCode(promotionCode.trim());
            if (promo != null && promo.getUsageLimit() > 0) {
                double promoDiscount = 0;
                if ("percent".equalsIgnoreCase(promo.getDiscountType())) {
                    promoDiscount = totalPrice * promo.getDiscountValue() / 100.0;
                } else if ("fixed".equalsIgnoreCase(promo.getDiscountType())) {
                    promoDiscount = promo.getDiscountValue();
                }
                promoDiscount = Math.min(promoDiscount, totalPrice);
                order.setDiscountAmount(promoDiscount);
                order.setOriginalAmount(totalPrice - promoDiscount);
                new PromotionDAO().incrementUsageCount(promo.getId());
            }
        }

        System.out.println("Begin insert");

        int orderId = new OrderDAO().insert(order);
        System.out.println("Order ID inserted: " + orderId);

        if (orderId <= 0) {
            System.out.println("Không tạo được đơn hàng, dừng lại!");
            req.setAttribute("message", "Không tạo được đơn hàng! Vui lòng thử lại.");
            req.getRequestDispatcher("checkout.jsp").forward(req, resp);
            return;
        }

        for (Ticket t : ticketList) {
            t.setOrderId(orderId);
            System.out.println("Insert ticket: scheduleId=" + t.getScheduleId() +
                ", seatId=" + t.getSeatId() +
                ", depStopId=" + t.getDepartureStopId() +
                ", arrStopId=" + t.getArrivalStopId() +
                ", orderId=" + t.getOrderId());
            int ticketId = new TicketDAO().insert(t);
            if (ticketId <= 0) {
                System.out.println("Insert ticket failed for seatId: " + t.getSeatId());
            }
        }
        System.out.println("Insert xong");

        // Redirect thanh toán VNPay
        if ("vnpay".equals(paymentMethod)) {
            String payUrl = VnPayUtil.buildPayUrl(order, VNP_TMNCODE, VNP_HASHSECRET, VNP_RETURN_URL);
            resp.sendRedirect(payUrl);
            return;
        } else {
            // Thanh toán COD
            new OrderDAO().updateStatus(orderId, "paid");
            for (Ticket t : ticketList) new TicketDAO().updateStatusByCode(t.getTicketCode(), "paid");
        }

        // Gửi email xác nhận
        List<String> ticketCodes = new ArrayList<>();
        for (Ticket t : ticketList) ticketCodes.add(t.getTicketCode());
        EmailUtil.sendOrderSuccess(buyerEmail, order.getOrderCode(), ticketCodes, order.getOriginalAmount(), order.getDiscountAmount(), (order.getOriginalAmount() - order.getDiscountAmount()));

        req.getSession().setAttribute("orderSuccess", true);
        req.getSession().setAttribute("orderCode", order.getOrderCode());
        resp.sendRedirect("paymentResult");
    }

    private String generateOrderCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) sb.append(chars.charAt(random.nextInt(chars.length())));
        return sb.toString();
    }
    private String generateTicketCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) sb.append(chars.charAt(random.nextInt(chars.length())));
        return sb.toString();
    }

    private int parseIntSafe(String value) {
        try { return Integer.parseInt(value); }
        catch (Exception ex) { return -1; }
    }
}
