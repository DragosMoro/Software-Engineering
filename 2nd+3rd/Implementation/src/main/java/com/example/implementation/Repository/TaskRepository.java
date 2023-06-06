package com.example.implementation.Repository;


import com.example.implementation.Model.Employee;
import com.example.implementation.Model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository implements ITaskRepository {
    private final SessionFactory sessionFactory;

    public TaskRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Task getById(Integer id) {
        return null;
    }

    @Override
    public void add(Task task) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(task);
            transaction.commit();
        } catch (RuntimeException e) {
            System.out.println("Error in addTask method from TaskRepository"+e);
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }



    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Task task = session.load(Task.class, id);
            if (task != null){
                session.delete(task);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            System.out.println("Error in deleteTask method from TaskRepository" + e);
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();

        }
    }

    @Override
    public void update(Task task) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(task);
            transaction.commit();
        }
        catch (RuntimeException e){
            System.out.println("Error in updateTask method from TaskRepository" + e);
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        finally {
            session.close();
        }


    }

    @Override
    public ArrayList<Task> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Task> tasks = new ArrayList<>();
        try {
            String hql = "from Task";
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            tasks = query.getResultList();
        }
        catch (RuntimeException e){
            System.out.println("Error in getAllTasks method from TaskRepository" + e);
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return (ArrayList<Task>) tasks;
    }


    @Override
    public ArrayList<Task> getTasksByEmployee(Employee employee) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Task> tasks = new ArrayList<>();
        try {
            String hql = "from Task where employee = :employee";
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("employee", employee);
            tasks = query.getResultList();
        }
        catch (RuntimeException e){
            System.out.println("Error in getTasksByEmployee method from TaskRepository" + e);
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return (ArrayList<Task>) tasks;
    }
}
