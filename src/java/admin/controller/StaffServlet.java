/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package admin.controller;

import dal.AuditLogDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.AuditLog;
import models.User;

/**
 *
 * @author thuat
 */
public class StaffServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private AuditLogDAO auditLogDAO = new AuditLogDAO();
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StaffServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "addForm":
                showAddForm(request, response);
                break;
            case "editForm":
                showEditForm(request, response);
                break;
            case "list":
            default:
                listStaff(request, response);
                break;
        }
    } 
    
    private void listStaff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<User> staffList;
        if (keyword != null && !keyword.trim().isEmpty()) {
            staffList = userDAO.searchStaff(keyword.trim());
        } else {
            staffList = userDAO.getAllActiveStaff();
        }
        request.setAttribute("staffList", staffList);
        request.getRequestDispatcher("/admin/staffList.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("formAction", "add");
        request.getRequestDispatcher("/admin/staffForm.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User staff = userDAO.getStaffById(id);
        if (staff == null) {
            response.sendRedirect("staff?action=list");
            return;
        }
        request.setAttribute("staff", staff);
        request.setAttribute("formAction", "update");
        request.getRequestDispatcher("/admin/staffForm.jsp").forward(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addStaff(request, response);
                break;
            case "update":
                updateStaff(request, response);
                break;
            case "toggleStatus":
                toggleStaffStatus(request, response);
                break;
            default:
                response.sendRedirect("staff?action=list");
        }
    }
    
    private void addStaff(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        User staff = new User();
        staff.setName(name);
        staff.setEmail(email);
        staff.setPassword(password);
        staff.setPhone(phone);

        boolean success = userDAO.addStaff(staff);

        // Ghi log
        if (success) {
            AuditLog log = new AuditLog();
            log.setUser((User) request.getSession().getAttribute("user"));
            log.setActionType("CREATE");
            log.setTableName("Users");
            log.setRecordId(0); // Có thể lấy Id mới insert nếu muốn
            log.setChangeDetails("Admin tạo nhân viên mới: " + name + " (" + email + ")");
            log.setIpAddress(request.getRemoteAddr());
            auditLogDAO.insertAuditLog(log);
        }

        request.setAttribute("message", success ? "Thêm mới thành công!" : "Thêm mới thất bại!");
        listStaff(request, response);
    }

    private void updateStaff(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        User staff = new User();
        staff.setId(id);
        staff.setName(name);
        staff.setEmail(email);
        staff.setPhone(phone);

        boolean success = userDAO.updateStaff(staff);

        // Ghi log
        if (success) {
            AuditLog log = new AuditLog();
            log.setUser((User) request.getSession().getAttribute("user"));
            log.setActionType("UPDATE");
            log.setTableName("Users");
            log.setRecordId(id);
            log.setChangeDetails("Admin sửa thông tin nhân viên: " + name + " (" + email + ")");
            log.setIpAddress(request.getRemoteAddr());
            auditLogDAO.insertAuditLog(log);
        }

        request.setAttribute("message", success ? "Cập nhật thành công!" : "Cập nhật thất bại!");
        listStaff(request, response);
    }

    private void toggleStaffStatus(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean enable = Boolean.parseBoolean(request.getParameter("enable"));
        boolean success = userDAO.setStaffActive(id, enable);

        // Ghi log
        if (success) {
            AuditLog log = new AuditLog();
            log.setUser((User) request.getSession().getAttribute("user"));
            log.setActionType(enable ? "UNDELETE" : "DELETE");
            log.setTableName("Users");
            log.setRecordId(id);
            log.setChangeDetails("Admin " + (enable ? "kích hoạt lại" : "vô hiệu hóa") + " nhân viên Id = " + id);
            log.setIpAddress(request.getRemoteAddr());
            auditLogDAO.insertAuditLog(log);
        }

        response.sendRedirect("staff?action=list");
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
