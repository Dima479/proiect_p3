package ui.client;

import model.Car;
import model.User;
import service.CarService;
import ui.common.SessionContext;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MyCarsPanel extends JPanel {
    private final CarService carService = new CarService();
    private final User currentUser = SessionContext.getInstance().getCurrentUser();
    private final JTable carTable;
    private final DefaultTableModel tableModel;

    public MyCarsPanel() {
        setLayout(new BorderLayout());
        
        tableModel = new DefaultTableModel(new String[]{"ID", "Make", "Model", "Year", "VIN"}, 0);
        carTable = new JTable(tableModel);
        add(new JScrollPane(carTable), BorderLayout.CENTER);

        loadCars();
    }

    private void loadCars() {
        new SwingWorker<List<Car>, Void>() {
            @Override
            protected List<Car> doInBackground() {
                return carService.findCarsByOwnerId(currentUser.getUser_ID());
            }

            @Override
            protected void done() {
                try {
                    List<Car> cars = get();
                    tableModel.setRowCount(0); // Clear existing data
                    for (Car car : cars) {
                        tableModel.addRow(new Object[]{car.getCar_ID(), car.getBrand(), car.getModel(), car.getYear(), car.getLicence_Plate()});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(MyCarsPanel.this, "Failed to load cars.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }
}
