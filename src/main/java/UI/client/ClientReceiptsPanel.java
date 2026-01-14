package ui.client;

import model.Car;
import model.Reciept;
import model.Reservation;
import model.User;
import service.CarService;
import service.ReceiptService;
import service.ReservationService;
import ui.common.SessionContext;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientReceiptsPanel extends JPanel {
    private final ReceiptService receiptService = new ReceiptService();
    private final ReservationService reservationService = new ReservationService();
    private final CarService carService = new CarService();
    private final User currentUser = SessionContext.getInstance().getCurrentUser();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public ClientReceiptsPanel() {
        setLayout(new BorderLayout());
        
        tableModel = new DefaultTableModel(new String[]{"Receipt ID", "Repair ID", "Date", "Value"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadClientReceipts();
    }

    private void loadClientReceipts() {
        new SwingWorker<List<Reciept>, Void>() {
            @Override
            protected List<Reciept> doInBackground() {
                // 1. Gaseste masinile clientului
                List<Car> clientCars = carService.findCarsByOwnerId(currentUser.getUser_ID());
                
                // 2. Gaseste ID-urile reparatiilor pentru aceste masini
                List<Integer> repairIds = new ArrayList<>();
                for (Car car : clientCars) {
                    reservationService.findReservationsByCarId(car.getCar_ID())
                        .forEach(r -> repairIds.add(r.getReservation_ID()));
                }

                // 3. Filtreaza toate chitantele din sistem
                return receiptService.findAll().stream()
                    .filter(receipt -> receipt.getReservation() != null && repairIds.contains(receipt.getReservation().getReservation_ID()))
                    .collect(Collectors.toList());
            }

            @Override
            protected void done() {
                try {
                    List<Reciept> receipts = get();
                    tableModel.setRowCount(0);
                    for (Reciept r : receipts) {
                        tableModel.addRow(new Object[]{
                            r.getReciept_ID(),
                            r.getReservation().getReservation_ID(),
                            r.getDate(),
                            String.format("%.2f", r.getVal())
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(ClientReceiptsPanel.this, "Failed to load receipts.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }
}
