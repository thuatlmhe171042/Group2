package staff.controller;

import dal.*;
import models.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class ManageCarriagesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        List<Train> trains = new TrainDAO().getAll();
        request.setAttribute("trains", trains);

        String trainIdRaw = request.getParameter("trainId");
        Integer selectedTrainId = null;
        if (trainIdRaw != null && !trainIdRaw.isEmpty()) {
            selectedTrainId = Integer.parseInt(trainIdRaw);
            List<Carriage> carriages = new CarriageDAO().getCarriagesByTrainId(selectedTrainId);
            CarriageTypeDAO carriageTypeDAO = new CarriageTypeDAO();
            Map<Integer, CarriageType> carriageTypeMap = carriageTypeDAO.getMapAll();

            SeatDAO seatDAO = new SeatDAO();
            Map<Integer, Integer> seatCountMap = new HashMap<>();
            Map<Integer, List<Seat>> seatMap = new HashMap<>();
            for (Carriage carriage : carriages) {
                List<Seat> seats = seatDAO.getSeatsByCarriageId(carriage.getId());
                seatCountMap.put(carriage.getId(), seats.size());
                seatMap.put(carriage.getId(), seats);
            }

            request.setAttribute("carriages", carriages);
            request.setAttribute("carriageTypeMap", carriageTypeMap);
            request.setAttribute("seatCountMap", seatCountMap);
            request.setAttribute("seatMap", seatMap);
            request.setAttribute("selectedTrainId", selectedTrainId);
        }
        request.getRequestDispatcher("/staff/manageCarriages.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User staff = (User) (session != null ? session.getAttribute("user") : null);
        String ip = request.getRemoteAddr();

        String action = request.getParameter("action");
        CarriageDAO carriageDAO = new CarriageDAO();
        SeatDAO seatDAO = new SeatDAO();

        if ("addCarriage".equals(action)) {
            int trainId = Integer.parseInt(request.getParameter("trainId"));
            int typeId = Integer.parseInt(request.getParameter("carriageTypeId"));
            int number = Integer.parseInt(request.getParameter("carriageNumber"));
            Carriage c = new Carriage(0, trainId, typeId, number, false);
            int id = carriageDAO.addCarriage(c);
            AuditLogDAO.addLog(staff.getId(), "ADD", "Carriages", id, "Thêm toa xe số " + number, ip);

        } else if ("editCarriage".equals(action)) {
            int id = Integer.parseInt(request.getParameter("carriageId"));
            int typeId = Integer.parseInt(request.getParameter("carriageTypeId"));
            int number = Integer.parseInt(request.getParameter("carriageNumber"));
            carriageDAO.updateCarriage(new Carriage(id, 0, typeId, number, false));
            AuditLogDAO.addLog(staff.getId(), "UPDATE", "Carriages", id, "Sửa toa xe " + id, ip);

        } else if ("deleteCarriage".equals(action)) {
            int id = Integer.parseInt(request.getParameter("carriageId"));
            carriageDAO.deleteCarriage(id);
            AuditLogDAO.addLog(staff.getId(), "DELETE", "Carriages", id, "Xóa logic toa xe " + id, ip);

        } else if ("addSeat".equals(action)) {
            int carriageId = Integer.parseInt(request.getParameter("carriageId"));
            String seatNumber = request.getParameter("seatNumber");
            Seat s = new Seat(0, carriageId, seatNumber, 0);
            int id = seatDAO.addSeat(s);
            AuditLogDAO.addLog(staff.getId(), "ADD", "Seats", id, "Thêm ghế " + seatNumber + " cho toa " + carriageId, ip);

        } else if ("editSeat".equals(action)) {
            int id = Integer.parseInt(request.getParameter("seatId"));
            String seatNumber = request.getParameter("seatNumber");
            seatDAO.updateSeat(new Seat(id, 0, seatNumber, 0));
            AuditLogDAO.addLog(staff.getId(), "UPDATE", "Seats", id, "Sửa ghế " + seatNumber, ip);

        } else if ("deleteSeat".equals(action)) {
            int id = Integer.parseInt(request.getParameter("seatId"));
            seatDAO.deleteSeat(id);
            AuditLogDAO.addLog(staff.getId(), "DELETE", "Seats", id, "Xóa logic ghế " + id, ip);
        }

        String redirect = "manageCarriages";
        String trainId = request.getParameter("trainId");
        if (trainId != null) redirect += "?trainId=" + trainId;
        response.sendRedirect(redirect);
    }
}
