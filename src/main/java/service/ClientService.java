package service;

import DAO.ClientDAO;
import model.Client;
import java.util.List;

public class ClientService {
    private final ClientDAO clientDAO = new ClientDAO();

    public List<Client> findAll() {
        return clientDAO.findAll();
    }
}
