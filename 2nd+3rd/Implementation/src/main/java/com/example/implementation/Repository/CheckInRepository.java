package com.example.implementation.Repository;

import com.example.implementation.Model.CheckIn;
import com.example.implementation.Model.CheckOut;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckInRepository implements ICheckInRepository{
    private final SessionFactory sessionFactory;

    public CheckInRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public CheckIn getById(Integer id) {
        Session session = sessionFactory.openSession();
        CheckIn checkIn = null;
        try {
            checkIn = session.get(CheckIn.class, id);
        } catch (RuntimeException e) {
            System.out.println("Error in getById method from CheckInRepository" + e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return checkIn;
    }

    public void add(CheckIn checkIn) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(checkIn);
            transaction.commit();
        } catch (RuntimeException e) {
            System.out.println("Error in addCheckIn method from CheckInRepository" + e);
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            CheckIn checkIn = session.load(CheckIn.class, id);
            if (checkIn != null) {
                session.delete(checkIn);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            System.out.println("Error in deleteCheckIn method from CheckInRepository" + e);
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void update(CheckIn checkIn) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(checkIn);
            transaction.commit();
        } catch (RuntimeException e) {
            System.out.println("Error in updateCheckIn method from CheckInRepository" + e);
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public ArrayList<CheckIn> getAll() {
        Session session = sessionFactory.openSession();
        List<CheckIn> checkIns = new ArrayList<>();
        try {
            String hql = "from CheckIn";
            Query query = session.createQuery(hql);
            checkIns = query.getResultList();
            for (CheckIn checkIn : checkIns) {
                checkIn.getEmployee().getFirstName();
                checkIn.getEmployee().getLastName();
            }

        } catch (RuntimeException e) {
            System.out.println("Error in getAllCheckIns method from CheckInRepository" + e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (ArrayList<CheckIn>) checkIns;
    }



}
