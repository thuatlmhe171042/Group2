<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng nhập</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    </head>
    <body class="bg-light">
        
        <%-- Redirect if user is already logged in --%>
        <%
            if (session.getAttribute("account") != null) {
                response.sendRedirect("home"); // Hoặc trang chủ của bạn
            }
        %>

        <jsp:include page="common/header.jsp"/>

        <div class="container" style="margin-top: 80px; margin-bottom: 80px;">
            <div class="row justify-content-center">
                <div class="col-md-5">
                    <div class="card shadow-lg">
                        <div class="card-body p-4">
                            <h3 class="text-center text-primary mb-4">Đăng Nhập</h3>
                            
                            <%
                                // Lấy thông báo lỗi từ request (khi đăng nhập sai)
                                String error = (String) request.getAttribute("error");
                                if (error != null) {
                            %>
                                    <div class="alert alert-danger" role="alert">
                                        <%= error %>
                                    </div>
                            <%
                                }
                            
                                // Lấy thông báo thành công từ session (khi đổi mật khẩu xong)
                                String successMessage = (String) session.getAttribute("successMessage");
                                if (successMessage != null) {
                            %>
                                    <div class="alert alert-success" role="alert">
                                        <%= successMessage %>
                                    </div>
                            <%
                                    // Xóa thông báo khỏi session để không hiển thị lại
                                    session.removeAttribute("successMessage");
                                }
                            %>

                            <form action="login" method="POST">
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                                        <input type="email" class="form-control" id="email" name="email" required>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">Mật khẩu</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-key"></i></span>
                                        <input type="password" class="form-control" id="password" name="password" required>
                                    </div>
                                </div>
                                
                                <div class="d-flex justify-content-between align-items-center mb-3">
                                    <div class="form-check">
                                        <input type="checkbox" class="form-check-input" id="remember">
                                        <label class="form-check-label" for="remember">Ghi nhớ</label>
                                    </div>
                                    <a href="forgotpassword.jsp" class="text-decoration-none">Quên mật khẩu?</a>
                                </div>
                                
                                <div class="d-grid gap-2">
                                    <button type="submit" class="btn btn-primary btn-lg">Đăng nhập</button>
                                </div>
                                <hr class="my-4">
                                <p class="text-center mb-0">Chưa có tài khoản? 
                                    <a href="register.jsp" class="fw-bold text-decoration-none">Đăng ký ngay</a>
                                </p>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="common/footer.jsp"/>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>









