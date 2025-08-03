<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.jsp" %>
<%@include file="sidebar.jsp" %>

<div class="container" style="margin-left:240px; padding-top:30px;">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3>Quản lý loại hành khách & quy tắc giá</h3>
    </div>

    <table class="table table-bordered table-hover shadow-sm align-middle">
        <thead class="table-light">
            <tr>
                <th>Loại hành khách</th>
                <th>Mô tả</th>
                <th>Loại giảm</th>
                <th>Giá trị giảm</th>
                <th>Trạng thái</th>
                <th style="width:160px;" class="text-center">Thao tác</th>
            </tr>
        </thead>
        <tbody>

        <!-- Danh sách loại hành khách -->
        <c:forEach var="row" items="${rows}">
        <tr>
            <form action="managePassengerTypes" method="post" class="d-flex">
                <input type="hidden" name="passengerTypeId" value="${row.passengerTypeId}" />
                <input type="hidden" name="ruleId" value="${row.ruleId}" />
                <input type="hidden" name="toActive" value="${row.ruleActive}" />

                <td>
                    <input type="text" class="form-control" name="typeName" value="${row.typeName}" required />
                </td>
                <td>
                    <input type="text" class="form-control" name="description" value="${row.description}" />
                </td>
                <td>
                    <select class="form-select" name="discountType">
                        <option value="percent" ${row.discountType == 'percent' ? 'selected' : ''}>%</option>
                        <option value="fixed" ${row.discountType == 'fixed' ? 'selected' : ''}>VNĐ</option>
                    </select>
                </td>
                <td>
                    <input type="number" class="form-control" name="discountValue" value="${row.discountValue}" min="0" />
                </td>
                <td>
                    <c:choose>
                        <c:when test="${row.ruleActive}">
                            <span class="badge bg-success">Đang áp dụng</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge bg-secondary">Đã tắt</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <button type="submit" name="action" value="edit" class="btn btn-warning btn-sm mb-1">
                        <i class="fas fa-save"></i> Lưu
                    </button>
                    <br />
                    <c:choose>
                        <c:when test="${row.ruleActive}">
                            <button type="submit" name="action" value="toggleRule"
                                    class="btn btn-danger btn-sm"
                                    onclick="this.form.toActive.value='false';">
                                <i class="fas fa-times"></i> Vô hiệu hóa
                            </button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" name="action" value="toggleRule"
                                    class="btn btn-success btn-sm"
                                    onclick="this.form.toActive.value='true';">
                                <i class="fas fa-check"></i> Kích hoạt
                            </button>
                        </c:otherwise>
                    </c:choose>
                </td>
            </form>
        </tr>
        </c:forEach>

        <!-- Dòng thêm mới -->
        <tr>
            <form action="managePassengerTypes" method="post">
                <input type="hidden" name="action" value="add"/>
                <td><input type="text" class="form-control" name="typeName" placeholder="Nhập tên loại khách" required/></td>
                <td><input type="text" class="form-control" name="description" placeholder="Mô tả" /></td>
                <td>
                    <select class="form-select" name="discountType">
                        <option value="percent">%</option>
                        <option value="fixed">VNĐ</option>
                    </select>
                </td>
                <td><input type="number" class="form-control" name="discountValue" min="0" placeholder="Giá trị giảm"/></td>
                <td><span class="badge bg-secondary">Mặc định</span></td>
                <td class="text-center">
                    <button type="submit" class="btn btn-success btn-sm"><i class="fas fa-plus"></i> Thêm</button>
                </td>
            </form>
        </tr>

        </tbody>
    </table>
</div>

<%@include file="../footer.jsp" %>
