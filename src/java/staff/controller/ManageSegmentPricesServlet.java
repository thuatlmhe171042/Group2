package staff.controller;

import dal.*;
import models.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class ManageSegmentPricesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        TrainScheduleDAO schDAO = new TrainScheduleDAO();
        List<TrainSchedule> schedules = schDAO.getAll();
        request.setAttribute("schedules", schedules);

        // === Lấy map trainId -> Train ===
        TrainDAO trainDAO = new TrainDAO();
        Map<Integer, Train> trainMap = new HashMap<>();
        for (TrainSchedule sch : schedules) {
            int trainId = sch.getTrainId();
            if (!trainMap.containsKey(trainId)) {
                Train train = trainDAO.getTrainById(trainId);
                trainMap.put(trainId, train);
            }
        }
        request.setAttribute("trainMap", trainMap);

        String scheduleIdRaw = request.getParameter("scheduleId");
        Integer selectedScheduleId = null;
        if (scheduleIdRaw != null && !scheduleIdRaw.isEmpty()) {
            selectedScheduleId = Integer.parseInt(scheduleIdRaw);
            ScheduleStopDAO stopDAO = new ScheduleStopDAO();
            List<ScheduleStop> stops = stopDAO.getStopsByScheduleId(selectedScheduleId);

            // ===== Lấy station cho từng stop =====
            StationDAO stationDAO = new StationDAO();
            Map<Integer, Station> stationMap = new HashMap<>();
            for (ScheduleStop stop : stops) {
                int stationId = stop.getStationId();
                if (!stationMap.containsKey(stationId)) {
                    Station station = stationDAO.getById(stationId);
                    stationMap.put(stationId, station);
                }
            }
            request.setAttribute("stationMap", stationMap);

            CarriageTypeDAO typeDAO = new CarriageTypeDAO();
            List<CarriageType> carriageTypes = typeDAO.getAll();

            // Sinh tất cả tổ hợp chặng (chỉ tính chặng đi trước đến sau)
            List<SegmentInfo> segments = new ArrayList<>();
            ScheduleSegmentPriceDAO priceDAO = new ScheduleSegmentPriceDAO();
            for (int i = 0; i < stops.size() - 1; ++i) {
                for (int j = i + 1; j < stops.size(); ++j) {
                    SegmentInfo seg = new SegmentInfo();
                    seg.setFromStop(stops.get(i));
                    seg.setToStop(stops.get(j));
                    Station fromStation = stationMap.get(stops.get(i).getStationId());
                    Station toStation = stationMap.get(stops.get(j).getStationId());
                    seg.setDistance(haversine(
                        fromStation.getLatitude(), fromStation.getLongitude(),
                        toStation.getLatitude(), toStation.getLongitude()
                    ));
                    seg.setPrices(new HashMap<>());
                    seg.setSuggestedPrices(new HashMap<>());
                    for (CarriageType type : carriageTypes) {
                        Double price = priceDAO.getPrice(
                            selectedScheduleId, stops.get(i).getId(), stops.get(j).getId(), type.getId()
                        );
                        if (price == null && type.getBasePricePerKm() != null) {
                            double suggest = Math.round(type.getBasePricePerKm() * seg.getDistance());
                            // ===== TỰ ĐỘNG LƯU GIÁ GỢI Ý VÀO DB =====
                            priceDAO.insertOrUpdatePrice(
                                selectedScheduleId, stops.get(i).getId(), stops.get(j).getId(), type.getId(), suggest
                            );
                            seg.getPrices().put(type.getId(), suggest);
                        } else if (price != null) {
                            seg.getPrices().put(type.getId(), price);
                        }
                    }
                    segments.add(seg);
                }
            }
            request.setAttribute("segments", segments);
            request.setAttribute("carriageTypes", carriageTypes);
            request.setAttribute("selectedScheduleId", selectedScheduleId);
        }
        request.getRequestDispatcher("/staff/manageSegmentPrices.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User staff = (User) (session != null ? session.getAttribute("user") : null);
        String ip = request.getRemoteAddr();

        String action = request.getParameter("action");
        if ("save".equals(action)) {
            int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
            int fromStopId = Integer.parseInt(request.getParameter("fromStopId"));
            int toStopId = Integer.parseInt(request.getParameter("toStopId"));
            int carriageTypeId = Integer.parseInt(request.getParameter("carriageTypeId"));
            double price = Double.parseDouble(request.getParameter("price"));

            // Insert hoặc Update giá
            new ScheduleSegmentPriceDAO().insertOrUpdatePrice(
                scheduleId, fromStopId, toStopId, carriageTypeId, price
            );
            AuditLogDAO.addLog(staff.getId(), "UPDATE", "ScheduleSegmentPrices", 0,
                String.format("Cập nhật giá chặng %d-%d (toa %d, lịch trình %d): %,.0f", fromStopId, toStopId, carriageTypeId, scheduleId, price), ip);
        }
        // Giữ lại lịch trình đang chọn khi reload
        String redirect = "manageSegmentPrices";
        String scheduleId = request.getParameter("scheduleId");
        if (scheduleId != null) {
            redirect += "?scheduleId=" + scheduleId;
        }
        response.sendRedirect(redirect);
    }

    // Hàm tính khoảng cách Haversine (chuẩn)
    private static double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.round(R * c * 10.0) / 10.0;
    }
}
