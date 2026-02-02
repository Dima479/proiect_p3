package service;

import DAO.RecieptDAO;
import model.Reciept;
import java.time.LocalDate;
import java.util.List;

public class ReceiptService {
    private final RecieptDAO recieptDAO = new RecieptDAO();

    public void create(Reciept reciept) {
        validateReciept(reciept);
        recieptDAO.create(reciept);
    }

    public void update(Reciept reciept) {
        validateReciept(reciept);
        recieptDAO.update(reciept);
    }

    public Reciept findById(int id) {
        validateId(id);
        return recieptDAO.find(id);
    }

    public Reciept findByReservationId(int reservationid) {
        validateId(reservationid);
        return recieptDAO.findByReservationId(reservationid);
    }

    public List<Reciept> findAll() {
        return recieptDAO.findAll();
    }

    private void validateReciept(Reciept reciept) {
        if (reciept == null) {
            throw new IllegalArgumentException("chitanta lipsa");
        }
        if (reciept.getVal() <= 0) {
            throw new IllegalArgumentException("valoare chitanta invalida");
        }
        if (reciept.getDate() == null) {
            throw new IllegalArgumentException("data chitanta lipsa");
        }
        if (reciept.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("data chitanta in viitor");
        }
        if (reciept.getReservation() == null) {
            throw new IllegalArgumentException("rezervare lipsa");
        }
        if (reciept.getReservation().getReservation_ID() <= 0) {
            throw new IllegalArgumentException("id rezervare invalid");
        }
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id invalid");
        }
    }
}
