<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Kết quả tìm kiếm lịch trình</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="file.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <style>
        .train-card {
            width: 170px;
            margin: 8px;
            padding: 8px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="container mt-4">
    <h2 class="mb-4 text-primary">Kết quả tìm kiếm lịch trình</h2>
    <div class="mb-3">
        <b>Ga đi:</b>
        <c:out value="${stationIdMap[fromStation].stationName}" /> (${stationIdMap[fromStation].stationCode})
        &nbsp; | &nbsp;
        <b>Ga đến:</b>
        <c:out value="${stationIdMap[toStation].stationName}" /> (${stationIdMap[toStation].stationCode})
        &nbsp; | &nbsp;
        <b>Ngày đi:</b> ${departDate}
    </div>
    <div id="train-list" class="d-flex flex-row flex-wrap justify-content-start my-3">
        <c:forEach var="sc" items="${schedules}">
            <div class="train-card card text-center"
                 onclick="showDetail('${sc.id}', '${fromStation}', '${toStation}')"
                 id="train-card-${sc.id}">
                <div><b>${trainMap[sc.trainId].trainCode}</b></div>
                <div>Đi: ${stationIdMap[fromStation].stationName}</div>
                <div>Đến: ${stationIdMap[toStation].stationName}</div>
                <div><b>KH:</b> ${sc.departureTime}</div>
                <div><b>ĐN:</b> ${sc.arrivalTime}</div>
            </div>
        </c:forEach>
    </div>
    <div id="schedule-detail-area"></div>
    <div class="mt-4">
        <img src="images/legend.png" alt="Chú giải màu sắc" style="max-width:100%;">
    </div>
</div>
<%@ include file="footer.jsp" %>
<div id="cart-area" class="cart-sidebar"></div>
<script src="search-ticket.js"></script>
<script>
function renderCart(){}
function showDetail(scheduleId, fromStationId, toStationId) {
    fetch('getScheduleDetail?scheduleId=' + scheduleId +
          '&fromStationId=' + fromStationId + '&toStationId=' + toStationId)
      .then(resp => resp.text())
      .then(html => {
          document.getElementById('schedule-detail-area').innerHTML = html;
          setTimeout(updateSeatUI, 100);
      });
}
renderCart();
</script>
</body>
</html>
