<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng nhập Quản trị</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    </head>
    <body class="bg-light">

        <jsp:include page="common/header.jsp"/>

        <div class="container" style="margin-top: 80px; margin-bottom: 80px;">
            <div class="row justify-content-center">
                <div class="col-md-5">
                    <div class="card shadow-lg border-danger">
                        <div class="card-header bg-danger text-white text-center">
                            <h4 class="mb-0"><i class="bi bi-shield-lock-fill"></i> Cổng Đăng Nhập Quản Trị</h4>
                        </div>
                        <div class="card-body p-4">
                            
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

                            <form action="AdminLoginServlet" method="POST">
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-envelope-fill"></i></span>
                                        <input type="email" class="form-control" id="email" name="email" required>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">Mật khẩu</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-key-fill"></i></span>
                                        <input type="password" class="form-control" id="password" name="password" required>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="role" class="form-label">Vai trò</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-person-badge-fill"></i></span>
                                        <select class="form-select" id="role" name="role" required>
                                            <option value="" disabled selected>-- Chọn vai trò --</option>
                                            <option value="admin">Admin</option>
                                            <option value="staff">Staff</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="d-grid gap-2 mt-4">
                                    <button type="submit" class="btn btn-danger btn-lg">Đăng nhập</button>
                                </div>
                                <div class="text-center mt-3">
                                    <a href="login.jsp" class="text-decoration-none">
                                        <i class="bi bi-arrow-left-circle"></i> Quay lại đăng nhập thường
                                    </a>
                                </div>
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
