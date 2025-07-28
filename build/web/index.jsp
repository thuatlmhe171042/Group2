<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>TrainTicket - Đặt vé tàu trực tuyến</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="header.jsp" %>
    <!-- Bootstrap CSS 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="file.css">
    
</head>
<body>

    <div class="container mt-4">
        <div class="row align-items-center bg-light rounded-3 p-4 shadow-sm">
            <div class="col-md-7 mb-3 mb-md-0">
                <h1 class="display-5 fw-bold text-primary">Đặt vé tàu trực tuyến</h1>
                <p class="lead mb-4">Nhanh chóng - An toàn - Tiện lợi <br> Trải nghiệm đặt vé tàu 24/7, chọn chỗ ngồi theo ý muốn và nhiều ưu đãi hấp dẫn.</p>
                <ul class="mb-4 list-unstyled">
                    <li><i class="bi bi-check-circle-fill text-success"></i> Lịch trình đa dạng, giá vé minh bạch</li>
                    <li><i class="bi bi-check-circle-fill text-success"></i> Thanh toán bảo mật, xác nhận nhanh qua email</li>
                    <li><i class="bi bi-check-circle-fill text-success"></i> Hỗ trợ trả vé tiện lợi</li>
                </ul>
            </div>
            <div class="col-md-5 text-center">
                <img src="https://homepage.momocdn.net/img/momo-upload-api-211014103142-637698043027982617.jpg" alt="Đặt vé tàu" class="img-fluid rounded shadow-sm" style="max-height:230px;">
            </div>
        </div>
    </div>

    <!-- Form tìm kiếm vé -->
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <form action="home" method="get" class="card p-4 shadow-lg border-0">
                    <h4 class="mb-4 text-center text-primary">Tìm chuyến tàu phù hợp</h4>
                    <div class="row g-3">
                        <div class="col-md-3">
                            <label for="fromStation" class="form-label">Ga đi</label>
                            <input list="stationList" id="fromStation" name="fromStation" class="form-control" required placeholder="Nhập tên hoặc chọn ga đi">
                            <datalist id="stationList">
                                <c:forEach var="station" items="${stationList}">
                                    <option value="${station.stationName} (${station.stationCode})">
                                </c:forEach>
                            </datalist>
                        </div>
                        <div class="col-md-3">
                            <label for="toStation" class="form-label">Ga đến</label>
                            <input list="stationList2" id="toStation" name="toStation" class="form-control" required placeholder="Nhập tên hoặc chọn ga đến">
                            <datalist id="stationList2">
                                <c:forEach var="station" items="${stationList}">
                                    <option value="${station.stationName} (${station.stationCode})">
                                </c:forEach>
                            </datalist>
                        </div>
                        <div class="col-md-3">
                            <label for="departDate" class="form-label">Ngày đi</label>
                            <input type="date" class="form-control" id="departDate" name="departDate" min="<%= java.time.LocalDate.now() %>" required>
                        </div>
                        <div class="col-md-3">
                            <label for="returnDate" class="form-label">Ngày về <span class="text-muted small">(Không bắt buộc)</span></label>
                            <input type="date" class="form-control" id="returnDate" name="returnDate" min="<%= java.time.LocalDate.now() %>">
                        </div>
                    </div>
                    <div class="row mt-4">
                        <div class="col text-center">
                            <button type="submit" class="btn btn-primary px-5 py-2 fw-bold">Tìm vé</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <%@ include file="footer.jsp" %>
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="file.js"></script>
</body>
</html>


