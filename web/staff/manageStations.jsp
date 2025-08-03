<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.jsp" %>
<%@include file="sidebar.jsp" %>

<!-- Nút toggle sidebar cho mobile -->
<button class="btn btn-outline-secondary d-md-none m-2" type="button" data-bs-toggle="offcanvas" data-bs-target="#staffSidebar">
    <i class="fas fa-bars"></i>
</button>

<div class="container"  ">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3>Quản lý ga</h3>
        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addStationModal">
            <i class="fas fa-plus"></i> Thêm ga mới
        </button>
    </div>

    <table class="table table-bordered table-hover shadow-sm">
        <thead class="table-light">
            <tr>
                <th>Mã ga</th>
                <th>Thành phố</th>
                <th>Tên ga</th>
                <th>Trạng thái</th>
                <th class="text-center" style="width: 150px;">Thao tác</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="station" items="${stations}">
            <tr class="<c:if test='${station.isDeleted}'>table-danger</c:if>">
                <td>${station.stationCode}</td>
                <td>${station.stationName}</td>
                <td>${station.city}</td>
                <td>
                    <c:choose>
                        <c:when test="${station.isDeleted}"><span class="badge bg-danger">Đã khóa</span></c:when>
                        <c:otherwise><span class="badge bg-success">Đang hoạt động</span></c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <button
                        class="btn btn-warning btn-sm me-1"
                        data-bs-toggle="modal"
                        data-bs-target="#editStationModal"
                        data-id="${station.id}"
                        data-code="${station.stationCode}"
                        data-name="${station.stationName}"
                        data-city="${station.city}"
                        data-address="${station.address}"
                        data-lat="${station.latitude}"
                        data-lng="${station.longitude}">
                        <i class="fas fa-edit"></i> Sửa
                    </button>
                    <form action="manageStations" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="toggleStatus"/>
                        <input type="hidden" name="id" value="${station.id}"/>
                        <button type="submit" class="btn ${station.isDeleted ? 'btn-success' : 'btn-danger'} btn-sm">
                            <i class="fas ${station.isDeleted ? 'fa-lock-open' : 'fa-lock'}"></i>
                            ${station.isDeleted ? 'Mở khóa' : 'Khóa'}
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Modal thêm ga mới -->
<div class="modal fade" id="addStationModal" tabindex="-1" aria-labelledby="addStationModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="manageStations" method="post" class="modal-content">
            <input type="hidden" name="action" value="add"/>
            <div class="modal-header">
                <h5 class="modal-title" id="addStationModalLabel">Thêm ga mới</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label for="stationCode" class="form-label">Mã ga</label>
                    <input type="text" class="form-control" id="stationCode" name="stationCode" required>
                </div>
                <div class="mb-3">
                    <label for="stationName" class="form-label">Tên ga</label>
                    <input type="text" class="form-control" id="stationName" name="stationName" required>
                </div>
                <div class="mb-3">
                    <label for="city" class="form-label">Thành phố</label>
                    <input type="text" class="form-control" id="city" name="city">
                </div>
                <div class="mb-3">
                    <label for="address" class="form-label">Địa chỉ</label>
                    <input type="text" class="form-control" id="address" name="address">
                </div>
                <div class="mb-3">
                    <label for="latitude" class="form-label">Vĩ độ (Latitude)</label>
                    <input type="number" step="any" class="form-control" id="latitude" name="latitude" required>
                </div>
                <div class="mb-3">
                    <label for="longitude" class="form-label">Kinh độ (Longitude)</label>
                    <input type="number" step="any" class="form-control" id="longitude" name="longitude" required>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">Thêm mới</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal sửa ga -->
<div class="modal fade" id="editStationModal" tabindex="-1" aria-labelledby="editStationModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="manageStations" method="post" class="modal-content">
            <input type="hidden" name="action" value="edit"/>
            <input type="hidden" name="id" id="editStationId">
            <div class="modal-header">
                <h5 class="modal-title" id="editStationModalLabel">Sửa thông tin ga</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label for="editStationCode" class="form-label">Mã ga</label>
                    <input type="text" class="form-control" id="editStationCode" name="stationCode" required>
                </div>
                <div class="mb-3">
                    <label for="editStationName" class="form-label">Thành phố</label>
                    <input type="text" class="form-control" id="editStationName" name="stationName" required>
                </div>
                <div class="mb-3">
                    <label for="editCity" class="form-label">Địa chỉ</label>
                    <input type="text" class="form-control" id="editCity" name="city">
                </div>
                <div class="mb-3">
                    <label for="editAddress" class="form-label">Tên ga</label>
                    <input type="text" class="form-control" id="editAddress" name="address">
                </div>
                <div class="mb-3">
                    <label for="editLatitude" class="form-label">Vĩ độ (Latitude)</label>
                    <input type="number" step="any" class="form-control" id="editLatitude" name="latitude" required>
                </div>
                <div class="mb-3">
                    <label for="editLongitude" class="form-label">Kinh độ (Longitude)</label>
                    <input type="number" step="any" class="form-control" id="editLongitude" name="longitude" required>
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
    // Đổ dữ liệu vào modal sửa ga khi bấm nút Sửa
    var editStationModal = document.getElementById('editStationModal');
    if (editStationModal) {
        editStationModal.addEventListener('show.bs.modal', function (event) {
            var button = event.relatedTarget;
            document.getElementById('editStationId').value = button.getAttribute('data-id');
            document.getElementById('editStationCode').value = button.getAttribute('data-code');
            document.getElementById('editStationName').value = button.getAttribute('data-name');
            document.getElementById('editCity').value = button.getAttribute('data-city');
            document.getElementById('editAddress').value = button.getAttribute('data-address');
            document.getElementById('editLatitude').value = button.getAttribute('data-lat');
            document.getElementById('editLongitude').value = button.getAttribute('data-lng');
        });
    }
</script>
