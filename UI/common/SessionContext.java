package ui.common;

import model.User;

public class SessionContext {
    private static SessionContext instance;
    private User currentUser;

    private SessionContext() {}

    public static SessionContext getInstance() {
        if (instance == null) {
            instance = new SessionContext();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void clearSession() {
        this.currentUser = null;
    }

    public boolean isAuthenticated() {
        return this.currentUser != null;
    }
}
