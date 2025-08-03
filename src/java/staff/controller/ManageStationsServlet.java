package staff.controller;

import dal.StationDAO;
import dal.AuditLogDAO;
import models.Station;
import models.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ManageStationsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StationDAO dao = new StationDAO();
        List<Station> stations = dao.getAllStations(true);
        request.setAttribute("stations", stations);
        request.setAttribute("activePage", "manageStations");
        request.getRequestDispatcher("/staff/manageStations.jsp").forward(request, response);
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
        StationDAO dao = new StationDAO();

        if ("add".equals(action)) {
            String code = request.getParameter("stationCode");
            String name = request.getParameter("stationName");
            String city = request.getParameter("city");
            String address = request.getParameter("address");
            double lat = Double.parseDouble(request.getParameter("latitude"));
            double lng = Double.parseDouble(request.getParameter("longitude"));
            Station s = new Station(0, code, name, city, address, lat, lng, false);
            int newId = dao.addStation(s);

            AuditLogDAO.addLog(staff.getId(), "ADD", "Stations", newId,
                "Thêm ga: " + code + " - " + name, ip);

        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String code = request.getParameter("stationCode");
            String name = request.getParameter("stationName");
            String city = request.getParameter("city");
            String address = request.getParameter("address");
            double lat = Double.parseDouble(request.getParameter("latitude"));
            double lng = Double.parseDouble(request.getParameter("longitude"));

            Station before = dao.getById(id);
            Station s = new Station(id, code, name, city, address, lat, lng, before.isIsDeleted());
            dao.updateStation(s);

            String details = String.format(
                "Sửa ga: %s -> %s, %s -> %s, %s -> %s, %s -> %s, (%.6f, %.6f) -> (%.6f, %.6f)",
                before.getStationCode(), code,
                before.getStationName(), name,
                before.getCity(), city,
                before.getAddress(), address,
                before.getLatitude(), before.getLongitude(),
                lat, lng
            );
            AuditLogDAO.addLog(staff.getId(), "UPDATE", "Stations", id, details, ip);

        } else if ("toggleStatus".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Station s = dao.getById(id);
            boolean newStatus = !s.isIsDeleted();
            dao.toggleStatus(id, newStatus);

            String details = newStatus ? "Khóa ga" : "Mở khóa ga";
            AuditLogDAO.addLog(staff.getId(), "TOGGLE_STATUS", "Stations", id, details, ip);
        }

        response.sendRedirect("manageStations");
    }
}
