<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Yêu cầu hoàn vé</title>
    <link rel="stylesheet" href="assets/css/file.css">
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container py-4">
    <h3>Tra cứu đơn hàng để hoàn vé</h3>

    <!-- Form tra cứu đơn hàng -->
    <form action="refund-list" method="post" class="row g-3 mb-4">
        <div class="col-md-4">
            <label>Mã đơn hàng</label>
            <input type="text" class="form-control" name="orderCode" required>
        </div>
        <div class="col-md-4">
            <label>Email</label>
            <input type="email" class="form-control" name="email" required>
        </div>
        <div class="col-md-4">
            <label>Số điện thoại</label>
            <input type="text" class="form-control" name="phone" required>
        </div>
        <div class="col-12">
            <button class="btn btn-primary">Tra cứu</button>
        </div>
    </form>

    <!-- Thông báo -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>

    <!-- Hiển thị thông tin đơn hàng -->
    <c:if test="${not empty order}">
        <h5>Thông tin đơn hàng</h5>
        <ul class="list-group mb-3">
            <li class="list-group-item"><b>Mã đơn:</b> ${order.orderCode}</li>
            <li class="list-group-item"><b>Email:</b> ${order.email}</li>
            <li class="list-group-item"><b>SĐT:</b> ${order.phoneNumber}</li>
            <li class="list-group-item"><b>Trạng thái:</b> ${order.status}</li>
        </ul>

        <!-- Danh sách vé -->
        <h5>Danh sách vé</h5>
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
                        <th>Giá vé</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
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
                            <td>
                                <fmt:formatNumber value="${t.price * 1000}" type="number" groupingUsed="true"/> VNĐ
                            </td>
                            <td>${t.ticketStatus}</td>
                            <td>
                                <c:if test="${t.ticketStatus eq 'paid' || t.ticketStatus eq 'pending_payment'}">

                                    <form action="refund-ticket" method="post" style="display:inline;">
                                        <input type="hidden" name="ticketCode" value="${t.ticketCode}" />
                                        <input type="hidden" name="orderCode" value="${order.orderCode}" />
                                        <input type="hidden" name="email" value="${order.email}" />
                                        <input type="hidden" name="phone" value="${order.phoneNumber}" />
                                        <button type="submit" class="btn btn-sm btn-warning">Trả vé</button>
                                    </form>
                                </c:if>
                            </td>
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
