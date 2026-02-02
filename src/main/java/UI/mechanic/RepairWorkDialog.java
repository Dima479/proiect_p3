package ui.mechanic;

import model.Part;
import model.Reciept;
import model.Reservation;
import model.Used_Parts;
import service.PartService;
import service.ReceiptService;
import service.ReservationService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class RepairWorkDialog extends JDialog {
    private final ReservationService reservationService = new ReservationService();
    private final PartService partService = new PartService();
    private final ReceiptService receiptService = new ReceiptService();
    private final Reservation reservation;

    private JComboBox<Reservation.Status> statusComboBox;
    private JTextArea detailsTextArea;
    private JList<String> usedPartsList;
    private DefaultListModel<String> listModel;

    public RepairWorkDialog(Frame owner, int reservationId) {
        super(owner, "Edit Repair #" + reservationId, true);
        Reservation loadedReservation;
        boolean hasError = false;
        try {
            loadedReservation = reservationService.findById(reservationId);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            loadedReservation = null;
            hasError = true;
        }
        this.reservation = loadedReservation;
        if (hasError) {
            return;
        }
        if (this.reservation == null) {
            JOptionPane.showMessageDialog(this, "Reservation not found.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setSize(500, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        initComponents();
        loadData();
    }

    private void initComponents() {

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        statusComboBox = new JComboBox<>(Reservation.Status.values());
        formPanel.add(statusComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.NORTH;
        formPanel.add(new JLabel("Details:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        detailsTextArea = new JTextArea(5, 20);
        formPanel.add(new JScrollPane(detailsTextArea), gbc);

        JPanel partsPanel = new JPanel(new BorderLayout());
        partsPanel.setBorder(BorderFactory.createTitledBorder("Used Parts"));
        listModel = new DefaultListModel<>();
        usedPartsList = new JList<>(listModel);
        partsPanel.add(new JScrollPane(usedPartsList), BorderLayout.CENTER);

        JButton addPartButton = new JButton("Add Part");
        addPartButton.addActionListener(e -> addPart());
        partsPanel.add(addPartButton, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, partsPanel);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);

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
            if (qtyStr == null || qtyStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int quantity = Integer.parseInt(qtyStr);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                listModel.addElement(selectedPart.getName() + " (Qty: " + quantity + ")");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveChanges() {
        Reservation.Status status = (Reservation.Status) statusComboBox.getSelectedItem();
        String details = detailsTextArea.getText();
        if (status == null) {
            JOptionPane.showMessageDialog(this, "Status is required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (details == null || details.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Details are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Reciept existingReciept = null;
        float receiptValue = 0;
        if (status == Reservation.Status.COMPLETED) {
            existingReciept = receiptService.findByReservationId(reservation.getReservation_ID());
            if (existingReciept == null) {
                String valStr = JOptionPane.showInputDialog(this, "Enter receipt value:");
                if (valStr == null || valStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Receipt value is required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    receiptValue = Float.parseFloat(valStr);
                    if (receiptValue <= 0) {
                        JOptionPane.showMessageDialog(this, "Receipt value must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Receipt value must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        reservation.setStatus(status);
        reservation.setDetails(details);

        try {
            reservationService.update(reservation);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (status == Reservation.Status.COMPLETED && existingReciept == null) {
            Reciept newReciept = new Reciept(0, receiptValue, LocalDate.now(), reservation);
            try {
                receiptService.create(newReciept);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Reservation updated successfully!");
        dispose();
    }
}
