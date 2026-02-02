package service;

import DAO.ReservationDAO;
import model.Reservation;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationService {
    private final ReservationDAO reservationDAO = new ReservationDAO();

    public void create(Reservation reservation) {
        validateReservationForCreate(reservation);
        reservationDAO.create(reservation);
    }

    public Reservation findById(int id) {
        validateId(id);
        return reservationDAO.findByIdWithDetails(id);
    }

    public void update(Reservation reservation) {
        validateReservationForUpdate(reservation);
        reservationDAO.update(reservation);
    }

    public List<Reservation> findReservationsByMechanicId(int mechanicid) {
        validateId(mechanicid);
        return reservationDAO.findAll().stream()
                .filter(r -> r.getMechanic() != null && r.getMechanic().getUser_ID() == mechanicid)
                .collect(Collectors.toList());
    }

    public List<Reservation> findReservationsByCarId(int carid) {
        validateId(carid);
        return reservationDAO.findAll().stream()
                .filter(r -> r.getCar() != null && r.getCar().getCar_ID() == carid)
                .collect(Collectors.toList());
    }

    private void validateReservationForCreate(Reservation reservation) {
        validateReservationCommon(reservation);
        if (reservation.getDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("data programare in trecut");
        }
    }

    private void validateReservationForUpdate(Reservation reservation) {
        validateReservationCommon(reservation);
        if (reservation.getReservation_ID() <= 0) {
            throw new IllegalArgumentException("id rezervare invalid");
        }
    }

    private void validateReservationCommon(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("rezervare lipsa");
        }
        if (reservation.getCar() == null) {
            throw new IllegalArgumentException("masina lipsa");
        }
        if (reservation.getCar().getCar_ID() <= 0) {
            throw new IllegalArgumentException("id masina invalid");
        }
        if (reservation.getDate() == null) {
            throw new IllegalArgumentException("data lipsa");
        }
        if (reservation.getDetails() == null || reservation.getDetails().trim().isEmpty()) {
            throw new IllegalArgumentException("detalii lipsa");
        }
        if (reservation.getStatus() == null) {
            throw new IllegalArgumentException("status lipsa");
        }
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id invalid");
        }
    }
}
