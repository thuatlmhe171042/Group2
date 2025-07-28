<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang quản trị Admin</title>
    <%@ include file="../header.jsp" %>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"/>
    <style>
        body { background: #f3f6fa; }
        .dashboard-card {
            max-width: 420px;
            margin: 60px auto 0 auto;
            border-radius: 14px;
            box-shadow: 0 2px 12px #b1b1b17a;
        }
    </style>
</head>
<body>
    <%@ include file="sidebar.jsp" %>
    <div class="container" style="margin-left:240px; padding-top:40px;">
        <h2 class="mb-4">Trang quản trị Admin</h2>
        <div class="card dashboard-card">
            <div class="card-body text-center">
                <h5 class="card-title mb-3">Tổng số nhân viên đang hoạt động</h5>
                <span class="display-3 text-primary font-weight-bold">${totalStaff}</span>
            </div>
        </div>
    </div>
    <%@ include file="../footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
