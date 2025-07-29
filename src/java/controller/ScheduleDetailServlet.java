package controller;

import dal.*;
import models.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ScheduleDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String idRaw = request.getParameter("id");
        if (idRaw == null) {
            response.sendRedirect("home");
            return;
        }
        int scheduleId = Integer.parseInt(idRaw);

        // 1. Lấy lịch trình
        TrainScheduleDAO schDAO = new TrainScheduleDAO();
        TrainSchedule schedule = schDAO.getScheduleById(scheduleId);

        // 2. Lấy điểm dừng
        ScheduleStopDAO stopDAO = new ScheduleStopDAO();
        List<ScheduleStop> stops = stopDAO.getStopsByScheduleId(scheduleId);

        // 3. Lấy thông tin tàu, toa, loại toa
        TrainDAO trainDAO = new TrainDAO();
        Train train = trainDAO.getTrainById(schedule.getTrainId());

        CarriageDAO carriageDAO = new CarriageDAO();
        List<Carriage> carriages = carriageDAO.getCarriagesByTrainId(schedule.getTrainId());

        CarriageTypeDAO typeDAO = new CarriageTypeDAO();
        Map<Integer, CarriageType> carriageTypeMap = new HashMap<>();
        for (Carriage c : carriages) {
            if (!carriageTypeMap.containsKey(c.getCarriageTypeId())) {
                carriageTypeMap.put(c.getCarriageTypeId(), typeDAO.getById(c.getCarriageTypeId()));
            }
        }

        // 4. Giá vé cho từng loại toa trên toàn chặng
        int depStopId = stops.get(0).getId();
        int arrStopId = stops.get(stops.size()-1).getId();
        ScheduleSegmentPriceDAO priceDAO = new ScheduleSegmentPriceDAO();
        List<ScheduleSegmentPrice> prices = priceDAO.getPrices(scheduleId, depStopId, arrStopId);

        // 5. Đẩy dữ liệu ra JSP
        request.setAttribute("schedule", schedule);
        request.setAttribute("stops", stops);
        request.setAttribute("train", train);
        request.setAttribute("carriages", carriages);
        request.setAttribute("carriageTypeMap", carriageTypeMap);
        request.setAttribute("prices", prices);

        request.getRequestDispatcher("scheduleDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response); // Hoặc processRequest nếu bạn muốn, nhưng ở đây GET/POST nên đồng bộ logic
    }

    @Override
    public String getServletInfo() {
        return "ScheduleDetailServlet - hiển thị chi tiết lịch trình";
    }
}
