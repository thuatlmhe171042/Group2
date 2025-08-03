package staff.controller;

import dal.*;
import models.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ManagePromotionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Promotion> promotions = new PromotionDAO().getAll();
        request.setAttribute("promotions", promotions);
        request.getRequestDispatcher("/staff/managePromotions.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User staff = (User) (session != null ? session.getAttribute("user") : null);
        String ip = request.getRemoteAddr();
        String action = request.getParameter("action");
        PromotionDAO dao = new PromotionDAO();

        if ("add".equals(action)) {
            String rawDiscountType = request.getParameter("discountType");
           String discountType = ("percent".equalsIgnoreCase(rawDiscountType) || "fixed".equalsIgnoreCase(rawDiscountType))
        ? rawDiscountType.toLowerCase() : "percent";


            // Format ngày giờ theo chuẩn SQL
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createdAt = LocalDateTime.now().format(formatter);

            Promotion p = new Promotion(
                0,
                request.getParameter("promotionCode"),
                request.getParameter("name"),
                request.getParameter("description"),
                discountType,
                parseDouble(request.getParameter("discountValue")),
                request.getParameter("startDate"),
                request.getParameter("endDate"),
                parseInt(request.getParameter("usageLimit")),
                0,
                request.getParameter("status"),
                createdAt,
                staff != null ? staff.getId() : 0,
                false
            );
            int id = dao.addPromotion(p);
            AuditLogDAO.addLog(staff.getId(), "ADD", "Promotions", id, "Thêm khuyến mãi " + p.getPromotionCode(), ip);

        } else if ("edit".equals(action)) {
            int id = parseInt(request.getParameter("id"));
            String rawDiscountType = request.getParameter("discountType");
            String discountType = ("percent".equalsIgnoreCase(rawDiscountType) || "fixed".equalsIgnoreCase(rawDiscountType))
                    ? rawDiscountType : "percent"; // fallback

            Promotion p = new Promotion(
                id,
                request.getParameter("promotionCode"),
                request.getParameter("name"),
                request.getParameter("description"),
                discountType,
                parseDouble(request.getParameter("discountValue")),
                request.getParameter("startDate"),
                request.getParameter("endDate"),
                parseInt(request.getParameter("usageLimit")),
                parseInt(request.getParameter("currentUsageCount")),
                request.getParameter("status"),
                request.getParameter("createdAt"),
                parseInt(request.getParameter("createdBy")),
                Boolean.parseBoolean(request.getParameter("isDeleted"))
            );
            dao.updatePromotion(p);
            AuditLogDAO.addLog(staff.getId(), "UPDATE", "Promotions", id, "Sửa khuyến mãi " + p.getPromotionCode(), ip);

        } else if ("delete".equals(action)) {
            int id = parseInt(request.getParameter("id"));
            dao.deletePromotion(id);
            AuditLogDAO.addLog(staff.getId(), "DELETE", "Promotions", id, "Xóa khuyến mãi " + id, ip);
        }

        response.sendRedirect("managePromotions");
    }

    // ===== Helper Methods để tránh lỗi khi parse null =====
    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
