package ui.auth;

import model.User;
import service.AuthService;
import UI.MainFrame;
import ui.common.SessionContext;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JButton loginButton = new JButton("Login");
    private final AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle("Login - Service Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        loginButton.addActionListener(e -> performLogin());
        getRootPane().setDefaultButton(loginButton);
    }

    private void performLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        loginButton.setEnabled(false);
        loginButton.setText("Logging in...");

        new SwingWorker<User, Void>() {
            @Override
            protected User doInBackground() {
                return authService.login(email, password);
            }

            @Override
            protected void done() {
                try {
                    User user = get();
                    if (user != null) {
                        SessionContext.getInstance().setCurrentUser(user);
                        dispose();
                        new MainFrame().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                "Invalid email or password.",
                                "Login Failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "An error occurred during login.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    loginButton.setEnabled(true);
                    loginButton.setText("Login");
                }
            }
        }.execute();
    }
}
