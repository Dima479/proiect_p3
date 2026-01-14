package service;

import DAO.CarDAO;
import model.Car;
import java.util.List;

public class CarService {
    private final CarDAO carDAO = new CarDAO();

    public List<Car> findAll() {
        return carDAO.findAll();
    }

    public List<Car> findCarsByOwnerId(int ownerId) {
        return carDAO.findByOwnerId(ownerId);
    }
}
