function showDetail(scheduleId, fromStationId, toStationId) {
    fetch('getScheduleDetail?scheduleId=' + scheduleId +
          '&fromStationId=' + fromStationId + '&toStationId=' + toStationId)
      .then(resp => resp.text())
      .then(html => {
          document.getElementById('schedule-detail-area').innerHTML = html;

          // Đợi DOM cập nhật xong mới lấy metadata
          setTimeout(() => {
              let meta = document.getElementById("schedule-detail-metadata");
              if (meta) {
                  window.selectedScheduleId = Number(meta.dataset.scheduleid);
                  console.log("ScheduleId metadata set: ", window.selectedScheduleId);
                  // alert("ScheduleId được set lại: " + window.selectedScheduleId);
              } else {
                  window.selectedScheduleId = 0;
                  console.warn("Không tìm thấy schedule-detail-metadata!");
              }

              // Cập nhật các biến khác nếu cần
              window.selectedTrainCode = document.getElementById("train-code-span")?.textContent?.trim() || "";
              window.selectedFromStationName = document.getElementById("from-station-span")?.textContent?.trim() || "";
              window.selectedToStationName = document.getElementById("to-station-span")?.textContent?.trim() || "";
              window.selectedDepartTime = document.getElementById("depart-time-span")?.textContent?.trim() || "";
              window.selectedArrivalTime = document.getElementById("arrive-time-span")?.textContent?.trim() || "";
              setTimeout(updateSeatUI, 100);
          }, 100); // Chờ 100ms để DOM chắc chắn đã cập nhật
      });
}


// Hàm selectSeat phải **luôn** lấy scheduleId từ window.selectedScheduleId
function selectSeat(el) {
    if (el.classList.contains("seat-booked")) return;
    el.classList.toggle("seat-selected");

    // LẤY scheduleId từ thuộc tính của chính thẻ ghế
    let scheduleId = Number(el.getAttribute("data-scheduleid"));
    let seatId = Number(el.getAttribute("data-seatid"));
    let carriageId = Number(el.getAttribute("data-carriageid"));
    let seatNumber = el.getAttribute("data-seatnumber");
    let carriageNumber = el.getAttribute("data-carriagenumber");
    let carriageType = el.getAttribute("data-carriagetype");
    let price = Number(el.getAttribute("data-price"));
    let fromStopId = Number(el.getAttribute("data-depstopid"));
    let toStopId = Number(el.getAttribute("data-arrstopid"));
    let expireAt = Date.now() + 5*60*1000;

    let trainCode = document.getElementById("train-code-span")?.textContent?.trim() || "";
    let fromStationName = document.getElementById("from-station-span")?.textContent?.trim() || "";
    let toStationName = document.getElementById("to-station-span")?.textContent?.trim() || "";
    let departTime = document.getElementById("depart-time-span")?.textContent?.trim() || "";
    let arrivalTime = document.getElementById("arrive-time-span")?.textContent?.trim() || "";

    let cart = JSON.parse(localStorage.getItem("cart") || "[]");
    let idx = cart.findIndex(item =>
        item.seatId === seatId &&
        item.carriageId === carriageId &&
        item.fromStopId === fromStopId &&
        item.toStopId === toStopId
    );

    if (el.classList.contains("seat-selected")) {
        if (idx === -1)
            cart.push({
                scheduleId,
                seatId, carriageId, seatNumber, carriageNumber, carriageType, price, fromStopId, toStopId, expireAt,
                trainCode, fromStationName, toStationName, departTime, arrivalTime
            });
    } else {
        if (idx !== -1)
            cart.splice(idx, 1);
    }
    localStorage.setItem("cart", JSON.stringify(cart));
    renderCart();
    updateSeatUI();
}


// Các hàm renderCart, removeCartSeat, updateSeatUI giữ nguyên như cũ.









function renderCart() {
    let cart = JSON.parse(localStorage.getItem("cart") || "[]");
    let now = Date.now();
    cart = cart.filter(item => item.expireAt > now);
    localStorage.setItem("cart", JSON.stringify(cart));
    let html = `<div class="cart-title"><i class="bi bi-cart"></i> Giỏ vé tạm thời</div><ul>`;
    let total = 0;
    if (cart.length === 0) html += "<li>Chưa có ghế nào được chọn.</li>";
    cart.forEach(item => {
        let remain = Math.floor((item.expireAt - now)/1000);
        let priceStr = Number(item.price).toLocaleString("vi-VN") + " ₫";
        total += Number(item.price);
        html += `<li>
            <span>Toa <b>${item.carriageNumber}</b> - Ghế <b>${item.seatNumber}</b>
            <br>
            <span style="font-size:13px;">
                ${item.carriageType} - <span style="color:#ff9800;font-weight:bold">${priceStr}</span>
            </span>
            <br>
            <span style="font-size:13px;">Chặng: ${item.fromStopId} → ${item.toStopId}</span>
            </span>
            <span>
                <span class="cart-timer">${remain}s</span>
                <button class="cart-remove-btn" title="Xóa ghế" onclick="removeCartSeat('${item.seatId}', '${item.carriageId}', '${item.fromStopId}', '${item.toStopId}')">
                    <i class="bi bi-x-lg"></i>
                </button>
            </span>
        </li>`;
    });
    if (cart.length > 0)
        html += `<li style="border:none;text-align:center;">
            <form id="checkoutForm" action="CheckoutServlet" method="post">
                <input type="hidden" name="cartData" id="cartData">
                <button type="submit" class="btn btn-success w-100 mt-2">Thanh toán</button>
            </form>
        </li>`;
    html += "</ul>";
    document.getElementById("cart-area").innerHTML = html;

    // Đẩy data vào hidden input khi form hiện
    let btn = document.getElementById('cartData');
    if (btn) {
        document.getElementById('cartData').value = JSON.stringify(cart);
    }
}

function removeCartSeat(seatId, carriageId, fromStopId, toStopId) {
    let cart = JSON.parse(localStorage.getItem("cart") || "[]");
    cart = cart.filter(item => !(item.seatId === seatId && item.carriageId === carriageId
                                 && item.fromStopId === fromStopId && item.toStopId === toStopId));
    localStorage.setItem("cart", JSON.stringify(cart));
    renderCart();
    updateSeatUI();
}
function updateSeatUI() {
    let cart = JSON.parse(localStorage.getItem("cart") || "[]");
    let seatPairs = cart.map(item => item.seatId + "_" + item.carriageId + "_" + item.fromStopId + "_" + item.toStopId);
    document.querySelectorAll('.seat').forEach(el => {
        let s = el.getAttribute("data-seatid") + "_" +
                el.getAttribute("data-carriageid") + "_" +
                el.getAttribute("data-depstopid") + "_" +
                el.getAttribute("data-arrstopid");
        if (seatPairs.includes(s)) {
            el.classList.add("seat-selected");
        } else {
            el.classList.remove("seat-selected");
        }
    });
}
setInterval(renderCart, 1000);

function releaseSeat(ticketId) {
    fetch('ReleaseTicketServlet', {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ ticketId })
    }).then(() => {
        renderCart();
        updateSeatUI();
    });
}
setInterval(renderCart, 1000);



