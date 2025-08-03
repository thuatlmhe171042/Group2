package staff.controller;

import dal.CarriageTypeDAO;
import dal.AuditLogDAO;
import models.CarriageType;
import models.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ManageCarriageTypesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CarriageTypeDAO dao = new CarriageTypeDAO();
        List<CarriageType> types = dao.getAll();
        request.setAttribute("carriageTypes", types);
        request.setAttribute("activePage", "manageCarriageTypes");
        request.getRequestDispatcher("/staff/manageCarriageTypes.jsp").forward(request, response);
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
        CarriageTypeDAO dao = new CarriageTypeDAO();

        if ("add".equals(action)) {
            String name = request.getParameter("typeName");
            String desc = request.getParameter("description");
            Double basePricePerKm = null;
            String basePriceStr = request.getParameter("basePricePerKm");
            if (basePriceStr != null && !basePriceStr.trim().isEmpty()) {
                basePricePerKm = Double.parseDouble(basePriceStr);
            }
            CarriageType newType = new CarriageType(0, name, desc, basePricePerKm);
            int id = dao.addCarriageType(newType);
            AuditLogDAO.addLog(staff.getId(), "ADD", "CarriageTypes", id, "Thêm loại toa: " + name + ", giá/km: " + basePricePerKm, ip);

        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("typeName");
            String desc = request.getParameter("description");
            Double basePricePerKm = null;
            String basePriceStr = request.getParameter("basePricePerKm");
            if (basePriceStr != null && !basePriceStr.trim().isEmpty()) {
                basePricePerKm = Double.parseDouble(basePriceStr);
            }
            CarriageType before = dao.getById(id);
            dao.updateCarriageType(new CarriageType(id, name, desc, basePricePerKm));
            String details = String.format(
                "Sửa loại toa: %s -> %s, %s -> %s, giá/km: %s -> %s",
                before.getTypeName(), name,
                before.getDescription(), desc,
                before.getBasePricePerKm(), basePricePerKm
            );
            AuditLogDAO.addLog(staff.getId(), "UPDATE", "CarriageTypes", id, details, ip);
        }

        response.sendRedirect("manageCarriageTypes");
    }
}
