package controller;

import dal.OrderDAO;
import dal.TicketDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import models.Order;
import models.TicketDetail;

public class ListOrderServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();
    private TicketDAO ticketDAO = new TicketDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("orderList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderCode = request.getParameter("orderCode");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        Order order = orderDAO.findByCodeEmailPhone(orderCode, email, phone);

        if (order == null) {
            request.setAttribute("error", "Không tìm thấy đơn hàng phù hợp!");
            request.getRequestDispatcher("orderList.jsp").forward(request, response);
        } else {
            List<TicketDetail> tickets = ticketDAO.getTicketsByOrderId2(order.getId());

            request.setAttribute("order", order);
            request.setAttribute("tickets", tickets);
            request.getRequestDispatcher("orderList.jsp").forward(request, response);
        }
    }
}
