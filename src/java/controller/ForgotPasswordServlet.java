/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;


import dal.UserDAO;
import dal.EmailVerificationDAO;
import utils.JavaMailUtil;
import models.User;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author thuat
 */
public class ForgotPasswordServlet extends HttpServlet {
    
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
            out.println("<title>Servlet ForgotPasswordServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotPasswordServlet at " + request.getContextPath () + "</h1>");
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
        request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
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
        User user = userDAO.getUserByEmail(email);

        if (user == null) {
            request.setAttribute("error", "Không tìm thấy tài khoản với email này!");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
            return;
        }

        // Sinh OTP ngẫu nhiên
        String otp = String.format("%06d", new Random().nextInt(1000000));
        Date expiresAt = new Date(System.currentTimeMillis() + 10 * 60 * 1000); // 10 phút

        otpDAO.insertOtp(email, otp, "reset_password", expiresAt);

        // Gửi mail (nếu lỗi gửi, in OTP ra màn hình)
        boolean sent = JavaMailUtil.sendOtpMail(email, otp);

        request.setAttribute("email", email);
        if (sent) {
            request.setAttribute("message", "Mã OTP đã được gửi đến email của bạn. Vui lòng kiểm tra hộp thư!");
        } else {
            request.setAttribute("message", "Không gửi được mail!");
        }
        request.getRequestDispatcher("enterOtp.jsp").forward(request, response);
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
