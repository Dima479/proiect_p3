package service;

import DAO.ReservationDAO;
import model.Reservation;
import java.util.List;

public class ReservationService {
    private final ReservationDAO reservationDAO = new ReservationDAO();

    public void create(Reservation reservation) {
        reservationDAO.create(reservation);
    }

    public Reservation findById(int id) {
        return reservationDAO.findByIdWithDetails(id);
    }

    public void update(Reservation reservation) {
        reservationDAO.update(reservation);
    }

    public List<Reservation> findReservationsByMechanicId(int mechanicId) {
        return reservationDAO.findReservationsByMechanicId(mechanicId);
    }

    public List<Reservation> findReservationsByCarId(int carId) {
        return reservationDAO.findReservationsByCarId(carId);
    }
}
