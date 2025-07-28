package controller;

import dal.UserDAO;
import dal.EmailVerificationDAO;
import models.User;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class VerifyOtpRegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();
    private EmailVerificationDAO otpDAO = new EmailVerificationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Không cho phép vào trực tiếp, có thể redirect về register hoặc homepage
        response.sendRedirect("register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String email = request.getParameter("email");
        String otp = request.getParameter("otp");

        if (email == null || otp == null) {
            request.setAttribute("error", "Dữ liệu không hợp lệ!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        boolean valid = otpDAO.isValidOtp(email, otp, "register");
        if (!valid) {
            request.setAttribute("error", "Mã OTP không hợp lệ hoặc đã hết hạn!");
            request.setAttribute("email", email);
            request.getRequestDispatcher("verifyOtpRegister.jsp").forward(request, response);
            return;
        }

        // Lấy info tạm trong session
        String name = (String) session.getAttribute("reg_name");
        String password = (String) session.getAttribute("reg_password");
        String phone = (String) session.getAttribute("reg_phone");

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setRole("customer");

        boolean ok = userDAO.addCustomer(user);
        if (ok) {
            otpDAO.markOtpUsed(email, otp, "register");
            session.removeAttribute("reg_name");
            session.removeAttribute("reg_email");
            session.removeAttribute("reg_password");
            session.removeAttribute("reg_phone");
            // Gửi message sang login.jsp, đúng yêu cầu của bạn
            request.setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập để tiếp tục.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Không đăng ký được tài khoản, thử lại sau!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet xác thực OTP đăng ký tài khoản mới";
    }
}
