package ui.admin;

import model.Part;
import service.PartService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PartsInventoryPanel extends JPanel {
    private final PartService partService = new PartService();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public PartsInventoryPanel() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Stock"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Part");
        addButton.addActionListener(e -> addPart());
        buttonPanel.add(addButton);
        
        JButton editButton = new JButton("Edit Selected");
        editButton.addActionListener(e -> editPart());
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> deletePart());
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadParts();
    }

    private void loadParts() {
        new SwingWorker<List<Part>, Void>() {
            @Override
            protected List<Part> doInBackground() {
                return partService.findAll();
            }

            @Override
            protected void done() {
                try {
                    List<Part> parts = get();
                    tableModel.setRowCount(0);
                    for (Part p : parts) {
                        tableModel.addRow(new Object[]{p.getPart_ID(), p.getName(), p.getPrice(), p.getQuantity_in_stock()});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private void addPart() {
        Part p = new Part();
        boolean success = showPartDialog(p, "Add New Part");
        if (success) {
            partService.save(p);
            loadParts();
        }
    }

    private void editPart() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            // This is inefficient, should be a findById in service
            Part partToEdit = partService.findAll().stream().filter(p -> p.getPart_ID() == id).findFirst().orElse(null);
            if (partToEdit != null) {
                boolean success = showPartDialog(partToEdit, "Edit Part");
                if (success) {
                    partService.save(partToEdit);
                    loadParts();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a part to edit.");
        }
    }

    private void deletePart() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            partService.delete(id);
            loadParts();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a part to delete.");
        }
    }

    private boolean showPartDialog(Part part, String title) {
        JTextField nameField = new JTextField(part.getName());
        JTextField priceField = new JTextField(String.valueOf(part.getPrice()));
        JTextField stockField = new JTextField(String.valueOf(part.getQuantity_in_stock()));

        Object[] message = {
            "Name:", nameField,
            "Price:", priceField,
            "Stock:", stockField
        };

        int option = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            part.setName(nameField.getText());
            part.setPrice(Float.parseFloat(priceField.getText()));
            part.setQuantity_in_stock(Integer.parseInt(stockField.getText()));
            return true;
        }
        return false;
    }
}
