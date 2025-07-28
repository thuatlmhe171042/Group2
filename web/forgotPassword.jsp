<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quên mật khẩu</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"/>
</head>
<body>
    <div class="container" style="max-width:400px; margin:60px auto;">
        <h3 class="mb-4 text-center">Quên mật khẩu</h3>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <form method="post" action="forgotPassword">
            <div class="form-group">
                <label>Nhập email đã đăng ký:</label>
                <input type="email" class="form-control" name="email" required autofocus/>
            </div>
            <button type="submit" class="btn btn-primary w-100">Gửi mã OTP</button>
        </form>
    </div>
</body>
</html>
