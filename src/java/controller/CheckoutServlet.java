package controller;

import dal.*;
import models.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cartData = req.getParameter("cartData");
        if (cartData == null || cartData.isEmpty()) {
            resp.sendRedirect("index.jsp");
            return;
        }
        // Parse JSON sang list vé
        Gson gson = new Gson();
        List<CartTicketInfo> cart = gson.fromJson(cartData, new TypeToken<List<CartTicketInfo>>(){}.getType());
        
        
        for (CartTicketInfo item : cart) {
    System.out.println("DEBUG: scheduleId=" + item.getScheduleId() +
        ", seatId=" + item.getSeatId() +
        ", fromStopId=" + item.getFromStopId() +
        ", toStopId=" + item.getToStopId() +
        ", price=" + item.getPrice());
}


        // Chuẩn bị list vé đủ trường
        List<TicketCheckoutRow> ticketList = new ArrayList<>();
        for (CartTicketInfo item : cart) {
            TicketCheckoutRow row = new TicketCheckoutRow();
            // --- Lấy/Set đủ các trường như bảng Tickets ---
            row.setScheduleId(item.getScheduleId());
            row.setDepartureStopId(item.getFromStopId());
            row.setArrivalStopId(item.getToStopId());
            row.setSeatId(item.getSeatId());
            row.setCarriageId(item.getCarriageId());
            row.setCarriageNumber(item.getCarriageNumber());
            row.setSeatNumber(item.getSeatNumber());
            row.setTrainCode(item.getTrainCode());
            row.setCarriageType(item.getCarriageType());
            row.setFromStationName(item.getFromStationName());
            row.setToStationName(item.getToStationName());
            row.setDepartTime(item.getDepartTime());
            row.setArrivalTime(item.getArrivalTime());
            row.setBasePrice(item.getPrice()); // lấy từ ScheduleSegmentPrices
            // --- Có thể bổ sung phụ phí ghế đặc biệt ---
            // --- Có thể lấy giá đã giảm nếu chọn sẵn loại khách ---
            ticketList.add(row);
        }

        // Lấy list loại khách + quy tắc giảm giá
        List<PassengerTypeRuleRow> passengerTypeRules = new PassengerTypeDAO().getAllWithRules();

        req.setAttribute("ticketList", ticketList);
        req.setAttribute("passengerTypeRules", passengerTypeRules);
        req.getRequestDispatcher("checkout.jsp").forward(req, resp);
    }
}
