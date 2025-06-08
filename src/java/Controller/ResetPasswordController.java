/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.UserDAO;
import Models.User;
import Utils.PasswordUtils;
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
@WebServlet(name="ResetPasswordController", urlPatterns={"/resetpassword"})
public class ResetPasswordController extends HttpServlet {
   
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
            out.println("<title>Servlet ResetPasswordController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetPasswordController at " + request.getContextPath () + "</h1>");
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
        String token = request.getParameter("token");
        UserDAO userDAO = new UserDAO();

        if (token == null || !userDAO.isValidResetToken(token)) {
            request.setAttribute("error", "Link không hợp lệ hoặc đã hết hạn. Vui lòng thử lại.");
            request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
            return;
        }
        
        request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
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
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        UserDAO userDAO = new UserDAO();

        if (token == null || !userDAO.isValidResetToken(token)) {
            request.setAttribute("error", "Yêu cầu không hợp lệ hoặc đã hết hạn. Vui lòng bắt đầu lại.");
            request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp.");
            request.setAttribute("token", token);
            request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
            return;
        }
        
        String email = userDAO.getEmailFromResetToken(token);
        if (email == null) {
            request.setAttribute("error", "Không thể tìm thấy tài khoản liên kết với yêu cầu này.");
            request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
            return;
        }
        
        String hashedPassword = PasswordUtils.hashPassword(newPassword);
        boolean success = userDAO.updatePassword(email, hashedPassword);

        if (success) {
            userDAO.invalidateResetToken(token);
            request.getSession().setAttribute("successMessage", "Mật khẩu của bạn đã được cập nhật thành công. Vui lòng đăng nhập lại.");
            response.sendRedirect("login");
        } else {
            request.setAttribute("error", "Đã có lỗi xảy ra khi cập nhật mật khẩu. Vui lòng thử lại.");
            request.setAttribute("token", token);
            request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles the final step of password resetting.";
    }// </editor-fold>

}