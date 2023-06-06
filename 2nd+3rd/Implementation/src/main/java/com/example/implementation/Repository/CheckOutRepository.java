package com.example.implementation.Repository;

import com.example.implementation.Model.CheckOut;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckOutRepository implements ICheckOutRepository{

    private final SessionFactory sessionFactory;

    public CheckOutRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public List<CheckOut> getCheckOutsAfter(LocalDateTime lastCheckTime) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<CheckOut> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDateTime = lastCheckTime.format(formatter);


        try {
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CheckOut> cr = cb.createQuery(CheckOut.class);
            Root<CheckOut> root = cr.from(CheckOut.class);

            cr.select(root).where(cb.greaterThan(root.get("checkoutDate"), formattedDateTime));


            Query<CheckOut> query = session.createQuery(cr);
            result = query.getResultList();
            for (CheckOut checkOut : result) {
                checkOut.getEmployee().getEmail();
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }
    public void add(CheckOut checkOut){
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(checkOut);
            transaction.commit();
        } catch (RuntimeException e) {
            System.out.println("Error in addCheckOut method from CheckOutRepository" + e);
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
}
