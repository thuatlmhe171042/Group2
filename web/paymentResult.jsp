<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Kết quả thanh toán</title>
    <link rel="stylesheet" href="assets/css/file.css">
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container mt-4">
    <h2>Kết quả thanh toán</h2>

    <c:if test="${success}">
        <div class="alert alert-success">
            <b>Thanh toán thành công!</b><br>
            Mã đơn hàng: ${order.orderCode}
        </div>

        <c:if test="${not empty order}">
            <h5>Thông tin đơn hàng</h5>
            <ul class="list-group mb-3">
                <li class="list-group-item"><b>Email:</b> ${order.email}</li>
                <li class="list-group-item"><b>Số điện thoại:</b> ${order.phoneNumber}</li>
                <li class="list-group-item"><b>Thời gian đặt:</b> ${order.orderTime}</li>
                <li class="list-group-item">
                    <b>Tổng tiền:</b>
                    <fmt:formatNumber value="${order.originalAmount * 1000}" type="number" groupingUsed="true"/> VNĐ
                </li>
                <li class="list-group-item"><b>Trạng thái:</b> ${order.status}</li>
            </ul>
        </c:if>

        <c:if test="${not empty tickets}">
            <h5>Vé và thông tin hành khách</h5>
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead class="table-light">
                        <tr>
                            <th>Mã vé</th>
                            <th>Hành khách</th>
                            <th>Loại</th>
                            <th>Ga đi</th>
                            <th>Ga đến</th>
                            <th>Thời gian</th>
                            <th>Tàu</th>
                            <th>Toa</th>
                            <th>Ghế</th>
                            <th>Loại toa</th>
                            <th>Giá vé</th>
                            <th>Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="t" items="${tickets}">
                            <tr>
                                <td>${t.ticketCode}</td>
                                <td>${t.passengerName}</td>
                                <td>${t.passengerType}</td>
                                <td>${t.fromStation}</td>
                                <td>${t.toStation}</td>
                                <td>${t.departureTime} → ${t.arrivalTime}</td>
                                <td>${t.trainCode}</td>
                                <td>${t.carriageNumber}</td>
                                <td>${t.seatNumber}</td>
                                <td>${t.carriageType}</td>
                                <td>
                                    <fmt:formatNumber value="${t.price * 1000}" type="number" groupingUsed="true"/> VNĐ
                                </td>
                                <td>${t.ticketStatus}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </c:if>

    <c:if test="${!success}">
        <div class="alert alert-danger">
            <b>Thanh toán thất bại!</b> <a href="home">Về trang chủ</a><br>
            ${message}
        </div>
    </c:if>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>
