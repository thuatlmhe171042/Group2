<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết lịch trình</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="file.css">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="container mt-4">
    <h2 class="mb-4 text-primary">Chi tiết lịch trình tàu</h2>

    <div class="card p-3 mb-4">
        <h5 class="mb-3">Thông tin lịch trình</h5>
        <div>
            <b>Mã tàu:</b> ${train.trainCode} <br>
            <b>Tên tàu:</b> ${train.trainName} <br>
            <b>Khởi hành:</b> ${schedule.departureTime} <br>
            <b>Đến nơi:</b> ${schedule.arrivalTime} <br>
            <b>Trạng thái:</b> ${schedule.status}
        </div>
    </div>

    <div class="card p-3 mb-4">
        <h5 class="mb-3">Danh sách điểm dừng</h5>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Thứ tự</th>
                    <th>Ga dừng</th>
                    <th>Đến lúc</th>
                    <th>Đi lúc</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="stop" items="${stops}">
                    <tr>
                        <td>${stop.stopSequence}</td>
                        <td>
                            <c:out value="${stationIdMap[stop.stationId].stationName}" />
                            (<c:out value="${stationIdMap[stop.stationId].stationCode}" />)
                        </td>
                        <td>${stop.arrivalTime}</td>
                        <td>${stop.departureTime}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="card p-3 mb-4">
        <h5 class="mb-3">Loại toa & Giá vé</h5>
        <table class="table table-striped table-bordered">
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Loại toa</th>
                    <th>Giá vé</th>
                    <th>Ghi chú</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="price" items="${prices}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>
                            <c:out value="${carriageTypeMap[price.carriageTypeId].typeName}" />
                        </td>
                        <td>
                            <fmt:formatNumber value="${price.price}" type="currency" currencySymbol="₫"/>
                        </td>
                        <td>
                            <c:out value="${carriageTypeMap[price.carriageTypeId].description}" />
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
