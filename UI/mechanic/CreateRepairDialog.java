package ui.mechanic;

import model.Car;
import model.Mechanic;
import model.Reservation;
import service.CarService;
import service.ReservationService;
import ui.common.SessionContext;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class CreateRepairDialog extends JDialog {
    private final CarService carService = new CarService();
    private final ReservationService reservationService = new ReservationService();
    private final Mechanic currentMechanic;

    private JComboBox<Car> carComboBox;
    private JTextArea detailsTextArea;

    public CreateRepairDialog(Frame owner) {
        super(owner, "Create New Repair", true);
        this.currentMechanic = (Mechanic) SessionContext.getInstance().getCurrentUser();

        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        initComponents();
        loadCars();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Car selection
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Car:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        carComboBox = new JComboBox<>();
        panel.add(carComboBox, gbc);

        // Details
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.NORTH;
        panel.add(new JLabel("Details:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        detailsTextArea = new JTextArea(5, 20);
        panel.add(new JScrollPane(detailsTextArea), gbc);

        add(panel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> createRepair());
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadCars() {
        new SwingWorker<List<Car>, Void>() {
            @Override
            protected List<Car> doInBackground() {
                return carService.findAll();
            }
            @Override
            protected void done() {
                try {
                    List<Car> cars = get();
                    carComboBox.setModel(new DefaultComboBoxModel<>(cars.toArray(new Car[0])));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private void createRepair() {
        Car selectedCar = (Car) carComboBox.getSelectedItem();
        if (selectedCar == null) {
            JOptionPane.showMessageDialog(this, "Please select a car.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String details = detailsTextArea.getText();
        if (details.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide repair details.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Reservation newReservation = new Reservation(selectedCar, LocalDate.now(), details, currentMechanic);
        reservationService.create(newReservation);

        JOptionPane.showMessageDialog(this, "Repair order created successfully!");
        dispose();
    }
}
