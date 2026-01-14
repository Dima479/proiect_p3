package DAO;

import DB.JPAUtil;
import model.Reservation;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.List;

public class ReservationDAO {

    public void create(Reservation entity) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Reservation find(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Reservation.class, id);
        } finally {
            em.close();
        }
    }

    public Reservation findByIdWithDetails(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                "SELECT r FROM Reservation r LEFT JOIN FETCH r.Parts WHERE r.Reservation_ID = :id", 
                Reservation.class)
                .setParameter("id", id)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void update(Reservation entity) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void delete(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Reservation entity = em.find(Reservation.class, id);
            if (entity != null) {
                em.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Reservation> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Incarcam si masinile pentru a evita probleme similare in tabel
            return em.createQuery("SELECT DISTINCT r FROM Reservation r LEFT JOIN FETCH r.car", Reservation.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Reservation> findReservationsByMechanicId(int mechanicId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Reservation r WHERE r.mechanic.user_ID = :mechanicId", Reservation.class)
                    .setParameter("mechanicId", mechanicId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Reservation> findReservationsByCarId(int carId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Reservation r WHERE r.car.car_ID = :carId", Reservation.class)
                    .setParameter("carId", carId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
