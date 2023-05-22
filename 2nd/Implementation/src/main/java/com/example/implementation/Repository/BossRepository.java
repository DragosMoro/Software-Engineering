package com.example.implementation.Repository;


import com.example.implementation.Model.Boss;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class BossRepository implements IBossRepository{


    private final SessionFactory sessionFactory;

    public BossRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Boss getById(Integer id) {
        Session session = sessionFactory.openSession();
        Boss boss = null;
        try {
            boss = session.get(Boss.class, id);
        } catch (RuntimeException e) {
            System.out.println("Error in getById method from BossRepository" + e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return boss;
    }

    public void add(Boss boss) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(boss);
            transaction.commit();
        } catch (RuntimeException e) {
            System.out.println("Error in addBoss method from BossRepository"+e);
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
            Boss boss = session.load(Boss.class, id);
            if (boss != null){
                session.delete(boss);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            System.out.println("Error in deleteBoss method from BossRepository" + e);
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void update(Boss boss) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(boss);
            transaction.commit();
        }
        catch (RuntimeException e){
            System.out.println("Error in updateBoss method from BossRepository" + e);
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    public ArrayList<Boss> getAll() {
        Session session = sessionFactory.openSession();
        List<Boss> bosses = new ArrayList<>();
        try {
            String hql = "from Boss";
            Query query = session.createQuery(hql);
            bosses = query.getResultList();
        }
        catch (RuntimeException e){
            System.out.println("Error in getAllBosses method from BossRepository" + e);
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return (ArrayList<Boss>) bosses;
    }

    @Override
    public Boss findByEmailAndPassword(String email, String password) {
        Session session = sessionFactory.openSession();
        Boss boss = null;
        try {
            String hql = "from Boss b where b.email = :email and b.password = :password";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            query.setParameter("password", password);
            boss = (Boss) query.uniqueResult();
        }
        catch (RuntimeException e){
            System.out.println("Error in findByEmailAndPassword method from BossRepository" + e);
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return boss;
    }

}
