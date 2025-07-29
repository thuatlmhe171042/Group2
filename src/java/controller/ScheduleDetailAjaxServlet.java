package controller;

import dal.*;
import models.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class ScheduleDetailAjaxServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String scheduleIdRaw = request.getParameter("scheduleId");
        String fromStationIdRaw = request.getParameter("fromStationId");
        String toStationIdRaw = request.getParameter("toStationId");
        if (scheduleIdRaw == null || fromStationIdRaw == null || toStationIdRaw == null) {
            response.sendError(400, "Thiếu tham số!");
            return;
        }
        int scheduleId = Integer.parseInt(scheduleIdRaw);
        int fromStationId = Integer.parseInt(fromStationIdRaw);
        int toStationId = Integer.parseInt(toStationIdRaw);

        List<Station> stationList = new StationDAO().getAllStations();
        Map<Integer, Station> stationIdMap = new HashMap<>();
        for (Station s : stationList) stationIdMap.put(s.getId(), s);
        request.setAttribute("stationIdMap", stationIdMap);

        TrainSchedule schedule = new TrainScheduleDAO().getScheduleById(scheduleId);
        // **LẤY OBJECT TRAIN**
        Train train = new TrainDAO().getTrainById(schedule.getTrainId());
        request.setAttribute("train", train);

        List<ScheduleStop> stops = new ScheduleStopDAO().getStopsByScheduleId(scheduleId);

        int depStopId = -1, arrStopId = -1;
        ScheduleStop depStop = null, arrStop = null;
        for (ScheduleStop stop : stops) {
            if (stop.getStationId() == fromStationId) { depStopId = stop.getId(); depStop = stop; }
            if (stop.getStationId() == toStationId)   { arrStopId = stop.getId(); arrStop = stop; }
        }
        if (depStopId == -1 || arrStopId == -1 || depStopId >= arrStopId) {
            response.sendError(400, "Không tìm thấy chặng phù hợp!");
            return;
        }
        Station fromStation = stationIdMap.get(fromStationId);
        Station toStation = stationIdMap.get(toStationId);

        List<Carriage> carriages = new CarriageDAO().getCarriagesByTrainId(schedule.getTrainId());
        Map<Integer, CarriageType> carriageTypeMap = new HashMap<>();
        CarriageTypeDAO typeDAO = new CarriageTypeDAO();
        for (Carriage c : carriages) {
            if (!carriageTypeMap.containsKey(c.getCarriageTypeId()))
                carriageTypeMap.put(c.getCarriageTypeId(), typeDAO.getById(c.getCarriageTypeId()));
        }

        List<ScheduleSegmentPrice> prices = new ScheduleSegmentPriceDAO().getPrices(scheduleId, depStopId, arrStopId);

        TicketDAO ticketDAO = new TicketDAO();
        Map<Integer, Set<Integer>> bookedSeatMap = new HashMap<>();
        for (Carriage c : carriages) {
            Set<Integer> bookedSeatIds = ticketDAO.getBookedSeatIds(scheduleId, depStopId, arrStopId);
            bookedSeatMap.put(c.getId(), bookedSeatIds);
        }
        Map<Integer, List<Seat>> seatMap = new HashMap<>();
        SeatDAO seatDAO = new SeatDAO();
        for (Carriage c : carriages) {
            seatMap.put(c.getId(), seatDAO.getSeatsByCarriageId(c.getId()));
        }

        request.setAttribute("schedule", schedule);
        request.setAttribute("train", train); // <<<< QUAN TRỌNG!
        request.setAttribute("stops", stops);
        request.setAttribute("carriages", carriages);
        request.setAttribute("carriageTypeMap", carriageTypeMap);
        request.setAttribute("prices", prices);
        request.setAttribute("seatMap", seatMap);
        request.setAttribute("bookedSeatMap", bookedSeatMap);
        request.setAttribute("depStopId", depStopId);
        request.setAttribute("arrStopId", arrStopId);
        request.setAttribute("fromStation", fromStation);
        request.setAttribute("toStation", toStation);
        request.setAttribute("fromStop", depStop);
        request.setAttribute("toStop", arrStop);

        request.getRequestDispatcher("schedule_detail_fragment.jsp").forward(request, response);
    }
}
