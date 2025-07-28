<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đổi mật khẩu mới</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"/>
</head>
<body>
    <div class="container" style="max-width:400px; margin:60px auto;">
        <h3 class="mb-4 text-center">Đặt mật khẩu mới</h3>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <form method="post" action="resetPassword">
            <input type="hidden" name="email" value="${email}"/>
            <input type="hidden" name="otp" value="${otp}"/>
            <div class="form-group">
                <label>Mật khẩu mới:</label>
                <input type="password" class="form-control" name="password" required minlength="4"/>
            </div>
            <div class="form-group">
                <label>Nhập lại mật khẩu:</label>
                <input type="password" class="form-control" name="repassword" required minlength="4"/>
            </div>
            <button type="submit" class="btn btn-success w-100">Đặt mật khẩu mới</button>
        </form>
    </div>
</body>
</html>
