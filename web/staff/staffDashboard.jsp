<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../header.jsp" %>
<%@include file="sidebar.jsp" %>

<!-- Admin Dashboard Template (Bootstrap SB Admin 2 style) -->
<div class="container-fluid" style="margin-left: 250px; padding-top: 20px">
    <h1 class="h3 mb-4 text-gray-800">Bảng điều khiển nhân viên</h1>

    <div class="row">
        <!-- Tổng số vé hôm nay -->
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                Vé bán hôm nay</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${totalTicketsToday}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-ticket-alt fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Doanh thu hôm nay -->
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-success shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                Doanh thu hôm nay</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${totalRevenueToday * 1000}đ</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-money-bill-wave fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Số tàu -->
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-info shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-info text-uppercase mb-1">Số tàu</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${trainCount}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-train fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>



    <div class="row">
        <!-- Loại toa -->
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-secondary shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-secondary text-uppercase mb-1">Loại toa</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${carriageTypeCount}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-couch fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Loại hành khách -->
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-dark shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-dark text-uppercase mb-1">Loại hành khách</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${passengerTypeCount}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-user-friends fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="../footer.jsp" %>

<!-- FontAwesome icons (nếu chưa có) -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
