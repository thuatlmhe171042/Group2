<!-- Sidebar dành cho staff -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="d-flex flex-column flex-shrink-0 bg-light position-fixed border-end" style="width: 250px; height: 100vh; z-index: 1000;">
    
    <div class="p-3">
        <a href="staffDashboard" class="d-flex align-items-center mb-3 link-dark text-decoration-none">
            <span class="fs-5 fw-bold">Nhân viên</span>
        </a>
        <hr>
        <ul class="nav nav-pills flex-column mb-auto">
            <li class="nav-item"><a href="dashboard" class="nav-link active">Trang chính</a></li>
            <li><a href="manageTrains" class="nav-link text-dark">Quản lý tàu</a></li>
            <li><a href="manageStations" class="nav-link text-dark">Quản lý ga</a></li>
            <li><a href="manageCarriageTypes" class="nav-link text-dark">Loại toa</a></li>
            <li><a href="manageCarriages" class="nav-link text-dark">Quản lý toa xe, ghế cho từng tàu.</a></li>
            <li><a href="manageSegmentPrices" class="nav-link text-dark">Giá chặng</a></li>
            <li><a href="manageSchedules" class="nav-link text-dark">Quản lý lịch trình tàu và điểm dừng</a></li>
            <li><a href="managePassengerTypes" class="nav-link text-dark">Loại hành khách</a></li>
            
            <li><a href="manageOrders" class="nav-link text-dark">Quản lý Đơn hàng và Vé</a></li>
            <li><a href="manageRefundRules" class="nav-link text-dark">Quy tắc hoàn vé</a></li>
        </ul>
    </div>
</div>
