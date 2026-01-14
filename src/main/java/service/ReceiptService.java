package service;

import DAO.RecieptDAO;
import model.Reciept;
import java.util.List;

public class ReceiptService {
    private final RecieptDAO recieptDAO = new RecieptDAO();

    public List<Reciept> findAll() {
        return recieptDAO.findAll();
    }
}
