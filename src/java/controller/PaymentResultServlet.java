package controller;

import dal.*;
import models.*;
import utils.VnPayUtil;
import utils.EmailUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class PaymentResultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> fields = new HashMap<>();
        for (String key : req.getParameterMap().keySet()) {
            fields.put(key, req.getParameter(key));
        }

        String vnp_HashSecret = "ICZ6BNCMQGJ03JQ2AZK56D2D95IROPF0";
        String vnp_SecureHash = req.getParameter("vnp_SecureHash");
        fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        try {
            String rawData = VnPayUtil.getRawData(fields);
            String myHash = VnPayUtil.hmacSHA512(vnp_HashSecret, rawData);

            if (myHash.equalsIgnoreCase(vnp_SecureHash)) {
                String responseCode = req.getParameter("vnp_ResponseCode");
                String orderCode = req.getParameter("vnp_TxnRef");

                if ("00".equals(responseCode)) {
                    OrderDAO orderDAO = new OrderDAO();
                    TicketDAO ticketDAO = new TicketDAO();

                    // Cập nhật trạng thái đơn và vé
                    orderDAO.updateOrderStatusByOrderCode(orderCode, "paid");
                    ticketDAO.updateStatusByOrderCode(orderCode, "paid");

                    Order order = orderDAO.getByOrderCode(orderCode);

                    // Gửi email xác nhận
                    List<Ticket> tickets = ticketDAO.getTicketsByOrderId(order.getId());
                    List<String> ticketCodes = new ArrayList<>();
                    for (Ticket t : tickets) ticketCodes.add(t.getTicketCode());

                    EmailUtil.sendOrderSuccess(
                        order.getEmail(),
                        order.getOrderCode(),
                        ticketCodes,
                        order.getOriginalAmount(),
                        order.getDiscountAmount(),
                        order.getOriginalAmount() - order.getDiscountAmount()
                    );

                    // Lấy danh sách vé chi tiết để hiển thị
                    List<TicketDetail> ticketDetails = ticketDAO.getTicketsByOrderId2(order.getId());

                    req.setAttribute("order", order);
                    req.setAttribute("tickets", ticketDetails);
                    req.setAttribute("success", true);
                } else {
                    req.setAttribute("success", false);
                    req.setAttribute("message", "Thanh toán thất bại! Mã lỗi: " + responseCode);
                }
            } else {
                req.setAttribute("success", false);
                req.setAttribute("message", "Sai mã xác thực trả về từ VNPay!");
            }
        } catch (Exception ex) {
            req.setAttribute("success", false);
            req.setAttribute("message", "Lỗi xử lý thanh toán: " + ex.getMessage());
        }

        req.getRequestDispatcher("paymentResult.jsp").forward(req, resp);
    }
}
