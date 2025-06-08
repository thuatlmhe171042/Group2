<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Vé Tàu Online - Đặt Vé Nhanh Chóng, Tiện Lợi</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link rel="stylesheet" href="css/style.css">
        <style>
            .hero-section {
                background: url('https://images.unsplash.com/photo-1570125909232-eb263c1869e7?q=80&w=2070&auto=format&fit=crop') no-repeat center center;
                background-size: cover;
                height: 60vh;
                display: flex;
                align-items: center;
                justify-content: center;
                text-align: center;
                color: white;
                position: relative;
            }
            .hero-section::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: rgba(0, 0, 0, 0.5);
            }
            .hero-content {
                position: relative;
                z-index: 1;
            }
            .hero-content h1 {
                font-size: 3.5rem;
                font-weight: bold;
            }
            .hero-content p {
                font-size: 1.5rem;
            }
        </style>
    </head>
    <body>
        
        <jsp:include page="common/header.jsp" />

        <!-- Hero Section -->
        <header class="hero-section">
            <div class="hero-content">
                <h1 class="display-4">Chào Mừng Đến Với Dịch Vụ Vé Tàu Online</h1>
                <p class="lead">Đặt vé tàu nhanh chóng, an toàn và tiện lợi cho mọi hành trình.</p>
                <a href="login.jsp" class="btn btn-primary btn-lg mt-3">Bắt Đầu Đặt Vé</a>
            </div>
        </header>

        <!-- Features Section -->
        <div class="container my-5">
            <h2 class="text-center mb-4">Tại sao chọn chúng tôi?</h2>
            <div class="row text-center">
                <div class="col-md-4">
                    <div class="feature-icon mb-3">
                        <i class="bi bi-clock-history fs-1 text-primary"></i>
                    </div>
                    <h3>Nhanh chóng</h3>
                    <p>Chỉ với vài cú nhấp chuột, bạn đã có ngay tấm vé cho chuyến đi của mình.</p>
                </div>
                <div class="col-md-4">
                    <div class="feature-icon mb-3">
                        <i class="bi bi-shield-check fs-1 text-primary"></i>
                    </div>
                    <h3>An toàn</h3>
                    <p>Hệ thống thanh toán được bảo mật tuyệt đối, đảm bảo an toàn thông tin.</p>
                </div>
                <div class="col-md-4">
                    <div class="feature-icon mb-3">
                        <i class="bi bi-headset fs-1 text-primary"></i>
                    </div>
                    <h3>Hỗ trợ 24/7</h3>
                    <p>Đội ngũ hỗ trợ của chúng tôi luôn sẵn sàng giải đáp mọi thắc mắc của bạn.</p>
                </div>
            </div>
        </div>
        
        <jsp:include page="common/footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>





