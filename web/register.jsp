<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng Ký Tài Khoản</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    </head>
    <body class="bg-light">

        <%-- Redirect if user is already logged in --%>
        <%
            if (session.getAttribute("account") != null) {
                response.sendRedirect("home");
            }
        %>

        <jsp:include page="common/header.jsp"/>

        <div class="container" style="margin-top: 80px; margin-bottom: 80px;">
            <div class="row justify-content-center">
                <div class="col-md-7">
                    <div class="card shadow-lg">
                        <div class="card-body p-4">
                            <h3 class="text-center text-primary mb-4">Tạo Tài Khoản Mới</h3>
                            
                            <%
                                String error = (String) request.getAttribute("error");
                                if (error != null) {
                            %>
                                    <div class="alert alert-danger" role="alert">
                                        <%= error %>
                                    </div>
                            <%
                                }
                            %>

                            <form action="register" method="POST" id="registerForm">
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="name" class="form-label">Họ và tên</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-person"></i></span>
                                            <input type="text" class="form-control" id="name" name="name" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="phone" class="form-label">Số điện thoại</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-telephone"></i></span>
                                            <input type="tel" class="form-control" id="phone" name="phone" required pattern="[0-9]{10}" title="Vui lòng nhập số điện thoại 10 số">
                                        </div>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                                        <input type="email" class="form-control" id="email" name="email" required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="password" class="form-label">Mật khẩu</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-key"></i></span>
                                            <input type="password" class="form-control" id="password" name="password" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="confirmPassword" class="form-label">Xác nhận mật khẩu</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-key-fill"></i></span>
                                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="d-grid gap-2 mt-3">
                                    <button type="submit" class="btn btn-primary btn-lg">Đăng ký</button>
                                </div>
                                <hr class="my-4">
                                <p class="text-center mb-0">Đã có tài khoản? 
                                    <a href="login.jsp" class="fw-bold text-decoration-none">Đăng nhập ngay</a>
                                </p>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="common/footer.jsp"/>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            document.getElementById('registerForm').addEventListener('submit', function(event) {
                var password = document.getElementById('password').value;
                var confirmPassword = document.getElementById('confirmPassword').value;
                
                if (password !== confirmPassword) {
                    event.preventDefault();
                    alert('Mật khẩu xác nhận không khớp!');
                }
            });
        </script>
    </body>
</html>





