/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.UserDAO;
import Models.User;
import Utils.EmailService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name="ForgotPasswordController", urlPatterns={"/forgotpassword"})
public class ForgotPasswordController extends HttpServlet {
   
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
            out.println("<title>Servlet ForgotPasswordController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotPasswordController at " + request.getContextPath () + "</h1>");
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
        // Hiển thị trang nhập email để quên mật khẩu
        request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        String email = request.getParameter("email");
        
        UserDAO userDAO = new UserDAO();
        
        // 1. Kiểm tra xem email có tồn tại trong hệ thống không
        if (!userDAO.checkEmailExists(email)) {
            request.setAttribute("error", "Email không tồn tại trong hệ thống.");
            request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
            return;
        }
        
        // 2. Tạo token reset và lưu vào database
        String token = userDAO.createResetToken(email);
        
        if (token != null) {
            // 3. Gửi email chứa link reset
            try {
                EmailService emailService = new EmailService();
                String requestURL = request.getRequestURL().toString();
                emailService.sendResetPasswordEmail(email, token, requestURL);
                
                // 4. Đặt thông báo thành công và hiển thị lại trang
                request.setAttribute("successMessage", "Link đổi mật khẩu đã được gửi đến tài khoản mail của bạn, vui lòng kiểm tra lại.");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Đã có lỗi xảy ra trong quá trình gửi email. Vui lòng thử lại.");
            }
        } else {
            request.setAttribute("error", "Không thể tạo yêu cầu đổi mật khẩu. Vui lòng thử lại.");
        }
        
        request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
    }

    private String generateRandomPassword() {
        // Generate a simple 8-character password
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = (int) (chars.length() * Math.random());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles the user request to reset their password.";
    }// </editor-fold>

}

