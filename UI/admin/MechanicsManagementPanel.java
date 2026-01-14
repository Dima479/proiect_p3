package ui.admin;

import model.Mechanic;
import service.MechanicService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MechanicsManagementPanel extends JPanel {
    private final MechanicService mechanicService = new MechanicService();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public MechanicsManagementPanel() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Experience"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Mechanic");
        addButton.addActionListener(e -> addMechanic());
        buttonPanel.add(addButton);
        
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> deleteMechanic());
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadMechanics();
    }

    private void loadMechanics() {
        new SwingWorker<List<Mechanic>, Void>() {
            @Override
            protected List<Mechanic> doInBackground() {
                return mechanicService.findAll();
            }

            @Override
            protected void done() {
                try {
                    List<Mechanic> mechanics = get();
                    tableModel.setRowCount(0);
                    for (Mechanic m : mechanics) {
                        tableModel.addRow(new Object[]{m.getUser_ID(), m.getName(), m.getEmail(), m.getExperience()});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    
    private void addMechanic() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField experienceField = new JTextField();
        
        Object[] message = {
            "Name:", nameField,
            "Email:", emailField,
            "Password:", passwordField,
            "Experience:", experienceField
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Add Mechanic", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Mechanic m = new Mechanic();
            m.setName(nameField.getText());
            m.setEmail(emailField.getText());
            m.setPassword(password);
            m.setExperience(Double.parseDouble(experienceField.getText()));
            m.setRole("MECHANIC");

            mechanicService.save(m);
            loadMechanics();
        }
    }
    
    private void deleteMechanic() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            mechanicService.delete(id);
            loadMechanics();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a mechanic to delete.");
        }
    }
}
