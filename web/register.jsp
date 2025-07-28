<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký tài khoản mới</title>
    <%@ include file="header.jsp" %>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"/>
    <style>
        body { background: #f3f6fa; }
        .register-card { max-width:430px; margin:56px auto; background:#fff; border-radius:14px; box-shadow:0 2px 12px #b1b1b17a; padding:35px 30px;}
    </style>
</head>
<body>
    <div class="register-card">
        <h3 class="mb-4 text-center">Đăng ký tài khoản</h3>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <form method="post" action="register">
            <div class="form-group">
                <label>Họ và tên:</label>
                <input type="text" class="form-control" name="name" required/>
            </div>
            <div class="form-group">
                <label>Email:</label>
                <input type="email" class="form-control" name="email" required/>
            </div>
            <div class="form-group">
                <label>Mật khẩu:</label>
                <input type="password" class="form-control" name="password" required minlength="4"/>
            </div>
            <div class="form-group">
                <label>Nhập lại mật khẩu:</label>
                <input type="password" class="form-control" name="repassword" required minlength="4"/>
            </div>
            <div class="form-group">
                <label>Số điện thoại:</label>
                <input type="text" class="form-control" name="phone"/>
            </div>
            <button type="submit" class="btn btn-success w-100">Đăng ký</button>
        </form>
        <div class="mt-3 text-center">
            <a href="login">Đã có tài khoản? Đăng nhập!</a>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>
