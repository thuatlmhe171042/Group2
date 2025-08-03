<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý tài khoản nhân viên</title>
    <%@ include file="../header.jsp" %>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"/>
    <style>
        body { background: #f3f6fa; }
        .table thead th { background: #f6faff; }
        .table tbody tr:hover { background: #f0f8ff; }
        .staff-actions .btn { min-width: 95px; margin-bottom: 4px; }
        .search-row { background: #fff; border-radius: 12px; box-shadow: 0 1px 5px #ccc2; padding: 15px 20px; margin-bottom: 20px; }
    </style>
</head>
<body>
    <%@ include file="sidebar.jsp" %>
    <div class="container" style="margin-left:240px; padding-top:40px;">
        <h3 class="mb-4">Quản lý tài khoản nhân viên</h3>
        <div class="search-row">
            <form class="form-inline" method="get" action="staff">
                <input type="hidden" name="action" value="list"/>
                <input type="text" name="keyword" class="form-control mr-2" placeholder="Tìm tên, email, số điện thoại"
                    value="${param.keyword != null ? param.keyword : ''}" style="min-width:220px;"/>
                <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                <a href="staff?action=addForm" class="btn btn-success ml-2">+ Thêm mới</a>
            </form>
        </div>
        <c:if test="${not empty message}">
            <div class="alert alert-info">${message}</div>
        </c:if>
        <div class="table-responsive">
            <table class="table table-bordered mt-3 bg-white">
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Tên nhân viên</th>
                        <th>Email</th>
                        <th>Số điện thoại</th>
                        <th>Ngày tạo</th>
                        <th>Trạng thái</th>
                        <th class="text-center">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="staff" items="${staffList}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${staff.name}</td>
                            <td>${staff.email}</td>
                            <td>${staff.phone}</td>
                            <td>
                                <fmt:formatDate value="${staff.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${!staff.deleted}"><span class="badge badge-success">Kích hoạt</span></c:when>
                                    <c:otherwise><span class="badge badge-secondary">Vô hiệu</span></c:otherwise>
                                </c:choose>
                            </td>
                            <td class="staff-actions text-center">
                                <a href="staff?action=editForm&id=${staff.id}" class="btn btn-sm btn-info">Sửa</a>
                                <form method="post" action="staff" style="display:inline;">
                                    <input type="hidden" name="action" value="toggleStatus"/>
                                    <input type="hidden" name="id" value="${staff.id}"/>
                                    <input type="hidden" name="enable" value="${staff.deleted}"/>
                                    <button type="submit" class="btn btn-sm ${staff.deleted ? 'btn-success' : 'btn-danger'}"
                                      onclick="return confirm('Bạn có chắc muốn ${staff.deleted ? 'kích hoạt lại' : 'vô hiệu hóa'} tài khoản này?');">
                                      ${staff.deleted ? 'Kích hoạt' : 'Vô hiệu hóa'}
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty staffList}">
                        <tr><td colspan="7" class="text-center">Không có dữ liệu.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
    <%@ include file="../footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
