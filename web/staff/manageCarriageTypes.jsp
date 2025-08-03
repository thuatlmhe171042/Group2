<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header.jsp" %>

<div class="container-fluid">
  <div class="row">
    <div class="col-md-2 d-none d-md-block" style="min-width:200px;">
      <%@include file="sidebar.jsp" %>
    </div>
    <div class="col-md-10 col-12 pt-4">
      <div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
        <h3 class="mb-0">Quản lý loại toa & Giá cơ bản/km</h3>
        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addTypeModal">
          <i class="fas fa-plus"></i> Thêm loại toa mới
        </button>
      </div>
      <div class="table-responsive">
        <table class="table table-bordered table-hover shadow-sm align-middle">
          <thead class="table-light">
            <tr>
              <th>Tên loại toa</th>
              <th>Giá cơ bản (VND/km)</th>
              <th class="text-center" style="width:230px;">Thao tác</th>
            </tr>
          </thead>
          <tbody>
          <c:forEach var="type" items="${carriageTypes}">
            <tr>
              <td>${type.typeName}</td>
              <td>
                <c:choose>
                  <c:when test="${type.basePricePerKm != null}">
                    <fmt:formatNumber value="${type.basePricePerKm}" type="number" maxFractionDigits="0"/>
                  </c:when>
                  <c:otherwise>
                    <span class="text-danger">Chưa thiết lập</span>
                  </c:otherwise>
                </c:choose>
              </td>
              <td class="text-center">
                <button class="btn btn-warning btn-sm"
                        data-bs-toggle="modal"
                        data-bs-target="#editTypeModal"
                        data-id="${type.id}"
                        data-name="${type.typeName}"
                        data-desc="${type.description}"
                        data-baseprice="${type.basePricePerKm}">
                  <i class="fas fa-edit"></i> Sửa
                </button>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="addTypeModal" tabindex="-1" aria-labelledby="addTypeModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="manageCarriageTypes" method="post" class="modal-content">
            <input type="hidden" name="action" value="add"/>
            <div class="modal-header">
                <h5 class="modal-title" id="addTypeModalLabel">Thêm loại toa mới</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label for="addTypeName" class="form-label">Tên loại toa</label>
                    <input type="text" class="form-control" id="addTypeName" name="typeName" required>
                </div>
                <div class="mb-3">
                    <label for="addDescription" class="form-label">Mô tả</label>
                    <textarea class="form-control" id="addDescription" name="description" rows="2"></textarea>
                </div>
                <div class="mb-3">
                    <label for="addBasePricePerKm" class="form-label">Giá cơ bản (VND/km)</label>
                    <input type="number" class="form-control" id="addBasePricePerKm" name="basePricePerKm" min="0" step="100" required>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">Thêm mới</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal Sửa loại toa -->
<div class="modal fade" id="editTypeModal" tabindex="-1" aria-labelledby="editTypeModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="manageCarriageTypes" method="post" class="modal-content">
            <input type="hidden" name="action" value="edit"/>
            <input type="hidden" name="id" id="editTypeId">
            <div class="modal-header">
                <h5 class="modal-title" id="editTypeModalLabel">Sửa loại toa</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label for="editTypeName" class="form-label">Tên loại toa</label>
                    <input type="text" class="form-control" id="editTypeName" name="typeName" required>
                </div>
                <div class="mb-3">
                    <label for="editDescription" class="form-label">Mô tả</label>
                    <textarea class="form-control" id="editDescription" name="description" rows="2"></textarea>
                </div>
                <div class="mb-3">
                    <label for="editBasePricePerKm" class="form-label">Giá cơ bản (VND/km)</label>
                    <input type="number" class="form-control" id="editBasePricePerKm" name="basePricePerKm" min="0" step="100" required>
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
    // Truyền dữ liệu lên modal sửa khi bấm Sửa
    var editTypeModal = document.getElementById('editTypeModal');
    if (editTypeModal) {
        editTypeModal.addEventListener('show.bs.modal', function (event) {
            var button = event.relatedTarget;
            document.getElementById('editTypeId').value = button.getAttribute('data-id');
            document.getElementById('editTypeName').value = button.getAttribute('data-name');
            document.getElementById('editDescription').value = button.getAttribute('data-desc');
            document.getElementById('editBasePricePerKm').value = button.getAttribute('data-baseprice') || '';
        });
    }
</script>
