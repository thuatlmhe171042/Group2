/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package admin.controller;

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
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;
import utils.JavaMailUtil;

/**
 *
 * @author thuat
 */
public class AdminProfileServlet extends HttpServlet {
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
            out.println("<title>Servlet AdminProfileServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminProfileServlet at " + request.getContextPath () + "</h1>");
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
        HttpSession session = request.getSession(false);
        User admin = (User) session.getAttribute("user");
        request.setAttribute("admin", admin);
        request.getRequestDispatcher("/admin/adminProfile.jsp").forward(request, response);
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
        HttpSession session = request.getSession(false);
        User admin = (User) session.getAttribute("user");
        request.setAttribute("admin", admin);

        String action = request.getParameter("action");

        // 1. Cập nhật thông tin cá nhân (name)
        if ("updateInfo".equals(action)) {
            String name = request.getParameter("name");
            if (name != null && !name.isEmpty()) {
                admin.setName(name);
                boolean ok = userDAO.updateAdmin(admin);
                if (ok) {
                    session.setAttribute("user", admin);
                    request.setAttribute("message", "Cập nhật thông tin thành công!");
                } else {
                    request.setAttribute("error", "Không cập nhật được thông tin!");
                }
            }
            request.getRequestDispatcher("/admin/adminProfile.jsp").forward(request, response);
            return;
        }

        // 2. Gửi OTP xác thực đổi email
        if ("sendOtpEmail".equals(action)) {
            String newEmail = request.getParameter("newEmail");
            if (newEmail == null || newEmail.isEmpty()) {
                request.setAttribute("error", "Email mới không hợp lệ!");
            } else if (userDAO.isEmailExists(newEmail)) {
                request.setAttribute("error", "Email mới đã được sử dụng!");
            } else {
                String otp = String.format("%06d", new Random().nextInt(1000000));
                Date expiresAt = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
                otpDAO.insertOtp(admin.getEmail(), otp, "change_email", expiresAt);
                boolean sent = JavaMailUtil.sendOtpMail(admin.getEmail(), otp);
                request.setAttribute("newEmail", newEmail);
                request.setAttribute("otpSentEmail", true);
                if (sent) {
                    request.setAttribute("message", "Mã OTP đã được gửi về email cũ. Vui lòng kiểm tra hộp thư!");
                } else {
                    request.setAttribute("message", "Không gửi được mail, mã OTP để test: " + otp);
                }
            }
            request.getRequestDispatcher("/admin/adminProfile.jsp").forward(request, response);
            return;
        }

        // 3. Xác thực OTP đổi email và update email mới, rồi logout
        if ("changeEmail".equals(action)) {
            String newEmail = request.getParameter("newEmail");
            String otp = request.getParameter("otp");
            if (newEmail == null || newEmail.isEmpty()) {
                request.setAttribute("error", "Email mới không hợp lệ!");
                request.setAttribute("otpSentEmail", true);
            } else if (otp == null || otp.isEmpty()) {
                request.setAttribute("error", "Bạn chưa nhập mã OTP!");
                request.setAttribute("otpSentEmail", true);
                request.setAttribute("newEmail", newEmail);
            } else if (!otpDAO.isValidOtp(admin.getEmail(), otp, "change_email")) {
                request.setAttribute("error", "Mã OTP không hợp lệ hoặc đã hết hạn!");
                request.setAttribute("otpSentEmail", true);
                request.setAttribute("newEmail", newEmail);
            } else {
                boolean updated = userDAO.updateEmail(admin.getId(), newEmail);
                if (updated) {
                    otpDAO.markOtpUsed(admin.getEmail(), otp, "change_email");
                    session.invalidate();
                    request.setAttribute("message", "Đổi email thành công! Vui lòng đăng nhập lại bằng email mới.");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                    return;
                } else {
                    request.setAttribute("error", "Có lỗi khi đổi email!");
                    request.setAttribute("otpSentEmail", true);
                    request.setAttribute("newEmail", newEmail);
                }
            }
            request.getRequestDispatcher("/admin/adminProfile.jsp").forward(request, response);
            return;
        }

        // 4. Gửi OTP xác thực đổi mật khẩu
        if ("sendOtp".equals(action)) {
            String oldPassword = request.getParameter("oldPassword");
            if (!oldPassword.equals(admin.getPassword())) {
                request.setAttribute("error", "Mật khẩu hiện tại không đúng!");
            } else {
                String otp = String.format("%06d", new Random().nextInt(1000000));
                Date expiresAt = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
                otpDAO.insertOtp(admin.getEmail(), otp, "change_password", expiresAt);
                boolean sent = JavaMailUtil.sendOtpMail(admin.getEmail(), otp);
                if (sent) {
                    request.setAttribute("message", "Mã OTP đã được gửi về email. Vui lòng kiểm tra hộp thư.");
                } else {
                    request.setAttribute("message", "Không gửi được mail, mã OTP để test: " + otp);
                }
                request.setAttribute("otpSent", true);
            }
            request.getRequestDispatcher("/admin/adminProfile.jsp").forward(request, response);
            return;
        }

        // 5. Xác thực OTP đổi mật khẩu
        if ("changePassword".equals(action)) {
            String otp = request.getParameter("otp");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            if (otp == null || otp.trim().isEmpty()) {
                request.setAttribute("error", "Bạn chưa nhập mã OTP!");
                request.setAttribute("otpSent", true);
            } else if (!otpDAO.isValidOtp(admin.getEmail(), otp, "change_password")) {
                request.setAttribute("error", "Mã OTP không hợp lệ hoặc đã hết hạn!");
                request.setAttribute("otpSent", true);
            } else if (newPassword == null || newPassword.length() < 4 || !newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "Mật khẩu mới không hợp lệ hoặc nhập lại chưa khớp!");
                request.setAttribute("otpSent", true);
            } else {
                boolean ok = userDAO.updateStaffPassword(admin.getId(), newPassword);
                if (ok) {
                    admin.setPassword(newPassword);
                    session.setAttribute("user", admin);
                    otpDAO.markOtpUsed(admin.getEmail(), otp, "change_password");
                    request.setAttribute("message", "Đổi mật khẩu thành công!");
                } else {
                    request.setAttribute("error", "Có lỗi khi đổi mật khẩu!");
                    request.setAttribute("otpSent", true);
                }
            }
            request.getRequestDispatcher("/admin/adminProfile.jsp").forward(request, response);
            return;
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
