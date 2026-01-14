package ui.client;

import model.Car;
import model.Reservation;
import model.User;
import service.CarService;
import service.ReservationService;
import ui.common.SessionContext;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepairsPanel extends JPanel {
    private final ReservationService reservationService = new ReservationService();
    private final CarService carService = new CarService();
    private final User currentUser = SessionContext.getInstance().getCurrentUser();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public ClientRepairsPanel() {
        setLayout(new BorderLayout());
        
        tableModel = new DefaultTableModel(new String[]{"Repair ID", "Car", "Date", "Status", "Details"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadClientRepairs();
    }

    private void loadClientRepairs() {
        new SwingWorker<List<Reservation>, Void>() {
            @Override
            protected List<Reservation> doInBackground() {
                // 1. Gaseste masinile clientului
                List<Car> clientCars = carService.findCarsByOwnerId(currentUser.getUser_ID());
                
                // 2. Gaseste toate reparatiile pentru fiecare masina
                List<Reservation> allRepairs = new ArrayList<>();
                for (Car car : clientCars) {
                    allRepairs.addAll(reservationService.findReservationsByCarId(car.getCar_ID()));
                }
                return allRepairs;
            }

            @Override
            protected void done() {
                try {
                    List<Reservation> reservations = get();
                    tableModel.setRowCount(0);
                    for (Reservation r : reservations) {
                        tableModel.addRow(new Object[]{
                            r.getReservation_ID(),
                            r.getCar(), // Foloseste toString() din Car
                            r.getDate(),
                            r.getStatus(),
                            r.getDetails()
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(ClientRepairsPanel.this, "Failed to load repairs.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }
}
