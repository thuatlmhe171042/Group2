<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="bg-white border-right" style="width:220px; min-height:100vh; position:fixed; top:0; left:0; z-index:1040;">
    <div class="sidebar-sticky pt-4">
        <ul class="nav flex-column">
            <li class="nav-item mb-2">
                <a class="nav-link font-weight-bold" href="dashboard" style="font-size:1.1rem;">
                    <i class="fas fa-tachometer-alt mr-1"></i> Trang chủ Admin
                </a>
            </li>
            <li class="nav-item mb-2">
                <a class="nav-link" href="staff?action=list">
                    <i class="fas fa-users-cog mr-1"></i> Quản Lý Tài Khoản Nhân Viên
                </a>
            </li>
            <li class="nav-item mb-2">
                <a class="nav-link" href="auditlog">
                    <i class="fas fa-book mr-1"></i> Nhật Ký Hệ Thống
                </a>
            </li>
            <li class="nav-item mb-2">
    <a class="nav-link" href="profile">
        <i class="fas fa-user-edit mr-1"></i> Hồ sơ cá nhân
    </a>
</li>
        </ul>
    </div>
</aside>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
