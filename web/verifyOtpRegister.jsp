<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác nhận OTP đăng ký</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"/>
</head>
<body>
    <div class="container" style="max-width:400px; margin:60px auto;">
        <h3 class="mb-4 text-center">Xác nhận đăng ký</h3>
        <c:if test="${not empty message}">
            <div class="alert alert-info">${message}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <form method="post" action="verifyOtpRegister">
            <input type="hidden" name="email" value="${email}"/>
            <div class="form-group">
                <label>Mã OTP đã gửi về email:</label>
                <input type="text" class="form-control" name="otp" required maxlength="6"/>
            </div>
            <button type="submit" class="btn btn-primary w-100">Xác nhận đăng ký</button>
        </form>
    </div>
</body>
</html>
