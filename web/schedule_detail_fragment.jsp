<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Thẻ metadata (có thể giữ lại, không ảnh hưởng) -->
<div id="schedule-detail-metadata" data-scheduleid="${schedule.id}" style="display:none"></div>

<!-- Hiển thị thông tin chặng đã chọn -->
<div class="mb-2">
  <b>Mã tàu:</b> <span id="train-code-span">${train.trainCode}</span>
  &nbsp; <b>KH:</b> <span id="from-station-span">${fromStation.stationName}</span> (<span id="depart-time-span">${fromStop.departureTime}</span>)
  &nbsp; <b>ĐN:</b> <span id="to-station-span">${toStation.stationName}</span> (<span id="arrive-time-span">${toStop.arrivalTime}</span>)
</div>


<!-- 3. Sơ đồ ghế cho từng toa -->
<c:forEach var="carriage" items="${carriages}">
    <div class="mb-4">
        <h6>Toa số ${carriage.carriageNumber} - ${carriageTypeMap[carriage.carriageTypeId].typeName}</h6>
        <div class="seat-map">
            <c:forEach var="row" begin="0" end="7">
                <div class="seat-row d-flex align-items-center mb-2">
                    <!-- Dãy trái: 2 ghế -->
                    <c:forEach var="col" begin="0" end="1">
                        <c:set var="seatIdx" value="${row*8 + col}" />
                        <c:set var="seat" value="${seatMap[carriage.id][seatIdx]}" />
                        <c:set var="isBooked" value="${bookedSeatMap[carriage.id].contains(seat.id)}" />
                        <div class="seat ${isBooked ? 'seat-booked' : 'seat-free'}"
                             data-scheduleid="${schedule.id}"  
                             data-seatid="${seat.id}"
                             data-carriageid="${carriage.id}"
                             data-seatnumber="${seat.seatNumber}"
                             data-carriagenumber="${carriage.carriageNumber}"
                             data-carriagetype="${carriageTypeMap[carriage.carriageTypeId].typeName}"
                             data-price="<c:forEach var='p' items='${prices}'><c:if test='${p.carriageTypeId == carriage.carriageTypeId}'>${p.price}</c:if></c:forEach>"
                             data-depstopid="${depStopId}" data-arrstopid="${arrStopId}"
                             onclick="selectSeat(this)">
                            ${seat.seatNumber}
                        </div>
                    </c:forEach>
                    <div style="width:18px"></div>
                    <!-- Dãy giữa: 4 ghế -->
                    <c:forEach var="col" begin="2" end="5">
                        <c:set var="seatIdx" value="${row*8 + col}" />
                        <c:set var="seat" value="${seatMap[carriage.id][seatIdx]}" />
                        <c:set var="isBooked" value="${bookedSeatMap[carriage.id].contains(seat.id)}" />
                        <div class="seat ${isBooked ? 'seat-booked' : 'seat-free'}"
                             data-scheduleid="${schedule.id}"
                             data-seatid="${seat.id}"
                             data-carriageid="${carriage.id}"
                             data-seatnumber="${seat.seatNumber}"
                             data-carriagenumber="${carriage.carriageNumber}"
                             data-carriagetype="${carriageTypeMap[carriage.carriageTypeId].typeName}"
                             data-price="<c:forEach var='p' items='${prices}'><c:if test='${p.carriageTypeId == carriage.carriageTypeId}'>${p.price}</c:if></c:forEach>"
                             data-depstopid="${depStopId}" data-arrstopid="${arrStopId}"
                             onclick="selectSeat(this)">
                            ${seat.seatNumber}
                        </div>
                    </c:forEach>
                    <div style="width:30px"></div>
                    <!-- Dãy phải: 2 ghế -->
                    <c:forEach var="col" begin="6" end="7">
                        <c:set var="seatIdx" value="${row*8 + col}" />
                        <c:set var="seat" value="${seatMap[carriage.id][seatIdx]}" />
                        <c:set var="isBooked" value="${bookedSeatMap[carriage.id].contains(seat.id)}" />
                        <div class="seat ${isBooked ? 'seat-booked' : 'seat-free'}"
                             data-scheduleid="${schedule.id}"
                             data-seatid="${seat.id}"
                             data-carriageid="${carriage.id}"
                             data-seatnumber="${seat.seatNumber}"
                             data-carriagenumber="${carriage.carriageNumber}"
                             data-carriagetype="${carriageTypeMap[carriage.carriageTypeId].typeName}"
                             data-price="<c:forEach var='p' items='${prices}'><c:if test='${p.carriageTypeId == carriage.carriageTypeId}'>${p.price}</c:if></c:forEach>"
                             data-depstopid="${depStopId}" data-arrstopid="${arrStopId}"
                             onclick="selectSeat(this)">
                            ${seat.seatNumber}
                        </div>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
    </div>
</c:forEach>

<script>
setTimeout(updateSeatUI, 100);
</script>








