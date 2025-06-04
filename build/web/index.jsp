<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang chủ</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" href="index.jsp">Vé tàu</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item">
                            <a class="nav-link active" href="index.jsp">Trang chủ</a>
                        </li>
                        <!-- Thêm các menu khác ở đây -->
                    </ul>
                    
                    <ul class="navbar-nav ms-auto">
                        <% if (request.getAttribute("admin") != null) { %>
                            <li class="nav-item">
                                <a class="nav-link" href="home?action=dashboard">
                                    <i class="bi bi-speedometer2"></i> Dashboard
                                </a>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" 
                                   data-bs-toggle="dropdown" aria-expanded="false">
                                    <i class="bi bi-person-circle"></i> 
                                    ${admin.name}
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                                    <li><a class="dropdown-item" href="home?action=profile">
                                        <i class="bi bi-person"></i> Thông tin cá nhân</a>
                                    </li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="home?action=logout">
                                        <i class="bi bi-box-arrow-right"></i> Đăng xuất</a>
                                    </li>
                                </ul>
                            </li>
                        <% } else if (request.getAttribute("user") != null) { %>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" 
                                   data-bs-toggle="dropdown" aria-expanded="false">
                                    <i class="bi bi-person-circle"></i> 
                                    ${user.name}
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                                    <li><a class="dropdown-item" href="home?action=profile">
                                        <i class="bi bi-person"></i> Thông tin cá nhân</a>
                                    </li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="home?action=logout">
                                        <i class="bi bi-box-arrow-right"></i> Đăng xuất</a>
                                    </li>
                                </ul>
                            </li>
                        <% } else { %>
                            <li class="nav-item">
                                <a class="nav-link" href="login.jsp">
                                    <i class="bi bi-box-arrow-in-right"></i> Đăng nhập
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="register.jsp">
                                    <i class="bi bi-person-plus"></i> Đăng ký
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="adminLogin.jsp">
                                    <i class="bi bi-shield-lock"></i> Staff/Admin
                                </a>
                            </li>
                        <% } %>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container mt-5">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="text-center mb-4">Chào mừng đến với Vé tàu</h1>
                    
                    <!-- Nội dung trang chủ -->
                    <div class="row">
                        <div class="col-md-4">
                            <div class="card mb-4">
                                <div class="card-body">
                                    <h5 class="card-title">Tiêu đề 1</h5>
                                    <p class="card-text">Nội dung mô tả ngắn...</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card mb-4">
                                <div class="card-body">
                                    <h5 class="card-title">Tiêu đề 2</h5>
                                    <p class="card-text">Nội dung mô tả ngắn...</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card mb-4">
                                <div class="card-body">
                                    <h5 class="card-title">Tiêu đề 3</h5>
                                    <p class="card-text">Nội dung mô tả ngắn...</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>






