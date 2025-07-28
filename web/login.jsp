<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập hệ thống</title>
    <%@ include file="header.jsp" %>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"/>
    <style>
        body { background: #f3f6fa; }
        .login-container {
            max-width: 420px; margin: 60px auto; background: #fff;
            border-radius: 14px; box-shadow: 0 2px 12px #b1b1b17a; padding: 35px 30px;
        }
    </style>
</head>
<body>
    <div class="login-container card p-4 mt-5">
        <h3 class="mb-4 text-center">Đăng nhập hệ thống</h3>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">${errorMsg}</div>
        </c:if>
        <form method="post" action="login">
            <div class="form-group">
                <label>Email:</label>
                <input type="email" class="form-control" name="email" required autofocus/>
            </div>
            <div class="form-group">
                <label>Mật khẩu:</label>
                <input type="password" class="form-control" name="password" required minlength="4"/>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Đăng nhập</button>
        </form>
        <div class="mt-3 text-center">
            <a href="forgotPassword.jsp" class="mr-3">Quên mật khẩu?</a>
            <span class="text-muted">|</span>
            <a href="register" class="ml-3 btn btn-outline-success btn-sm">Đăng ký tài khoản mới</a>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
