<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header.jsp" %>
<%@include file="sidebar.jsp" %>

<div class="container" ">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3>Quản lý giá vé theo chặng</h3>
        <form method="get" action="manageSegmentPrices" class="d-flex">
            <select class="form-select" name="scheduleId" required onchange="this.form.submit()">
                <option value="">-- Chọn lịch trình --</option>
                <c:forEach var="sch" items="${schedules}">
                    <option value="${sch.id}" ${sch.id == selectedScheduleId ? 'selected' : ''}>
                        Tàu: ${trainMap[sch.trainId].trainCode} (${trainMap[sch.trainId].trainName})
                        - Đi: ${sch.departureTime}, Đến: ${sch.arrivalTime}
                    </option>
                </c:forEach>
            </select>
        </form>
    </div>
    <c:if test="${selectedScheduleId != null}">
    <div class="table-responsive">
    <table class="table table-bordered table-hover shadow-sm align-middle">
        <thead class="table-light">
            <tr>
                <th>Chặng</th>
                <c:forEach var="type" items="${carriageTypes}">
                    <th>${type.typeName}<br/>(VND)</th>
                </c:forEach>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="segment" items="${segments}">
            <tr>
                <td>
                    ${stationMap[segment.fromStop.stationId].stationName}
                    →
                    ${stationMap[segment.toStop.stationId].stationName}
                    <br/><span class="text-muted">(${segment.distance} km)</span>
                </td>
                <c:forEach var="type" items="${carriageTypes}">
                    <td>
                        <form method="post" action="manageSegmentPrices" class="d-flex align-items-center">
                            <input type="hidden" name="action" value="save"/>
                            <input type="hidden" name="scheduleId" value="${selectedScheduleId}"/>
                            <input type="hidden" name="fromStopId" value="${segment.fromStop.id}"/>
                            <input type="hidden" name="toStopId" value="${segment.toStop.id}"/>
                            <input type="hidden" name="carriageTypeId" value="${type.id}"/>
                            <input type="number" min="0" class="form-control form-control-sm me-2"
                                   name="price"
                                   value="${segment.prices[type.id] != null ? segment.prices[type.id] : ''}"
                                   placeholder="${segment.suggestedPrices[type.id]}"/>
                            <button type="submit" class="btn btn-primary btn-sm">
                                <i class="fas fa-save"></i>
                            </button>
                            <c:if test="${segment.prices[type.id] == null}">
                                <span class="text-muted ms-2 small">(gợi ý: 
                                  <fmt:formatNumber value="${segment.suggestedPrices[type.id]}" type="number" maxFractionDigits="0"/>
                                )</span>
                            </c:if>
                        </form>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
    </c:if>
</div>
<%@include file="../footer.jsp" %>
