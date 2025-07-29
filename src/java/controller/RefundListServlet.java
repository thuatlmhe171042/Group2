package controller;

import dal.OrderDAO;
import dal.TicketDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import models.Order;
import models.TicketDetail;

import java.io.IOException;
import java.util.List;

public class RefundListServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();
    private final TicketDAO ticketDAO = new TicketDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderCode = req.getParameter("orderCode").trim();
        String email = req.getParameter("email").trim();
        String phone = req.getParameter("phone").trim();

        Order order = orderDAO.findByCodeEmailPhone(orderCode, email, phone);
        if (order == null) {
            req.setAttribute("error", "Không tìm thấy đơn hàng phù hợp.");
        } else {
            List<TicketDetail> tickets = ticketDAO.getTicketsByOrderId2(order.getId());
            req.setAttribute("order", order);
            req.setAttribute("tickets", tickets);
        }

        req.getRequestDispatcher("refundList.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("refundList.jsp").forward(req, resp);
    }
}
