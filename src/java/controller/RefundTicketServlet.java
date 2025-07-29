package controller;

import dal.OrderDAO;
import dal.RefundDAO;
import dal.RefundRuleDAO;
import dal.TicketDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import models.Order;
import models.Ticket;
import utils.EmailUtil;
import java.sql.*;

import java.io.IOException;
import models.RefundRule;

public class RefundTicketServlet extends HttpServlet {

    private final RefundDAO refundDAO = new RefundDAO();
    private final TicketDAO ticketDAO = new TicketDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String ticketCode = request.getParameter("ticketCode");
    TicketDAO ticketDAO = new TicketDAO();
    Ticket ticket = ticketDAO.getByCode(ticketCode);
    if (ticket == null || (!"paid".equals(ticket.getTicketStatus()) || !"pending-payment".equals(ticket.getTicketStatus()))) {
        request.setAttribute("error", "Vé không tồn tại hoặc không thể hoàn!");
        request.getRequestDispatcher("refundList.jsp").forward(request, response);
        return;
    }

    Timestamp departureTime = ticketDAO.getDepartureTime(ticketCode);
    int carriageTypeId = ticketDAO.getCarriageTypeId(ticketCode);
    RefundRule rule = new RefundRuleDAO().findApplicableRule(ticket.getPassengerTypeId(), carriageTypeId, departureTime);

    if (rule == null) {
        request.setAttribute("error", "Không tìm thấy quy tắc hoàn vé phù hợp.");
        request.getRequestDispatcher("refundList.jsp").forward(request, response);
        return;
    }

    double refundAmount = ticket.getPrice() * rule.getRefundPercentage();
    boolean success = new RefundDAO().refundTicket(ticket.getId(), -1, refundAmount, "Áp dụng rule ID " + rule.getId());

    if (success) {
        EmailUtil.sendRefundSuccess(ticketDAO.getEmailByTicketCode(ticketCode), ticketCode, refundAmount, ticket.getPassengerName());
        request.setAttribute("success", true);
    } else {
        request.setAttribute("error", "Hoàn vé thất bại!");
    }
    request.getRequestDispatcher("refundList.jsp").forward(request, response);
}

}
