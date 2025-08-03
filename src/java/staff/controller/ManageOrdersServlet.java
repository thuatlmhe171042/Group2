package staff.controller;

import dal.*;
import models.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class ManageOrdersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String status = request.getParameter("status");

        // Đúng field mapping với bảng Orders
        List<Order> orders = new OrderDAO().search(keyword, status);

        // Mapping các vé theo từng đơn hàng
        TicketDAO ticketDAO = new TicketDAO();
        Map<Integer, List<Ticket>> ticketMap = new HashMap<>();
        for (Order order : orders) {
            ticketMap.put(order.getId(), ticketDAO.getTicketsByOrderId(order.getId()));
        }
        request.setAttribute("orders", orders);
        request.setAttribute("ticketMap", ticketMap);
        request.getRequestDispatcher("/staff/manageOrders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User staff = (User) (session != null ? session.getAttribute("user") : null);
        String ip = request.getRemoteAddr();
        String action = request.getParameter("action");

        if ("cancelOrder".equals(action)) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            new OrderDAO().updateStatus(orderId, "cancelled");
            AuditLogDAO.addLog(staff.getId(), "UPDATE", "Orders", orderId, "Hủy đơn hàng " + orderId, ip);

        } else if ("cancelTicket".equals(action)) {
            int ticketId = Integer.parseInt(request.getParameter("ticketId"));
            new TicketDAO().cancelTicket(ticketId);
            AuditLogDAO.addLog(staff.getId(), "UPDATE", "Tickets", ticketId, "Hủy vé " + ticketId, ip);
        }
        response.sendRedirect("manageOrders");
    }
}
