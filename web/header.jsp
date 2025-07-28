<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Bootstrap 5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
<!-- FontAwesome -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg navbar-light bg-white border-bottom shadow-sm">
    <div class="container">
        <a class="navbar-brand font-weight-bold text-primary" href="${pageContext.request.contextPath}/index.jsp" style="font-size: 1.5rem;">TrainTicket</a>
        <div class="collapse navbar-collapse justify-content-end">
            <ul class="navbar-nav">

                <!-- Nếu chưa đăng nhập -->
                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/orderList.jsp">Đơn hàng</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/refundList.jsp">Trả vé</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/support.jsp"></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link btn btn-outline-primary px-3" href="${pageContext.request.contextPath}/login.jsp" style="margin-right:10px;">Đăng nhập</a>
                        </li>
                    </c:when>

                    <c:otherwise>
                        <!-- Đã đăng nhập -->
                        <li class="nav-item mt-2 mt-lg-0 mr-2">
                            <span class="nav-link text-dark">Xin chào, <b>${sessionScope.user.name}</b></span>
                        </li>

                        <!-- Nếu KHÔNG phải staff hoặc admin -->
                        <c:if test="${sessionScope.user.role != 'staff' && sessionScope.user.role != 'admin'}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/orderList.jsp">Đơn hàng</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/refundList.jsp">Trả vé</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/support.jsp">Hỗ trợ</a>
                            </li>
                        </c:if>

                        <!-- Ai đăng nhập cũng có thể xem profile -->
                        
                        <li class="nav-item">
                            <a class="nav-link text-danger btn btn-link" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                        </li>
                    </c:otherwise>
                </c:choose>

            </ul>
        </div>
    </div>
</nav>
