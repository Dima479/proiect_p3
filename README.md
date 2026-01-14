# Service Management UI

This project is a Java Swing UI for a service management application.

## How to Run

1.  Make sure you have a MySQL database running with the schema `service_auto_db`.
2.  Update the database credentials in `src/main/resources/META-INF/persistence.xml`.
3.  Run the `App.java` file to start the application.
4.  Log in with a user from your database. Example credentials might be:
    -   **Admin**: `admin@service.ro` / `pass123`
    -   **Client**: `client@yahoo.com` / `clientpass`

## UI Screens

### Common
-   **Login**: Authenticates users.

### Client Role
-   **My Cars**: Lists all cars owned by the logged-in client.

### Admin Role
-   **Mechanics Management**: Allows adding and deleting mechanics.

### Mechanic Role
-   (Not yet implemented)
