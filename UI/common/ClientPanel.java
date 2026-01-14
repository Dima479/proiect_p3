package ui.common;

import DAO.ClientDAO;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClientPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private ClientDAO clientDAO;

    public ClientPanel() {
        clientDAO = new ClientDAO();
        setLayout(new BorderLayout());

        // --- Tabel ---
        String[] cols = {"ID", "Nume", "Email", "CNP", "Telefon"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Butoane ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Adauga Client");
        JButton deleteButton = new JButton("Sterge Client Selectat");
        
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- Evenimente ---
        addButton.addActionListener(e -> addClient());
        deleteButton.addActionListener(e -> deleteClient());

        refreshClientTable();
    }

    private void refreshClientTable() {
        new SwingWorker<List<Client>, Void>() {
            @Override
            protected List<Client> doInBackground() { return clientDAO.findAll(); }
            @Override
            protected void done() {
                try {
                    List<Client> clients = get();
                    tableModel.setRowCount(0);
                    for (Client c : clients) {
                        tableModel.addRow(new Object[]{c.getUser_ID(), c.getName(), c.getEmail(), c.getCNP(), c.getPhone_Number()});
                    }
                } catch (Exception e) { e.printStackTrace(); }
            }
        }.execute();
    }

    private void addClient() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField cnpField = new JTextField();
        JTextField phoneField = new JTextField();

        Object[] message = {
            "Nume:", nameField,
            "Email:", emailField,
            "Parola:", passwordField,
            "CNP:", cnpField,
            "Telefon:", phoneField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Adauga Client Nou", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());
            if (password.isEmpty() || nameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Numele si parola sunt obligatorii!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Client client = new Client(0, nameField.getText(), emailField.getText(), password, "CLIENT", cnpField.getText(), phoneField.getText());
            clientDAO.create(client);
            refreshClientTable();
        }
    }

    private void deleteClient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) table.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Sunteti sigur ca doriti sa stergeti clientul selectat?", "Confirmare Stergere", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                clientDAO.delete(id);
                refreshClientTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Va rugam sa selectati un client din tabel.", "Eroare", JOptionPane.WARNING_MESSAGE);
        }
    }
}
