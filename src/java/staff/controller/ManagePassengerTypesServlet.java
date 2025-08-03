package staff.controller;

import dal.PassengerTypeDAO;
import dal.PassengerPricingRuleDAO;
import dal.AuditLogDAO;
import models.PassengerTypeRuleRow;
import models.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ManagePassengerTypesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PassengerTypeDAO dao = new PassengerTypeDAO();
        List<PassengerTypeRuleRow> list = dao.getAllForManagement();
        request.setAttribute("rows", list);
        request.setAttribute("activePage", "managePassengerTypes");
        request.getRequestDispatcher("/staff/managePassengerTypes.jsp").forward(request, response);
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession(false);
    User staff = (User) (session != null ? session.getAttribute("user") : null);
    String ip = request.getRemoteAddr();

    if (staff == null || !"staff".equals(staff.getRole())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    String action = request.getParameter("action");
    PassengerTypeDAO ptDao = new PassengerTypeDAO();
    PassengerPricingRuleDAO prDao = new PassengerPricingRuleDAO();

    if ("add".equals(action)) {
        String typeName = request.getParameter("typeName");
        String desc = request.getParameter("description");
        String discountType = request.getParameter("discountType");
        String discountValueRaw = request.getParameter("discountValue");
        double discountValue = 0;
        try {
            discountValue = Double.parseDouble(discountValueRaw);
        } catch (NumberFormatException e) {
            discountValue = 0;
        }

        int ptId = ptDao.addPassengerType(typeName, desc);
        prDao.addPricingRule(ptId, discountType, discountValue);

        AuditLogDAO.addLog(staff.getId(), "ADD", "PassengerTypes", ptId,
                "Thêm loại khách " + typeName + ", rule " + discountType + " " + discountValue, ip);

    } else if ("edit".equals(action)) {
        int ptId = Integer.parseInt(request.getParameter("passengerTypeId"));
        int ruleId = Integer.parseInt(request.getParameter("ruleId"));
        String typeName = request.getParameter("typeName");
        String desc = request.getParameter("description");
        String discountType = request.getParameter("discountType");
        String discountValueRaw = request.getParameter("discountValue");
        double discountValue = 0;
        try {
            discountValue = Double.parseDouble(discountValueRaw);
        } catch (NumberFormatException e) {
            discountValue = 0;
        }

        ptDao.updatePassengerType(ptId, typeName, desc);
        prDao.updatePricingRule(ruleId, discountType, discountValue);

        AuditLogDAO.addLog(staff.getId(), "UPDATE", "PassengerTypes", ptId,
                "Sửa loại khách " + typeName + ", rule " + discountType + " " + discountValue, ip);

    } else if ("toggleRule".equals(action)) {
        int ruleId = Integer.parseInt(request.getParameter("ruleId"));
        boolean toActive = Boolean.parseBoolean(request.getParameter("toActive"));
        prDao.toggleRuleActive(ruleId, toActive);

        AuditLogDAO.addLog(staff.getId(), "TOGGLE_STATUS", "PassengerPricingRules", ruleId,
                toActive ? "Kích hoạt rule" : "Vô hiệu hóa rule", ip);
    }

    response.sendRedirect("managePassengerTypes");
}

}
