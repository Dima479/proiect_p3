package service;

import DAO.ReservationDAO;
import model.Reservation;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationService {
    private final ReservationDAO reservationDAO = new ReservationDAO();

    public void create(Reservation reservation) {
        reservationDAO.create(reservation);
    }

    public Reservation findById(int id) {
        // Apelam noua metoda pentru a incarca toate detaliile
        return reservationDAO.findByIdWithDetails(id);
    }

    public void update(Reservation reservation) {
        reservationDAO.update(reservation);
    }

    public List<Reservation> findReservationsByMechanicId(int mechanicId) {
        return reservationDAO.findAll().stream()
                .filter(r -> r.getMechanic() != null && r.getMechanic().getUser_ID() == mechanicId)
                .collect(Collectors.toList());
    }

    public List<Reservation> findReservationsByCarId(int carId) {
        return reservationDAO.findAll().stream()
                .filter(r -> r.getCar() != null && r.getCar().getCar_ID() == carId)
                .collect(Collectors.toList());
    }
}
