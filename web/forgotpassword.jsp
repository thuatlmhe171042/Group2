<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quên Mật khẩu</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" href="index.jsp">Website Name</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="login.jsp">Đăng nhập</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="register.jsp">Đăng ký</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="adminLogin.jsp">Staff/Admin</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-5">
                    <div class="card shadow">
                        <div class="card-body p-4">
                            <h3 class="text-center mb-4">Tìm tài khoản của bạn</h3>
                            <p class="text-muted text-center">Vui lòng nhập địa chỉ email của bạn để tìm kiếm tài khoản.</p>
                            
                            <%
                                String error = (String) request.getAttribute("error");
                                String successMessage = (String) request.getAttribute("successMessage");
                                
                                if (error != null) {
                            %>
                                    <div class="alert alert-danger" role="alert">
                                        <%= error %>
                                    </div>
                            <%
                                }
                                if (successMessage != null) {
                            %>
                                    <div class="alert alert-success" role="alert">
                                        <%= successMessage %>
                                    </div>
                            <%
                                }
                            %>

                            <form action="forgotPassword" method="POST">
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <%
                                        String inputClass = "form-control";
                                        if (error != null) {
                                            inputClass += " is-invalid";
                                        } else if (successMessage != null) {
                                            inputClass += " is-valid";
                                        }
                                    %>
                                    <input type="email" class="<%= inputClass %>" id="email" name="email" required>
                                </div>
                                <div class="d-grid gap-2">
                                    <button type="submit" class="btn btn-primary">Gửi liên kết đặt lại</button>
                                </div>
                                <div class="text-center mt-3">
                                    <a href="login.jsp" class="text-decoration-none">Quay lại đăng nhập</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>