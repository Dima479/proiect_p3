package ui;

import model.User;
import ui.admin.MechanicsManagementPanel;
import ui.admin.PartsInventoryPanel;
import ui.auth.LoginFrame;
import ui.client.ClientReceiptsPanel;
import ui.client.ClientRepairsPanel;
import ui.client.MyCarsPanel;
import ui.common.CarPanel;
import ui.common.ClientPanel;
import ui.common.SessionContext;
import ui.mechanic.AssignedRepairsPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final User currentUser;

    public MainFrame() {
        this.currentUser = SessionContext.getInstance().getCurrentUser();
        if (this.currentUser == null) {
            JOptionPane.showMessageDialog(null, "No authenticated user found. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        setTitle("Service Management - " + currentUser.getName() + " (" + currentUser.getRole() + ")");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMenuBar();
        createMainContent();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> logout());
        fileMenu.add(logoutItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void createMainContent() {
        JTabbedPane tabbedPane = new JTabbedPane();
        String role = currentUser.getRole().toUpperCase();

        switch (role) {
            case "ADMIN":
                tabbedPane.addTab("Clients", new ClientPanel());
                tabbedPane.addTab("Cars", new CarPanel());
                tabbedPane.addTab("Mechanics", new MechanicsManagementPanel());
                tabbedPane.addTab("Inventory", new PartsInventoryPanel());
                break;
            case "MECHANIC":
                tabbedPane.addTab("Assigned Repairs", new AssignedRepairsPanel());
                tabbedPane.addTab("Clients", new ClientPanel());
                tabbedPane.addTab("Cars", new CarPanel());
                break;
            case "CLIENT":
                tabbedPane.addTab("My Cars", new MyCarsPanel());
                tabbedPane.addTab("My Repairs", new ClientRepairsPanel());
                tabbedPane.addTab("My Receipts", new ClientReceiptsPanel());
                break;
            default:
                add(new JLabel("Unknown role. Please contact support."), BorderLayout.CENTER);
                return;
        }

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void logout() {
        SessionContext.getInstance().clearSession();
        dispose();
        new LoginFrame().setVisible(true);
    }
}
