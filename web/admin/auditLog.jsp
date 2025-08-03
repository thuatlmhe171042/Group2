<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nhật ký hệ thống</title>
    <%@ include file="../header.jsp" %>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"/>
    <style>
        body { background: #f3f6fa; }
        .log-table thead th { background: #f6faff; }
        .log-table tbody tr:hover { background: #f0f8ff; }
        .filter-row { background: #fff; border-radius: 12px; box-shadow: 0 1px 5px #ccc2; padding: 15px 20px; margin-bottom: 20px; }
    </style>
</head>
<body>
    <%@ include file="sidebar.jsp" %>
    <div class="container" style="margin-left:240px; padding-top:40px;">
        <h3 class="mb-4">Nhật ký hệ thống</h3>
        <div class="filter-row">
            <form method="get" action="auditlog" class="form-inline mb-3">
                <input type="text" class="form-control mr-2" name="userKeyword" placeholder="Nhân viên/email"
                  value="${param.userKeyword != null ? param.userKeyword : ''}"/>
                <input type="text" class="form-control mr-2" name="actionType" placeholder="Loại hành động"
                  value="${param.actionType != null ? param.actionType : ''}"/>
                <input type="text" class="form-control mr-2" name="tableName" placeholder="Bảng dữ liệu"
                  value="${param.tableName != null ? param.tableName : ''}"/>
                <input type="date" class="form-control mr-2" name="fromDate" value="${param.fromDate}"/>
                <input type="date" class="form-control mr-2" name="toDate" value="${param.toDate}"/>
                <button type="submit" class="btn btn-primary">Lọc</button>
            </form>
        </div>
        <div class="table-responsive">
            <table class="table table-bordered log-table bg-white">
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Nhân viên</th>
                        <th>Hành động</th>
                        <th>Bảng</th>
                        <th>ID Bản ghi</th>
                        <th>Chi tiết</th>
                        <th>IP</th>
                        <th>Thời gian</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="log" items="${logs}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${log.user != null ? log.user.name : '[N/A]'}</td>
                            <td>${log.actionType}</td>
                            <td>${log.tableName}</td>
                            <td>${log.recordId}</td>
                            <td>${log.changeDetails}</td>
                            <td>${log.ipAddress}</td>
                            <td>
                                <fmt:formatDate value="${log.timestamp}" pattern="dd/MM/yyyy HH:mm:ss"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty logs}">
                        <tr><td colspan="8" class="text-center">Không có dữ liệu.</td></tr>
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
