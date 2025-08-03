<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Flatpickr -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

<%@include file="../header.jsp" %>
<%@include file="sidebar.jsp" %>

<div class="container" style="margin-left:240px; padding-top:30px;">
    <form method="get" action="manageSchedules" class="mb-3">
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
    <!-- Thêm lịch trình -->
    <form method="post" action="manageSchedules" class="row g-2 mb-2">
        <input type="hidden" name="action" value="addSchedule"/>
        <input type="hidden" name="trainId" value="${selectedTrainId}"/>
        <div class="col">
            <select name="departureStationId" class="form-select" required>
                <c:forEach var="st" items="${stations}">
                    <option value="${st.id}">${st.stationName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col">
            <select name="arrivalStationId" class="form-select" required>
                <c:forEach var="st" items="${stations}">
                    <option value="${st.id}">${st.stationName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col">
            <input type="text" name="departureTime" class="form-control datetimepicker" placeholder="Giờ đi" required/>
        </div>
        <div class="col">
            <input type="text" name="arrivalTime" class="form-control datetimepicker" placeholder="Giờ đến" required/>
        </div>
        <div class="col">
            <input type="text" name="status" class="form-control" placeholder="Trạng thái" value="available" required/>
        </div>
        <div class="col">
            <button type="submit" class="btn btn-success">Thêm lịch trình</button>
        </div>
    </form>

    <!-- Danh sách lịch trình -->
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>ID</th>
                <th>Ga đi</th>
                <th>Ga đến</th>
                <th>Giờ đi</th>
                <th>Giờ đến</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="sch" items="${schedules}">
            <tr>
                <td>${sch.id}</td>
                <td>${stationMap[sch.departureStationId].stationName}</td>
                <td>${stationMap[sch.arrivalStationId].stationName}</td>
                <td>${sch.departureTime}</td>
                <td>${sch.arrivalTime}</td>
                <td>${sch.status}</td>
                <td>
                    <button type="button" class="btn btn-info btn-sm" data-bs-toggle="collapse" data-bs-target="#stops${sch.id}">Điểm dừng</button>
                </td>
            </tr>
            <tr class="collapse" id="stops${sch.id}">
                <td colspan="7">
                    <!-- Danh sách điểm dừng -->
                    <table class="table table-sm mb-0">
                        <thead>
                            <tr>
                                <th>STT</th>
                                <th>Tên ga</th>
                                <th>Đến</th>
                                <th>Đi</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="stop" items="${stopMap[sch.id]}">
                            <tr>
                                <td>${stop.stopSequence}</td>
                                <td>${stationMap[stop.stationId].stationName}</td>
                                <td>${stop.arrivalTime}</td>
                                <td>${stop.departureTime}</td>
                                <td>
                                    <form method="post" action="manageSchedules" class="d-inline">
                                        <input type="hidden" name="action" value="deleteStop"/>
                                        <input type="hidden" name="stopId" value="${stop.id}"/>
                                        <input type="hidden" name="trainId" value="${selectedTrainId}"/>
                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Xóa điểm dừng này?')">Xóa</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <!-- Thêm điểm dừng mới -->
                    <form method="post" action="manageSchedules" class="row g-2 mt-1">
                        <input type="hidden" name="action" value="addStop"/>
                        <input type="hidden" name="scheduleId" value="${sch.id}"/>
                        <input type="hidden" name="trainId" value="${selectedTrainId}"/>
                        <div class="col">
                            <input type="number" name="stopSequence" class="form-control form-control-sm" placeholder="Thứ tự" required/>
                        </div>
                        <div class="col">
                            <select name="stationId" class="form-select form-select-sm" required>
                                <c:forEach var="station" items="${stations}">
                                    <option value="${station.id}">${station.stationName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col">
                            <input type="text" name="arrivalTime" class="form-control form-control-sm datetimepicker" placeholder="Giờ đến"/>
                        </div>
                        <div class="col">
                            <input type="text" name="departureTime" class="form-control form-control-sm datetimepicker" placeholder="Giờ đi"/>
                        </div>
                        <div class="col">
                            <button type="submit" class="btn btn-success btn-sm">Thêm điểm dừng</button>
                        </div>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:if>
</div>

<%@include file="../footer.jsp" %>

<!-- Flatpickr Init -->
<script>
    flatpickr(".datetimepicker", {
        enableTime: true,
        dateFormat: "Y-m-d H:i:S",
        time_24hr: true
    });
</script>
