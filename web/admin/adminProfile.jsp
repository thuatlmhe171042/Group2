<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hồ sơ cá nhân Admin</title>
    <%@ include file="../header.jsp" %>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"/>
    <style>
        body { background: #f3f6fa; }
        .profile-card { max-width: 540px; margin: 48px auto; background: #fff; border-radius: 14px; box-shadow: 0 2px 12px #b1b1b17a; padding: 35px 30px; }
        .form-section { margin-bottom: 40px; }
    </style>
</head>
<body>
    <%@ include file="sidebar.jsp" %>
    <div class="profile-card">
        <h3 class="mb-4 text-center">Hồ sơ tài khoản Admin</h3>

        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- Form cập nhật thông tin cá nhân -->
        <div class="form-section">
            <form method="post" action="profile">
                <input type="hidden" name="action" value="updateInfo"/>
                <div class="form-group">
                    <label>Họ và tên:</label>
                    <input type="text" class="form-control" name="name" required value="${admin.name}"/>
                </div>
                <div class="form-group">
        <label>Số điện thoại:</label>
        <input type="text" class="form-control" name="phone" required value="${admin.phone}"/>
        </div>
                <div class="form-group">
                    <label>Email hiện tại:</label>
                    <input type="email" class="form-control" value="${admin.email}" disabled/>
                </div>
                <button type="submit" class="btn btn-primary">Cập nhật thông tin</button>
            </form>
        </div>

        <hr>

        <!-- Đổi email qua OTP -->
        <div class="form-section">
            <h5 class="mb-3">Đổi email đăng nhập</h5>
            <c:choose>
                <c:when test="${empty requestScope.otpSentEmail}">
                    <form method="post" action="profile">
                        <input type="hidden" name="action" value="sendOtpEmail"/>
                        <div class="form-group">
                            <label>Email mới:</label>
                            <input type="email" class="form-control" name="newEmail" required/>
                        </div>
                        <button type="submit" class="btn btn-info">Gửi mã OTP tới email cũ</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form method="post" action="profile">
                        <input type="hidden" name="action" value="changeEmail"/>
                        <input type="hidden" name="newEmail" value="${newEmail}"/>
                        <div class="form-group">
                            <label>Nhập mã OTP (gửi về email cũ):</label>
                            <input type="text" class="form-control" name="otp" required maxlength="6"/>
                        </div>
                        <button type="submit" class="btn btn-success">Xác nhận đổi email</button>
                        <a href="profile" class="btn btn-secondary ml-2">Hủy</a>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>

        <hr>

        <!-- Đổi mật khẩu bằng OTP gửi email -->
        <div class="form-section">
            <h5 class="mb-3">Đổi mật khẩu</h5>
            <c:choose>
                <c:when test="${empty requestScope.otpSent}">
                    <form method="post" action="profile">
                        <input type="hidden" name="action" value="sendOtp"/>
                        <div class="form-group">
                            <label>Mật khẩu hiện tại:</label>
                            <input type="password" class="form-control" name="oldPassword" required minlength="4"/>
                        </div>
                        <button type="submit" class="btn btn-info">Gửi mã OTP tới email</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form method="post" action="profile">
                        <input type="hidden" name="action" value="changePassword"/>
                        <div class="form-group">
                            <label>Mã OTP (gửi về email):</label>
                            <input type="text" class="form-control" name="otp" required maxlength="6"/>
                        </div>
                        <div class="form-group">
                            <label>Mật khẩu mới:</label>
                            <input type="password" class="form-control" name="newPassword" required minlength="4"/>
                        </div>
                        <div class="form-group">
                            <label>Nhập lại mật khẩu mới:</label>
                            <input type="password" class="form-control" name="confirmPassword" required minlength="4"/>
                        </div>
                        <button type="submit" class="btn btn-success">Đổi mật khẩu</button>
                        <a href="profile" class="btn btn-secondary ml-2">Hủy</a>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <%@ include file="../footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
