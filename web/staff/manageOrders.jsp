<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.jsp" %>
<%@include file="sidebar.jsp" %>
<div class="container" style="margin-left:240px; padding-top:30px;">
    <!-- Form tìm kiếm -->
    <form method="get" action="manageOrders" class="mb-3 row">
        <div class="col"><input type="text" name="keyword" class="form-control" placeholder="Mã đơn/email/điện thoại"/></div>
        <div class="col">
            <select name="status" class="form-select">
                <option value="">-- Tất cả --</option>
                <option value="pending">Chờ thanh toán</option>
                <option value="paid">Đã thanh toán</option>
                <option value="cancelled">Đã hủy</option>
            </select>
        </div>
        <div class="col"><button type="submit" class="btn btn-primary">Tìm kiếm</button></div>
    </form>
    <!-- Danh sách đơn hàng -->
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Mã ĐH</th><th>Khách</th><th>Điện thoại</th><th>Email</th>
                <th>Trạng thái</th><th>Thành tiền</th><th>Ngày đặt</th><th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="o" items="${orders}">
            <tr>
                <td>${o.orderCode}</td>
                <td>${o.userId}</td>
                <td>${o.phoneNumber}</td>
                <td>${o.email}</td>
                <td>${o.status}</td>
                <td>
                    <c:set var="finalAmount" value="${o.originalAmount - o.discountAmount}"/>
                    <c:out value="${finalAmount}"/>
                </td>
                <td>${o.orderTime}</td>
                <td>
                    <button class="btn btn-info btn-sm" type="button" data-bs-toggle="collapse" data-bs-target="#tickets${o.id}">Vé</button>
                    <form method="post" action="manageOrders" class="d-inline">
                        <input type="hidden" name="action" value="cancelOrder"/>
                        <input type="hidden" name="orderId" value="${o.id}"/>
                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Hủy đơn này?')">Hủy đơn</button>
                    </form>
                </td>
            </tr>
            <tr class="collapse" id="tickets${o.id}">
                <td colspan="8">
                    <table class="table table-sm mb-0">
                        <thead>
                            <tr>
                                <th>Mã vé</th><th>Hành khách</th><th>Chỗ</th><th>Giá</th>
                                <th>Trạng thái</th><th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="t" items="${ticketMap[o.id]}">
                            <tr>
                                <td>${t.ticketCode}</td>
                                <td>${t.passengerName}</td>
                                <td>${t.seatId}</td>
                                <td>${t.price}</td>
                                <td>${t.ticketStatus}</td>
                                <td>
                                    <form method="post" action="manageOrders" class="d-inline">
                                        <input type="hidden" name="action" value="cancelTicket"/>
                                        <input type="hidden" name="ticketId" value="${t.id}"/>
                                        <button type="submit" class="btn btn-warning btn-sm" onclick="return confirm('Hủy vé này?')">Hủy vé</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@include file="../footer.jsp" %>
