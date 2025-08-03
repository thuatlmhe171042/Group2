<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.jsp" %>
<%@include file="sidebar.jsp" %>

<div class="container" style="margin-left:240px; padding-top:30px;">
    <!-- Thêm mới quy tắc -->
    <form method="post" action="manageRefundRules" class="row g-2 mb-4">
        <input type="hidden" name="action" value="add"/>
        <div class="col"><input type="text" name="ruleName" class="form-control" placeholder="Tên quy tắc" required/></div>
        <div class="col"><input type="number" name="applyBeforeHours" class="form-control" placeholder="Áp dụng trước (giờ)" required/></div>
        <div class="col"><input type="number" step="0.01" name="refundPercentage" class="form-control" placeholder="% hoàn" required/></div>
        <div class="col"><input type="number" name="applyToPassengerTypeId" class="form-control" placeholder="ID loại khách (nếu có)"/></div>
        <div class="col"><input type="number" name="applyToCarriageTypeId" class="form-control" placeholder="ID loại toa (nếu có)"/></div>
        <div class="col"><input type="text" name="note" class="form-control" placeholder="Ghi chú"/></div>
        <div class="col"><button type="submit" class="btn btn-success">Thêm</button></div>
    </form>

    <!-- Danh sách quy tắc -->
    <table class="table table-bordered table-striped">
        <thead class="table-light">
            <tr>
                <th>Tên quy tắc</th>
                <th>Trước (giờ)</th>
                <th>% hoàn</th>
                <th>ID loại khách</th>
                <th>ID loại toa</th>
                <th>Ghi chú</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="r" items="${rules}">
            <tr>
                <td>${r.ruleName}</td>
                <td>${r.applyBeforeHours}</td>
                <td>${r.refundPercentage}</td>
                <td>${r.applyToPassengerTypeId}</td>
                <td>${r.applyToCarriageTypeId}</td>
                <td>${r.note}</td>
                <td>
                    <form method="post" action="manageRefundRules" class="d-inline">
                        <input type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="id" value="${r.id}"/>
                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Xóa quy tắc này?')">Xóa</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@include file="../footer.jsp" %>
