package controller;

import dal.*;
import models.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class SearchTicketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Station> stationList = new StationDAO().getAllStations();
        request.setAttribute("stationList", stationList);

        String fromStationName = request.getParameter("fromStation");
        String toStationName = request.getParameter("toStation");
        String departDate = request.getParameter("departDate");

        if (fromStationName == null || toStationName == null || departDate == null ||
                fromStationName.isEmpty() || toStationName.isEmpty() || departDate.isEmpty()) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        StationDAO stationDAO = new StationDAO();
        Station fromStation = stationDAO.getStationByDisplayName(fromStationName);
        Station toStation = stationDAO.getStationByDisplayName(toStationName);

        if (fromStation == null || toStation == null) {
            request.setAttribute("searchError", "Không tìm thấy ga đi hoặc ga đến hợp lệ!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        List<TrainSchedule> schedules = new TrainScheduleDAO().searchSchedules(
                fromStation.getId(), toStation.getId(), departDate);

        // --- Lọc các lịch trình còn ghế trống ---
        List<TrainSchedule> availableSchedules = new ArrayList<>();
        ScheduleStopDAO stopDAO = new ScheduleStopDAO();
        SeatDAO seatDAO = new SeatDAO();
        TicketDAO ticketDAO = new TicketDAO();
        for (TrainSchedule sc : schedules) {
            List<ScheduleStop> stops = stopDAO.getStopsByScheduleId(sc.getId());
            int depStopId = -1, arrStopId = -1;
            for (ScheduleStop stop : stops) {
                if (stop.getStationId() == fromStation.getId()) depStopId = stop.getId();
                if (stop.getStationId() == toStation.getId()) arrStopId = stop.getId();
            }
            if (depStopId == -1 || arrStopId == -1 || depStopId >= arrStopId) continue;

            List<Carriage> carriages = new CarriageDAO().getCarriagesByTrainId(sc.getTrainId());
            boolean found = false;
            for (Carriage c : carriages) {
                List<Seat> seats = seatDAO.getSeatsByCarriageId(c.getId());
                Set<Integer> bookedSeatIds = ticketDAO.getBookedSeatIds(sc.getId(), depStopId, arrStopId);
                for (Seat seat : seats) {
                    if (!bookedSeatIds.contains(seat.getId())) {
                        found = true; break;
                    }
                }
                if (found) break;
            }
            if (found) availableSchedules.add(sc);
        }

        Map<Integer, Train> trainMap = new HashMap<>();
        TrainDAO trainDAO = new TrainDAO();
        for (TrainSchedule sc : availableSchedules) {
            if (!trainMap.containsKey(sc.getTrainId())) {
                Train t = trainDAO.getTrainById(sc.getTrainId());
                trainMap.put(sc.getTrainId(), t);
            }
        }
        Map<Integer, Station> stationIdMap = new HashMap<>();
        for (Station s : stationList) {
            stationIdMap.put(s.getId(), s);
        }

        // TRUYỀN ID xuống JSP
        request.setAttribute("schedules", availableSchedules);
        request.setAttribute("trainMap", trainMap);
        request.setAttribute("stationIdMap", stationIdMap);
        request.setAttribute("fromStation", fromStation.getId());
        request.setAttribute("toStation", toStation.getId());
        request.setAttribute("departDate", departDate);
        
        Map<Integer, List<ScheduleStop>> stopsMap = new HashMap<>();
for (TrainSchedule sc : availableSchedules) {
    stopsMap.put(sc.getId(), stopDAO.getStopsByScheduleId(sc.getId()));
}

request.setAttribute("stopsMap", stopsMap);

        request.getRequestDispatcher("searchResult.jsp").forward(request, response);
    }
}
