package service;

import DAO.MechanicDAO;
import model.Mechanic;
import java.util.List;

public class MechanicService {
    private final MechanicDAO mechanicDAO = new MechanicDAO();

    public List<Mechanic> findAll() {
        return mechanicDAO.findAll();
    }

    public void save(Mechanic mechanic) {
        if (mechanic.getUser_ID() == 0) {
            mechanicDAO.create(mechanic);
        } else {
            mechanicDAO.update(mechanic);
        }
    }

    public void delete(int id) {
        mechanicDAO.delete(id);
    }
}
