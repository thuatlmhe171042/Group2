<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.jsp" %>
<%@include file="sidebar.jsp" %>

<div class="container" style="margin-left:240px; padding-top:30px;">
    <h4 class="mb-3">Quản lý khuyến mãi</h4>

    <!-- Thêm mới khuyến mãi -->
    <form method="post" action="managePromotions" class="row g-2 mb-3">
        <input type="hidden" name="action" value="add" />
        
        <div class="col"><input type="text" name="promotionCode" class="form-control" placeholder="Mã khuyến mãi" required /></div>
        <div class="col"><input type="text" name="name" class="form-control" placeholder="Tên chương trình" required /></div>
        <div class="col"><input type="text" name="description" class="form-control" placeholder="Mô tả" /></div>
        
        <div class="col">
            <select name="discountType" class="form-select" required>
                <option value="percent">%</option>
                <option value="fixed">VNĐ</option>
            </select>
        </div>

        <div class="col"><input type="number" name="discountValue" step="0.01" min="0.01" class="form-control" placeholder="Giá trị giảm" required /></div>
        <div class="col"><input type="number" name="usageLimit" min="1" class="form-control" placeholder="Giới hạn lượt dùng" required /></div>

        <div class="col">
            <select name="status" class="form-select" required readonly>
                <option value="active" selected>active</option>
            </select>
        </div>

        <div class="col"><input type="date" name="startDate" class="form-control" required /></div>
        <div class="col"><input type="date" name="endDate" class="form-control" required /></div>

        <div class="col"><button type="submit" class="btn btn-success">Thêm</button></div>
    </form>

    <!-- Danh sách khuyến mãi -->
    <table class="table table-bordered table-hover align-middle shadow-sm">
        <thead class="table-light">
            <tr>
                <th>Mã</th><th>Tên</th><th>Giá trị</th><th>Loại</th><th>Giới hạn</th>
                <th>Đã dùng</th><th>Trạng thái</th><th>Bắt đầu</th><th>Kết thúc</th><th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${promotions}">
            <tr>
                <td>${p.promotionCode}</td>
                <td>${p.name}</td>
                <td>${p.discountValue}</td>
                <td>${p.discountType}</td>
                <td>${p.usageLimit}</td>
                <td>${p.currentUsageCount}</td>
                <td>
                    <span class="badge ${p.status == 'active' ? 'bg-success' : 'bg-secondary'}">
                        ${p.status}
                    </span>
                </td>
                <td>${p.startDate}</td>
                <td>${p.endDate}</td>
                <td>
                    <form method="post" action="managePromotions" class="d-inline">
                        <input type="hidden" name="action" value="delete" />
                        <input type="hidden" name="id" value="${p.id}" />
                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Xóa khuyến mãi này?')">Xóa</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@include file="../footer.jsp" %>
