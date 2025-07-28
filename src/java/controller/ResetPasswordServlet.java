/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;


import dal.UserDAO;
import dal.EmailVerificationDAO;
import models.User;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author thuat
 */
public class ResetPasswordServlet extends HttpServlet {
    
    private UserDAO userDAO = new UserDAO();
    private EmailVerificationDAO otpDAO = new EmailVerificationDAO();
   
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
            out.println("<title>Servlet ResetPasswordServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetPasswordServlet at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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
        String email = request.getParameter("email");
        String otp = request.getParameter("otp");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");

        if (!password.equals(repassword)) {
            request.setAttribute("error", "Mật khẩu nhập lại không khớp!");
            request.setAttribute("email", email);
            request.setAttribute("otp", otp);
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        boolean valid = otpDAO.isValidOtp(email, otp, "reset_password");
        if (!valid) {
            request.setAttribute("error", "Mã OTP không hợp lệ hoặc đã hết hạn!");
            request.setAttribute("email", email);
            request.getRequestDispatcher("enterOtp.jsp").forward(request, response);
            return;
        }

        // Cập nhật mật khẩu
        boolean updated = userDAO.updatePasswordByEmail(email, password);
        if (updated) {
            otpDAO.markOtpUsed(email, otp, "reset_password");
            request.setAttribute("message", "Cập nhật mật khẩu thành công! Vui lòng đăng nhập lại.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Không cập nhật được mật khẩu!");
            request.setAttribute("email", email);
            request.setAttribute("otp", otp);
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        }
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
