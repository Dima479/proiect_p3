package service;

import DAO.CarDAO;
import model.Car;
import java.util.List;
import java.util.stream.Collectors;

public class CarService {
    private final CarDAO carDAO = new CarDAO();

    public List<Car> findAll() {
        return carDAO.findAll();
    }

    public List<Car> findCarsByOwnerId(int ownerId) {
        // This is inefficient. A real app would have a dedicated DAO method.
        return carDAO.findAll().stream()
                .filter(car -> car.getOwnerID() == ownerId)
                .collect(Collectors.toList());
    }
}
