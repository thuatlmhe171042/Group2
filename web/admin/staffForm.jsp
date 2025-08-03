<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${formAction == 'add' ? 'Thêm mới' : 'Cập nhật'} nhân viên</title>
    <%@ include file="../header.jsp" %>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"/>
    <style>
        body { background: #f3f6fa; }
        .staff-form-card {
            max-width: 460px; margin: 50px auto; background: #fff;
            border-radius: 14px; box-shadow: 0 2px 12px #b1b1b17a; padding: 30px 28px;
        }
    </style>
</head>
<body>
    <%@ include file="sidebar.jsp" %>
    <div class="staff-form-card">
        <h4 class="mb-4">${formAction == 'add' ? 'Thêm mới' : 'Cập nhật'} nhân viên</h4>
        <form method="post" action="staff">
            <input type="hidden" name="action" value="${formAction}"/>
            <c:if test="${formAction == 'update'}">
                <input type="hidden" name="id" value="${staff.id}"/>
            </c:if>
            <div class="form-group">
                <label>Tên nhân viên:</label>
                <input type="text" class="form-control" name="name" required value="${staff != null ? staff.name : ''}"/>
            </div>
            <div class="form-group">
                <label>Email:</label>
                <input type="email" class="form-control" name="email" required value="${staff != null ? staff.email : ''}"/>
            </div>
            <div class="form-group">
                <label>Số điện thoại:</label>
                <input type="text" class="form-control" name="phone" required value="${staff != null ? staff.phone : ''}"/>
            </div>
            <c:if test="${formAction == 'add'}">
                <div class="form-group">
                    <label>Mật khẩu:</label>
                    <input type="password" class="form-control" name="password" required minlength="4"/>
                </div>
            </c:if>
            <button type="submit" class="btn btn-primary">${formAction == 'add' ? 'Thêm mới' : 'Cập nhật'}</button>
            <a href="staff?action=list" class="btn btn-secondary ml-2">Quay lại</a>
        </form>
    </div>
    <%@ include file="../footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
