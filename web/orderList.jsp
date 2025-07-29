<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tra cứu thông tin đặt vé</title>
    <link rel="stylesheet" href="assets/css/file.css">
    <script src="assets/js/file.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container py-4">
    <h3 class="mb-3">Tra cứu thông tin đặt vé</h3>

    <!-- Form tra cứu -->
    <form action="list-order" method="post" class="row g-3 mb-4">
        <div class="col-md-4">
            <label for="orderCode" class="form-label">Mã đơn hàng</label>
            <input type="text" class="form-control" name="orderCode" required>
        </div>
        <div class="col-md-4">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" name="email" required>
        </div>
        <div class="col-md-4">
            <label for="phone" class="form-label">Số điện thoại</label>
            <input type="text" class="form-control" name="phone" required>
        </div>
        <div class="col-12">
            <button type="submit" class="btn btn-primary">Tra cứu</button>
        </div>
    </form>

    <!-- Hiển thị lỗi nếu có -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <!-- Hiển thị thông tin đơn hàng -->
    <c:if test="${not empty order}">
        <h5>Thông tin đơn hàng</h5>
        <ul class="list-group mb-3">
            <li class="list-group-item"><b>Mã đơn hàng:</b> ${order.orderCode}</li>
            <li class="list-group-item"><b>Email:</b> ${order.email}</li>
            <li class="list-group-item"><b>Số điện thoại:</b> ${order.phoneNumber}</li>
            <li class="list-group-item"><b>Thời gian đặt:</b> ${order.orderTime}</li>
            <li class="list-group-item">
                <b>Tổng tiền:</b>
                <fmt:formatNumber value="${order.originalAmount * 1000}" type="number" groupingUsed="true"/> VNĐ
            </li>
            <li class="list-group-item"><b>Trạng thái:</b> ${order.status}</li>
        </ul>

        <!-- Danh sách vé -->
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
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>
