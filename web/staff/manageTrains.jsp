<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.jsp" %>
<%@include file="sidebar.jsp" %>

<div class="container">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3>Quản lý tàu</h3>
        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addTrainModal">
            <i class="fas fa-plus"></i> Thêm tàu mới
        </button>
    </div>
    
    <table class="table table-bordered table-hover shadow-sm">
        <thead class="table-light">
            <tr>
                <th>Mã tàu</th>
                <th>Tên tàu</th>
                <th>Mô tả</th>
                <th>Trạng thái</th>
                <th class="text-center" style="width: 170px;">Thao tác</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="train" items="${trains}">
            <tr class="<c:if test='${train.isDeleted}'>table-danger</c:if>">
                <td>${train.trainCode}</td>
                <td>${train.trainName}</td>
                <td>${train.description}</td>
                <td>
                    <c:choose>
                        <c:when test="${train.isDeleted}"><span class="badge bg-danger">Đã khóa</span></c:when>
                        <c:otherwise><span class="badge bg-success">Đang hoạt động</span></c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <button 
                        class="btn btn-warning btn-sm me-1"
                        data-bs-toggle="modal"
                        data-bs-target="#editTrainModal"
                        data-id="${train.id}"
                        data-code="${train.trainCode}"
                        data-name="${train.trainName}"
                        data-desc="${train.description}">
                        <i class="fas fa-edit"></i> Sửa
                    </button>
                    <form action="manageTrains" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="toggleStatus"/>
                        <input type="hidden" name="id" value="${train.id}"/>
                        <button type="submit" class="btn ${train.isDeleted ? 'btn-success' : 'btn-danger'} btn-sm">
                            <i class="fas ${train.isDeleted ? 'fa-lock-open' : 'fa-lock'}"></i>
                            ${train.isDeleted ? 'Mở khóa' : 'Khóa'}
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Modal thêm tàu mới -->
<div class="modal fade" id="addTrainModal" tabindex="-1" aria-labelledby="addTrainModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="manageTrains" method="post" class="modal-content">
            <input type="hidden" name="action" value="add"/>
            <div class="modal-header">
                <h5 class="modal-title" id="addTrainModalLabel">Thêm tàu mới</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label for="trainCode" class="form-label">Mã tàu</label>
                    <input type="text" class="form-control" id="trainCode" name="trainCode" required>
                </div>
                <div class="mb-3">
                    <label for="trainName" class="form-label">Tên tàu</label>
                    <input type="text" class="form-control" id="trainName" name="trainName" required>
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Mô tả</label>
                    <textarea class="form-control" id="description" name="description" rows="2"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">Thêm mới</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal sửa tàu -->
<div class="modal fade" id="editTrainModal" tabindex="-1" aria-labelledby="editTrainModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="manageTrains" method="post" class="modal-content">
            <input type="hidden" name="action" value="edit"/>
            <input type="hidden" name="id" id="editTrainId">
            <div class="modal-header">
                <h5 class="modal-title" id="editTrainModalLabel">Sửa thông tin tàu</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label for="editTrainCode" class="form-label">Mã tàu</label>
                    <input type="text" class="form-control" id="editTrainCode" name="trainCode" required>
                </div>
                <div class="mb-3">
                    <label for="editTrainName" class="form-label">Tên tàu</label>
                    <input type="text" class="form-control" id="editTrainName" name="trainName" required>
                </div>
                <div class="mb-3">
                    <label for="editDescription" class="form-label">Mô tả</label>
                    <textarea class="form-control" id="editDescription" name="description" rows="2"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-warning">Cập nhật</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
            </div>
        </form>
    </div>
</div>

<%@include file="../footer.jsp" %>

<script>
    // Tự động đổ dữ liệu vào modal sửa khi bấm nút Sửa
    var editTrainModal = document.getElementById('editTrainModal');
    if (editTrainModal) {
        editTrainModal.addEventListener('show.bs.modal', function (event) {
            var button = event.relatedTarget;
            document.getElementById('editTrainId').value = button.getAttribute('data-id');
            document.getElementById('editTrainCode').value = button.getAttribute('data-code');
            document.getElementById('editTrainName').value = button.getAttribute('data-name');
            document.getElementById('editDescription').value = button.getAttribute('data-desc');
        });
    }
</script>
