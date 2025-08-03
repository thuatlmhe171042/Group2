package staff.controller;

import dal.*;
import models.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class ManageSchedulesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        List<Train> trains = new TrainDAO().getAll();
        List<Station> stations = new StationDAO().getAllStations();
        Map<Integer, Station> stationMap = new HashMap<>();
        for (Station st : stations) stationMap.put(st.getId(), st);

        request.setAttribute("trains", trains);
        request.setAttribute("stations", stations);
        request.setAttribute("stationMap", stationMap);

        String trainIdRaw = request.getParameter("trainId");
        Integer selectedTrainId = null;
        List<TrainSchedule> schedules = new ArrayList<>();
        Map<Integer, List<ScheduleStop>> stopMap = new HashMap<>();
        if (trainIdRaw != null && !trainIdRaw.isEmpty()) {
            selectedTrainId = Integer.parseInt(trainIdRaw);
            schedules = new TrainScheduleDAO().getByTrainId(selectedTrainId);
            ScheduleStopDAO stopDAO = new ScheduleStopDAO();
            for (TrainSchedule sch : schedules) {
                List<ScheduleStop> stops = stopDAO.getStopsByScheduleId(sch.getId());
                stopMap.put(sch.getId(), stops);
            }
        }
        request.setAttribute("schedules", schedules);
        request.setAttribute("stopMap", stopMap);
        request.setAttribute("selectedTrainId", selectedTrainId);

        request.getRequestDispatcher("/staff/manageSchedules.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User staff = (User) (session != null ? session.getAttribute("user") : null);
        String ip = request.getRemoteAddr();

        String action = request.getParameter("action");
        TrainScheduleDAO scheduleDAO = new TrainScheduleDAO();
        ScheduleStopDAO stopDAO = new ScheduleStopDAO();

        if ("addSchedule".equals(action)) {
            int trainId = Integer.parseInt(request.getParameter("trainId"));
            int depStationId = Integer.parseInt(request.getParameter("departureStationId"));
            int arrStationId = Integer.parseInt(request.getParameter("arrivalStationId"));
            String depTime = request.getParameter("departureTime");
            String arrTime = request.getParameter("arrivalTime");
            String status = request.getParameter("status");
            TrainSchedule sch = new TrainSchedule(0, trainId, depStationId, arrStationId, depTime, arrTime, status, 0);
            int id = scheduleDAO.addSchedule(sch);
            AuditLogDAO.addLog(staff.getId(), "ADD", "TrainSchedules", id, "Thêm lịch trình tàu " + trainId, ip);

        } else if ("editSchedule".equals(action)) {
            int id = Integer.parseInt(request.getParameter("scheduleId"));
            int depStationId = Integer.parseInt(request.getParameter("departureStationId"));
            int arrStationId = Integer.parseInt(request.getParameter("arrivalStationId"));
            String depTime = request.getParameter("departureTime");
            String arrTime = request.getParameter("arrivalTime");
            String status = request.getParameter("status");
            scheduleDAO.updateSchedule(new TrainSchedule(id, 0, depStationId, arrStationId, depTime, arrTime, status, 0));
            AuditLogDAO.addLog(staff.getId(), "UPDATE", "TrainSchedules", id, "Sửa lịch trình " + id, ip);

        } else if ("deleteSchedule".equals(action)) {
            int id = Integer.parseInt(request.getParameter("scheduleId"));
            scheduleDAO.deleteSchedule(id);
            AuditLogDAO.addLog(staff.getId(), "DELETE", "TrainSchedules", id, "Xóa logic lịch trình " + id, ip);

        } else if ("addStop".equals(action)) {
            int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
            int stopSequence = Integer.parseInt(request.getParameter("stopSequence"));
            int stationId = Integer.parseInt(request.getParameter("stationId"));
            String arrivalTime = request.getParameter("arrivalTime");
            String departureTime = request.getParameter("departureTime");
            ScheduleStop stop = new ScheduleStop(0, scheduleId, stationId, stopSequence, arrivalTime, departureTime);
            int id = stopDAO.addStop(stop);
            AuditLogDAO.addLog(staff.getId(), "ADD", "ScheduleStops", id, "Thêm điểm dừng ga " + stationId, ip);

        } else if ("editStop".equals(action)) {
            int id = Integer.parseInt(request.getParameter("stopId"));
            int stopSequence = Integer.parseInt(request.getParameter("stopSequence"));
            int stationId = Integer.parseInt(request.getParameter("stationId"));
            String arrivalTime = request.getParameter("arrivalTime");
            String departureTime = request.getParameter("departureTime");
            stopDAO.updateStop(new ScheduleStop(id, 0, stationId, stopSequence, arrivalTime, departureTime));
            AuditLogDAO.addLog(staff.getId(), "UPDATE", "ScheduleStops", id, "Sửa điểm dừng " + id, ip);

        } else if ("deleteStop".equals(action)) {
            int id = Integer.parseInt(request.getParameter("stopId"));
            stopDAO.deleteStop(id);
            AuditLogDAO.addLog(staff.getId(), "DELETE", "ScheduleStops", id, "Xóa logic điểm dừng " + id, ip);
        }

        String redirect = "manageSchedules";
        String trainId = request.getParameter("trainId");
        if (trainId != null) redirect += "?trainId=" + trainId;
        response.sendRedirect(redirect);
    }
}
