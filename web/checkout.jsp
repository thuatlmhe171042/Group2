<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thanh toán đơn hàng</title>
    <link rel="stylesheet" href="file.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="container mt-4 mb-5">
    <h2 class="mb-4 text-primary">Thanh toán đơn hàng</h2>
    <form method="post" action="placeOrder" autocomplete="off">
        <table class="table table-bordered align-middle text-center">
            <thead>
                <tr>
                    <th>Họ tên</th>
                    <th>Loại khách</th>
                    <th>CMND/CCCD</th>
                    <th>Ngày sinh</th>
                    <th>Thông tin chỗ</th>
                    <th>Giá vé</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="ticket" items="${ticketList}" varStatus="vs">
                    <tr>
                        <td>
                            <input name="passengerName${vs.index}" class="form-control" required>
                        </td>
                        <td>
                            <select name="passengerTypeId${vs.index}" class="form-select" required>
                                <c:forEach var="type" items="${passengerTypeRules}">
                                    <option value="${type.passengerTypeId}"
                                            data-discounttype="${type.discountType}" 
                                            data-discountvalue="${type.discountValue}">
                                        ${type.typeName}
                                        <c:if test="${type.discountValue != null && type.discountValue > 0}">
    <c:choose>
        <c:when test="${type.discountType == 'percentage' || type.discountType == 'percent'}">
            (-${type.discountValue}%)
        </c:when>
        <c:when test="${type.discountType == 'fixed'}">
            (-${type.discountValue}₫)
        </c:when>
    </c:choose>
</c:if>

                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <input name="passengerIdNumber${vs.index}" class="form-control" required
                                   pattern="^[0-9]{9}|[0-9]{12}$" title="Nhập đúng số CMND/CCCD">
                        </td>
                        <td>
                            <input name="passengerDateOfBirth${vs.index}" type="date" class="form-control" required>
                        </td>
                        <td style="text-align:left;">
                            <b>Mã tàu:</b> ${ticket.trainCode} <br>
                            <b>Ga đi:</b> ${ticket.fromStationName} <br>
                            <b>Ga đến:</b> ${ticket.toStationName} <br>
                            <b>KH:</b> ${ticket.departTime}<br>
                            <b>ĐN:</b> ${ticket.arrivalTime}<br>
                            <b>Toa:</b> ${ticket.carriageNumber} (${ticket.carriageType})<br>
                            <b>Ghế:</b> ${ticket.seatNumber}
                            <input type="hidden" name="seatId${vs.index}" value="${ticket.seatId}">
                            <input type="hidden" name="carriageId${vs.index}" value="${ticket.carriageId}">
                            <input type="hidden" name="scheduleId${vs.index}" value="${ticket.scheduleId}">
                            <input type="hidden" name="departureStopId${vs.index}" value="${ticket.departureStopId}">
                            <input type="hidden" name="arrivalStopId${vs.index}" value="${ticket.arrivalStopId}">
                        </td>
                        <td>
                            <input name="ticketPrice${vs.index}" class="form-control" value="${ticket.basePrice}" readonly>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="5" class="text-end fw-bold">Tổng tiền đơn hàng</td>
                    <td id="checkout-total-price" class="fw-bold text-primary"></td>
                </tr>
            </tfoot>
        </table>
        <input type="hidden" name="ticketCount" value="${fn:length(ticketList)}">

        <!-- Thông tin người đặt -->
        <div class="card mb-4">
            <div class="card-header text-primary fw-bold">Thông tin người đặt vé</div>
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label>Họ và tên*</label>
                        <input type="text" class="form-control" name="buyerName" required>
                    </div>
                    <div class="col-md-6">
                        <label>Email nhận vé*</label>
                        <input type="email" class="form-control" name="buyerEmail" required>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label>Số điện thoại*</label>
                        <input type="text" class="form-control" name="buyerPhone" required pattern="^0[0-9]{9,10}$" title="Nhập đúng số điện thoại">
                    </div>
                    
                </div>
            </div>
        </div>

        <div class="mb-3">
            <label>Phương thức thanh toán</label>
            <select name="paymentMethod" class="form-select" required>
                <option value="vnpay">VNPay</option>
                <option value="cod">Thanh toán khi lên tàu</option>
            </select>
        </div>
        <div class="text-end">
            <button type="submit" class="btn btn-success btn-lg">Thanh toán</button>
        </div>
    </form>
</div>
<%@ include file="footer.jsp" %>

<script>
function parseNumber(str) {
    if (!str) return 0;
    if (typeof str === "number") return str;
    return parseFloat(str.replace(/[^0-9\.\-]/g,'').replace(',', '.')) || 0;
}

function updateTicketPrices() {
    let rows = document.querySelectorAll("tbody tr");
    let total = 0;
    rows.forEach((tr, idx) => {
        let basePrice = parseNumber(tr.querySelector('input[name="ticketPrice'+idx+'"]').defaultValue || tr.querySelector('input[name="ticketPrice'+idx+'"]').value);
        let select = tr.querySelector('select[name="passengerTypeId'+idx+'"]');
        let selectedOption = select.options[select.selectedIndex];
        let discountType = selectedOption.getAttribute('data-discounttype');
        let discountValue = parseNumber(selectedOption.getAttribute('data-discountvalue'));
        let price = basePrice;
        if (discountType && discountValue) {
            if (discountType === "percent" || discountType === "percentage") {
                price = basePrice * (1 - discountValue/100.0);
            } else if (discountType === "fixed") {
                price = Math.max(0, basePrice - discountValue);
            }
        }
        price = Math.round(price/1000)*1000;
        tr.querySelector('input[name="ticketPrice'+idx+'"]').value = price.toLocaleString("vi-VN");
        total += price;
    });
    document.getElementById("checkout-total-price").textContent = total.toLocaleString("vi-VN") + " ₫";
}

document.querySelectorAll('select[name^="passengerTypeId"]').forEach(function(sel){
    sel.addEventListener("change", updateTicketPrices);
});
window.addEventListener("DOMContentLoaded", updateTicketPrices);
</script>
</body>
</html>
