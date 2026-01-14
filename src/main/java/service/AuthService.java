package service;

import DAO.UserDAO;
import model.User;
import java.util.List;

public class AuthService {
    private UserDAO userDAO;
    private User currentUser;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public User login(String email, String password) {
        List<User> users = userDAO.findAll();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                this.currentUser = user;
                return user;
            }
        }
        return null;
    }


    public void logout() {
        this.currentUser = null;
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public boolean hasRole(String role) {
        return currentUser != null && currentUser.getRole().equalsIgnoreCase(role);
    }
}
