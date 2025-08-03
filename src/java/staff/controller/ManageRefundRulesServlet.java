package staff.controller;

import dal.RefundRuleDAO;
import dal.AuditLogDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import models.RefundRule;
import models.User;

public class ManageRefundRulesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<RefundRule> rules = new RefundRuleDAO().getAllActiveRules();
        request.setAttribute("rules", rules);
        request.getRequestDispatcher("/staff/manageRefundRules.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User staff = (User) (session != null ? session.getAttribute("user") : null);
        String ip = request.getRemoteAddr();

        if (staff == null || !"staff".equals(staff.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        RefundRuleDAO dao = new RefundRuleDAO();

        if ("add".equals(action)) {
            RefundRule rule = new RefundRule();
            rule.setRuleName(request.getParameter("ruleName"));
            rule.setApplyBeforeHours(Integer.parseInt(request.getParameter("applyBeforeHours")));
            rule.setRefundPercentage(Double.parseDouble(request.getParameter("refundPercentage")));
            String ptId = request.getParameter("applyToPassengerTypeId");
            String ctId = request.getParameter("applyToCarriageTypeId");
            rule.setApplyToPassengerTypeId(ptId != null && !ptId.isEmpty() ? Integer.parseInt(ptId) : null);
            rule.setApplyToCarriageTypeId(ctId != null && !ctId.isEmpty() ? Integer.parseInt(ctId) : null);
            rule.setNote(request.getParameter("note"));
            rule.setActive(true);

            int id = dao.add(rule);
            AuditLogDAO.addLog(staff.getId(), "ADD", "RefundRules", id,
                    "Thêm quy tắc hoàn tiền: " + rule.getRuleName(), ip);

        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            RefundRule rule = new RefundRule();
            rule.setId(id);
            rule.setRuleName(request.getParameter("ruleName"));
            rule.setApplyBeforeHours(Integer.parseInt(request.getParameter("applyBeforeHours")));
            rule.setRefundPercentage(Double.parseDouble(request.getParameter("refundPercentage")));
            String ptId = request.getParameter("applyToPassengerTypeId");
            String ctId = request.getParameter("applyToCarriageTypeId");
            rule.setApplyToPassengerTypeId(ptId != null && !ptId.isEmpty() ? Integer.parseInt(ptId) : null);
            rule.setApplyToCarriageTypeId(ctId != null && !ctId.isEmpty() ? Integer.parseInt(ctId) : null);
            rule.setNote(request.getParameter("note"));
            rule.setActive(Boolean.parseBoolean(request.getParameter("isActive")));

            dao.update(rule);
            AuditLogDAO.addLog(staff.getId(), "UPDATE", "RefundRules", id,
                    "Sửa quy tắc hoàn tiền: " + rule.getRuleName(), ip);

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deactivate(id);
            AuditLogDAO.addLog(staff.getId(), "DELETE", "RefundRules", id,
                    "Vô hiệu hóa quy tắc hoàn tiền", ip);
        }

        response.sendRedirect("manageRefundRules");
    }
}
