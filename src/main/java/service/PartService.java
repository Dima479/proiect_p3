package service;

import DAO.PartDAO;
import model.Part;
import java.util.List;

public class PartService {
    private final PartDAO partDAO = new PartDAO();

    public List<Part> findAll() {
        return partDAO.findAll();
    }

    public void save(Part part) {
        if (part.getPart_ID() == 0) {
            partDAO.create(part);
        } else {
            partDAO.update(part);
        }
    }

    public void delete(int id) {
        partDAO.delete(id);
    }
}
