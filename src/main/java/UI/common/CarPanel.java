package ui.common;

import DAO.CarDAO;
import model.Car;
import model.Client;
import model.User;
import service.ClientService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CarPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfPlate, tfBrand, tfModel, tfYear;
    private JComboBox<Client> clientComboBox;
    private JButton btnSave, btnClear, btnDelete;
    private CarDAO carDAO;
    private ClientService clientService;

    public CarPanel() {
        carDAO = new CarDAO();
        clientService = new ClientService();
        setLayout(new BorderLayout());

        // --- Formular Adaugare ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Adauga Masina"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        clientComboBox = new JComboBox<>();
        addFormField(formPanel, gbc, "Client:", clientComboBox, 0);
        addFormField(formPanel, gbc, "Nr. Inmatriculare:", tfPlate = new JTextField(20), 1);
        addFormField(formPanel, gbc, "Marca:", tfBrand = new JTextField(20), 2);
        addFormField(formPanel, gbc, "Model:", tfModel = new JTextField(20), 3);
        addFormField(formPanel, gbc, "An:", tfYear = new JTextField(20), 4);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnClear = new JButton("Curata");
        btnSave = new JButton("Salveaza");
        btnPanel.add(btnClear);
        btnPanel.add(btnSave);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);
        add(formPanel, BorderLayout.NORTH);

        // --- Tabel Listare ---
        String[] cols = {"ID", "Client", "Nr. Inmatriculare", "Marca", "Model"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Buton Stergere ---
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnDelete = new JButton("Sterge Masina Selectata");
        southPanel.add(btnDelete);
        add(southPanel, BorderLayout.SOUTH);

        // --- Evenimente ---
        btnClear.addActionListener(e -> clearForm());
        btnSave.addActionListener(e -> saveCar());
        btnDelete.addActionListener(e -> deleteCar());

        loadInitialData();
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JComponent comp, int y) {
        gbc.gridx = 0; gbc.gridy = y; gbc.weightx = 0.1;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.gridy = y; gbc.weightx = 0.9;
        panel.add(comp, gbc);
    }

    private void loadInitialData() {
        new SwingWorker<List<Client>, Void>() {
            @Override
            protected List<Client> doInBackground() { return clientService.findAll(); }
            @Override
            protected void done() {
                try {
                    clientComboBox.setModel(new DefaultComboBoxModel<>(get().toArray(new Client[0])));
                    clientComboBox.setSelectedIndex(-1);
                } catch (Exception e) { e.printStackTrace(); }
            }
        }.execute();
        refreshCarTable();
    }

    private void refreshCarTable() {
        new SwingWorker<List<Car>, Void>() {
            @Override
            protected List<Car> doInBackground() { return carDAO.findAll(); }
            @Override
            protected void done() {
                try {
                    List<Car> cars = get();
                    tableModel.setRowCount(0);
                    List<Client> clients = clientService.findAll();
                    for (Car c : cars) {
                        String clientName = clients.stream()
                            .filter(client -> client.getUser_ID() == c.getOwnerID())
                            .map(User::getName)
                            .findFirst().orElse("N/A");
                        tableModel.addRow(new Object[]{c.getCar_ID(), clientName, c.getLicence_Plate(), c.getBrand(), c.getModel()});
                    }
                } catch (Exception e) { e.printStackTrace(); }
            }
        }.execute();
    }

    private void clearForm() {
        tfPlate.setText("");
        tfBrand.setText("");
        tfModel.setText("");
        tfYear.setText("");
        clientComboBox.setSelectedIndex(-1);
    }

    private void saveCar() {
        Client selectedClient = (Client) clientComboBox.getSelectedItem();
        if (selectedClient == null) {
            JOptionPane.showMessageDialog(this, "Selectati un client!");
            return;
        }
        String plate = tfPlate.getText();
        if (plate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nr. de inmatriculare este obligatoriu!");
            return;
        }
        int year = 0;
        try { year = Integer.parseInt(tfYear.getText()); } catch (Exception ex) {}
        Car car = new Car(0, selectedClient.getUser_ID(), plate, tfBrand.getText(), tfModel.getText(), year, "VIN_DEFAULT");
        carDAO.create(car);
        refreshCarTable();
        clearForm();
    }

    private void deleteCar() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) table.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Sunteti sigur ca doriti sa stergeti masina selectata?", "Confirmare Stergere", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                carDAO.delete(id);
                refreshCarTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Va rugam sa selectati o masina din tabel.", "Eroare", JOptionPane.WARNING_MESSAGE);
        }
    }
}
