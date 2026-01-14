package ui.mechanic;

import model.Part;
import model.Reservation;
import model.Used_Parts;
import service.PartService;
import service.ReservationService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RepairWorkDialog extends JDialog {
    private final ReservationService reservationService = new ReservationService();
    private final PartService partService = new PartService();
    private final Reservation reservation;

    private JComboBox<Reservation.Status> statusComboBox;
    private JTextArea detailsTextArea;
    private JList<String> usedPartsList;
    private DefaultListModel<String> listModel;

    public RepairWorkDialog(Frame owner, int reservationId) {
        super(owner, "Edit Repair #" + reservationId, true);
        this.reservation = reservationService.findById(reservationId);

        setSize(500, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        initComponents();
        loadData();
    }

    private void initComponents() {
        // --- Form Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Status
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        statusComboBox = new JComboBox<>(Reservation.Status.values());
        formPanel.add(statusComboBox, gbc);

        // Details
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.NORTH;
        formPanel.add(new JLabel("Details:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        detailsTextArea = new JTextArea(5, 20);
        formPanel.add(new JScrollPane(detailsTextArea), gbc);

        // --- Used Parts Panel ---
        JPanel partsPanel = new JPanel(new BorderLayout());
        partsPanel.setBorder(BorderFactory.createTitledBorder("Used Parts"));
        listModel = new DefaultListModel<>();
        usedPartsList = new JList<>(listModel);
        partsPanel.add(new JScrollPane(usedPartsList), BorderLayout.CENTER);
        
        JButton addPartButton = new JButton("Add Part");
        addPartButton.addActionListener(e -> addPart());
        partsPanel.add(addPartButton, BorderLayout.SOUTH);

        // --- Main Layout ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, partsPanel);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);

        // --- Bottom Buttons ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveChanges());
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        if (reservation != null) {
            statusComboBox.setSelectedItem(reservation.getStatus());
            detailsTextArea.setText(reservation.getDetails());
            
            listModel.clear();
            if (reservation.getParts() != null) {
                for (Used_Parts up : reservation.getParts()) {
                    listModel.addElement(up.getPart().getName() + " (Qty: " + up.getQuantity() + ")");
                }
            }
        }
    }

    private void addPart() {
        // In a real app, this would be a more complex search dialog
        List<Part> allParts = partService.findAll();
        Part selectedPart = (Part) JOptionPane.showInputDialog(
                this,
                "Select a part:",
                "Add Part",
                JOptionPane.PLAIN_MESSAGE,
                null,
                allParts.toArray(),
                null);

        if (selectedPart != null) {
            String qtyStr = JOptionPane.showInputDialog(this, "Enter quantity for " + selectedPart.getName() + ":");
            try {
                int quantity = Integer.parseInt(qtyStr);
                // This logic is simplified. A real app would check stock and update DB relations.
                listModel.addElement(selectedPart.getName() + " (Qty: " + quantity + ")");
                // TODO: Add the Used_Parts object to the reservation's list
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveChanges() {
        reservation.setStatus((Reservation.Status) statusComboBox.getSelectedItem());
        reservation.setDetails(detailsTextArea.getText());
        
        // The parts logic is not fully implemented here, just the status/details update
        reservationService.update(reservation);
        
        JOptionPane.showMessageDialog(this, "Reservation updated successfully!");
        dispose();
    }
}
