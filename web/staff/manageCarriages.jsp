<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.jsp" %>
<%@include file="sidebar.jsp" %>

<div class="container" style="margin-left:240px; padding-top:30px;">
    <form method="get" action="manageCarriages" class="mb-3">
        <select name="trainId" class="form-select" onchange="this.form.submit()">
            <option value="">-- Chọn tàu --</option>
            <c:forEach var="train" items="${trains}">
                <option value="${train.id}" ${train.id == selectedTrainId ? 'selected' : ''}>
                    ${train.trainCode} (${train.trainName})
                </option>
            </c:forEach>
        </select>
    </form>
    <c:if test="${selectedTrainId != null}">
    <table class="table table-bordered align-middle shadow-sm">
        <thead>
            <tr>
                <th>Toa (Số & Loại)</th>
                <th>Số ghế</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="carriage" items="${carriages}">
            <tr>
                <td>
                    Toa ${carriage.carriageNumber} - ${carriageTypeMap[carriage.carriageTypeId].typeName}
                </td>
                <td>${seatCountMap[carriage.id]}</td>
                <td>
                    <form method="post" action="manageCarriages" class="d-inline">
                        <input type="hidden" name="action" value="deleteCarriage"/>
                        <input type="hidden" name="carriageId" value="${carriage.id}"/>
                        <input type="hidden" name="trainId" value="${selectedTrainId}"/>
                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Xóa toa này?')">Xóa</button>
                    </form>
                    <button class="btn btn-info btn-sm" type="button" data-bs-toggle="collapse" data-bs-target="#seats${carriage.id}">Ghế</button>
                </td>
            </tr>
            <tr class="collapse" id="seats${carriage.id}">
                <td colspan="3">
                    <table class="table table-sm mb-0">
                        <thead>
                            <tr>
                                <th>Số ghế</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="seat" items="${seatMap[carriage.id]}">
                            <tr>
                                <td>${seat.seatNumber}</td>
                                <td>
                                    <form method="post" action="manageCarriages" class="d-inline">
                                        <input type="hidden" name="action" value="deleteSeat"/>
                                        <input type="hidden" name="seatId" value="${seat.id}"/>
                                        <input type="hidden" name="trainId" value="${selectedTrainId}"/>
                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Xóa ghế này?')">Xóa</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!-- Thêm ghế -->
                    <form method="post" action="manageCarriages" class="row g-2 mt-1">
                        <input type="hidden" name="action" value="addSeat"/>
                        <input type="hidden" name="carriageId" value="${carriage.id}"/>
                        <input type="hidden" name="trainId" value="${selectedTrainId}"/>
                        <div class="col">
                            <input type="text" name="seatNumber" class="form-control form-control-sm" placeholder="Số ghế" required/>
                        </div>
                        <div class="col">
                            <button type="submit" class="btn btn-success btn-sm">Thêm ghế</button>
                        </div>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <!-- Thêm toa mới -->
    <form method="post" action="manageCarriages" class="row g-2 mb-2">
        <input type="hidden" name="action" value="addCarriage"/>
        <input type="hidden" name="trainId" value="${selectedTrainId}"/>
        <div class="col"><input type="number" name="carriageNumber" class="form-control" placeholder="Số toa" required/></div>
        <div class="col">
            <select name="carriageTypeId" class="form-select" required>
                <c:forEach var="type" items="${carriageTypeMap.values()}">
                    <option value="${type.id}">${type.typeName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col"><button type="submit" class="btn btn-success">Thêm toa</button></div>
    </form>
    </c:if>
</div>
<%@include file="../footer.jsp" %>
