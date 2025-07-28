package filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import models.User; // nếu bạn có class User trong model

public class StaffAuth implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        boolean isLoggedIn = session != null && session.getAttribute("user") != null;
        boolean isStaff = false;

        if (isLoggedIn) {
            User user = (User) session.getAttribute("user");
            isStaff = "staff".equals(user.getRole());
        }

        if (!isLoggedIn || !isStaff) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {}
}
