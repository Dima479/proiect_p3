package ui.mechanic;

import model.Reservation;
import model.User;
import service.ReservationService;
import ui.common.SessionContext;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AssignedRepairsPanel extends JPanel {
    private final ReservationService reservationService = new ReservationService();
    private final User currentUser = SessionContext.getInstance().getCurrentUser();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public AssignedRepairsPanel() {
        setLayout(new BorderLayout());
        
        tableModel = new DefaultTableModel(new String[]{"ID", "Car", "Date", "Status", "Details"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton createButton = new JButton("Create New Repair");
        createButton.addActionListener(e -> openCreateDialog());
        buttonPanel.add(createButton);

        JButton editButton = new JButton("Edit Selected Repair");
        editButton.addActionListener(e -> openEditDialog());
        buttonPanel.add(editButton);
        
        add(buttonPanel, BorderLayout.SOUTH);

        loadReservations();
    }

    private void loadReservations() {
        new SwingWorker<List<Reservation>, Void>() {
            @Override
            protected List<Reservation> doInBackground() {
                return reservationService.findReservationsByMechanicId(currentUser.getUser_ID());
            }

            @Override
            protected void done() {
                try {
                    List<Reservation> reservations = get();
                    tableModel.setRowCount(0);
                    for (Reservation r : reservations) {
                        tableModel.addRow(new Object[]{
                            r.getReservation_ID(), 
                            r.getCar(), // toString() from Car will be used
                            r.getDate(), 
                            r.getStatus(), 
                            r.getDetails()
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(AssignedRepairsPanel.this, "Failed to load reservations.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void openCreateDialog() {
        CreateRepairDialog dialog = new CreateRepairDialog((Frame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        loadReservations(); // Refresh list after creation
    }

    private void openEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a repair to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int reservationId = (int) table.getValueAt(selectedRow, 0);
        RepairWorkDialog dialog = new RepairWorkDialog((Frame) SwingUtilities.getWindowAncestor(this), reservationId);
        dialog.setVisible(true);
        loadReservations(); // Refresh list after edit
    }
}
