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
@WebServlet(name="HomeController", urlPatterns={"/home"})
public class HomeController extends HttpServlet {
   
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
            out.println("<title>Servlet HomeController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeController at " + request.getContextPath () + "</h1>");
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        User admin = (User) session.getAttribute("admin");

        // Thêm thông tin người dùng vào request
        if (user != null) {
            request.setAttribute("user", user);
        }
        if (admin != null) {
            request.setAttribute("admin", admin);
        }

        // Xử lý các action
        if (action != null && !action.isEmpty()) {
            switch (action) {
                case "logout":
                    session.invalidate();
                    response.sendRedirect("index.jsp");
                    return;

                case "profile":
                    if (user == null && admin == null) {
                        response.sendRedirect("login.jsp");
                        return;
                    }
                    String profilePath = getProfilePath(user, admin);
                    response.sendRedirect(profilePath);
                    return;

                case "dashboard":
                    if (admin == null) {
                        response.sendRedirect("adminLogin.jsp");
                        return;
                    }
                    String dashboardPath = admin.getRole().equals("admin") ? 
                            "admin/dashboard.jsp" : "staff/dashboard.jsp";
                    response.sendRedirect(dashboardPath);
                    return;
            }
        }

        // Mặc định hiển thị trang chủ
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private String getProfilePath(User user, User admin) {
        if (admin != null) {
            return admin.getRole().equals("admin") ? 
                    "admin/profile.jsp" : "staff/profile.jsp";
        }
        return "user/profile.jsp";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
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
