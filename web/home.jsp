<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Chủ - Vé Tàu Online</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <style>
        body {
            padding-top: 56px; /* Adjust for fixed-top navbar */
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        main {
            flex: 1;
        }
        .welcome-section {
            padding: 80px 0;
            background-color: #e9ecef;
            text-align: center;
        }
        .features-section {
            padding: 60px 0;
        }
        .feature-icon {
            font-size: 3.5rem;
            color: #0d6efd; /* Bootstrap primary blue */
        }
    </style>
</head>
<body>
    
    <%
        User user = (User) session.getAttribute("account");
        // Redirect to login if user is not logged in
        if (user == null) {
            response.sendRedirect("login.jsp");
            return; // Stop further processing of the page
        }
    %>

    <jsp:include page="common/header.jsp"/>

    <main>
        <div class="welcome-section">
            <div class="container">
                <h1 class="display-4">Chào mừng đến với Hệ thống Đặt Vé Tàu!</h1>
                <p class="lead">Nhanh chóng, tiện lợi, an toàn.</p>
                
                <%-- Display user-specific welcome message --%>
                <p class="h4 mt-3">Xin chào, <%= user.getName() %>!</p>
                
                <%
                    // Display role-specific dashboard buttons
                    if ("admin".equals(user.getRole())) {
                %>
                        <a href="admin/dashboard.jsp" class="btn btn-danger btn-lg mt-3"><i class="bi bi-person-gear"></i> Đi đến Dashboard Admin</a>
                <%
                    } else if ("staff".equals(user.getRole())) {
                %>
                        <a href="staff/dashboard.jsp" class="btn btn-warning btn-lg mt-3"><i class="bi bi-clipboard-data"></i> Đi đến Dashboard Staff</a>
                <%
                    }
                %>
            </div>
        </div>

        <div class="features-section text-center">
            <div class="container">
                <h2>Dịch vụ của chúng tôi</h2>
                <div class="row mt-4">
                    <div class="col-md-4">
                        <i class="bi bi-calendar-check feature-icon mb-3"></i>
                        <h4>Đặt vé dễ dàng</h4>
                        <p class="text-muted">Chọn tuyến đường, ngày giờ và thanh toán chỉ trong vài bước.</p>
                    </div>
                    <div class="col-md-4">
                        <i class="bi bi-credit-card feature-icon mb-3"></i>
                        <h4>Thanh toán an toàn</h4>
                        <p class="text-muted">Hỗ trợ nhiều phương thức thanh toán trực tuyến bảo mật.</p>
                    </div>
                    <div class="col-md-4">
                        <i class="bi bi-headset feature-icon mb-3"></i>
                        <h4>Hỗ trợ 24/7</h4>
                        <p class="text-muted">Đội ngũ hỗ trợ luôn sẵn sàng giải đáp thắc mắc của bạn.</p>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="common/footer.jsp"/>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
