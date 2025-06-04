/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.UserDAO;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
@WebServlet(name="AdminLoginController", urlPatterns={"/AdminLoginServlet"})
public class AdminLoginController extends HttpServlet {
   
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
            out.println("<title>Servlet AdminLoginController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminLoginController at " + request.getContextPath () + "</h1>");
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
        request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // Validate input
        if (email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            role == null || role.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng điền đầy đủ thông tin");
            request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
            return;
        }

        UserDAO dao = new UserDAO();
        
        // Validate role
        if (!dao.isValidRole(role)) {
            request.setAttribute("error", "Quyền truy cập không hợp lệ");
            request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
            return;
        }

        // Check if email exists with specified role
        if (!dao.checkEmailExistsWithRole(email, role)) {
            request.setAttribute("error", "Email không tồn tại hoặc không có quyền truy cập này");
            request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
            return;
        }

        // Try to login
        User user = dao.checkAdminLogin(email, password, role);
        
        if (user == null) {
            request.setAttribute("error", "Email hoặc mật khẩu không chính xác");
            request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("admin", user);
            
            // Chuyển hướng đến trang quản trị tương ứng
            if (role.equals("admin")) {
                response.sendRedirect("admin/dashboard.jsp");
            } else {
                response.sendRedirect("staff/dashboard.jsp");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Admin Login Servlet";
    }

}