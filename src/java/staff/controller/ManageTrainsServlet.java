package staff.controller;

import dal.TrainDAO;
import dal.AuditLogDAO;
import models.Train;
import models.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ManageTrainsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TrainDAO dao = new TrainDAO();
        List<Train> trains = dao.getAllTrains(true); // Hiển thị cả tàu đang hoạt động và đã khóa
         request.setAttribute("activePage", "manageTrains");
        request.setAttribute("trains", trains);
        request.getRequestDispatcher("/staff/manageTrains.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy staff từ session
        HttpSession session = request.getSession(false);
        User staff = (User) (session != null ? session.getAttribute("user") : null);
        String ip = request.getRemoteAddr();

        if (staff == null || !"staff".equals(staff.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        TrainDAO dao = new TrainDAO();

       if ("add".equals(action)) {
    String code = request.getParameter("trainCode");
    String name = request.getParameter("trainName");
    String desc = request.getParameter("description");
    Train t = new Train(0, code, name, desc, null, null, false); // 7 tham số
    int newId = dao.addTrain(t);

    AuditLogDAO.addLog(staff.getId(), "ADD", "Trains", newId, 
        "Thêm tàu: " + code + " - " + name, ip);
    response.sendRedirect("manageTrains");

} else if ("edit".equals(action)) {
    int id = Integer.parseInt(request.getParameter("id"));
    String code = request.getParameter("trainCode");
    String name = request.getParameter("trainName");
    String desc = request.getParameter("description");

    Train before = dao.getById(id);
    Train t = new Train(id, code, name, desc, before.getCreatedAt(), null, before.isIsDeleted());
    dao.updateTrain(t);

    String details = String.format("Sửa tàu: %s -> %s, %s -> %s, %s -> %s",
        before.getTrainCode(), code,
        before.getTrainName(), name,
        before.getDescription(), desc
    );
    AuditLogDAO.addLog(staff.getId(), "UPDATE", "Trains", id, details, ip);
response.sendRedirect("manageTrains");
} else if ("toggleStatus".equals(action)) {
    int id = Integer.parseInt(request.getParameter("id"));
    Train t = dao.getById(id);
    boolean newStatus = !t.isIsDeleted();
    dao.toggleStatus(id, newStatus);

    String details = newStatus ? "Khóa tàu" : "Mở khóa tàu";
    AuditLogDAO.addLog(staff.getId(), "TOGGLE_STATUS", "Trains", id, details, ip);
    response.sendRedirect("manageTrains");
}
    }
}
