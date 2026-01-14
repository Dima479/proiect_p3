package DB;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * clasa utilitara pentru gestionarea conexiunii jpa
 * asigura crearea unei singure instante de entitymanagerfactory
 */
public class JPAUtil {
    private static final EntityManagerFactory entityManagerFactory;

    /**
    metoda statica pentru ca sa se efecteze odata cu initializarea clasei si sa se pastreze pana la inchidere
    */
    static {
        try {
            // initializarea fabricii folosind unitatea de persistenta din persistence.xml
            entityManagerFactory = Persistence.createEntityManagerFactory("GestiuneServicePU");
        } catch (Throwable ex) {
            System.err.println("Initial EntityManagerFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * returneaza instanta curenta de entitymanagerfactory
     * @return fabrica de entity manager folosita pentru interactiunea cu baza de date
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    /**
     * inchide conexiunea si elibereaza resursele
     * metoda trebuie apelata la inchiderea aplicatiei
     */
    public static void shutdown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
