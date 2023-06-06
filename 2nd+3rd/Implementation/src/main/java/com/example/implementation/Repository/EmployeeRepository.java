package com.example.implementation.Repository;


import com.example.implementation.Model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements IEmployeeRepository{
    private final SessionFactory sessionFactory;

    public EmployeeRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Employee getById(Integer id) {
        Session session = sessionFactory.openSession();
        Employee employee = null;
        try {
            employee = session.get(Employee.class, id);
        } catch (RuntimeException e) {
            System.out.println("Error in getById method from EmployeeRepository" + e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employee;
    }

    public void add(Employee employee) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        } catch (RuntimeException e) {
            System.out.println("Error in addEmployee method from EmployeeRepository"+e);
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
            Employee employee = session.load(Employee.class, id);
            if (employee != null){
                session.delete(employee);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            System.out.println("Error in deleteEmployee method from EmployeeRepository" + e);
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void update(Employee employee) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
        }
        catch (RuntimeException e){
            System.out.println("Error in updateEmployee method from EmployeeRepository" + e);
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    public ArrayList<Employee> getAll() {
        Session session = sessionFactory.openSession();
        List<Employee> employees = new ArrayList<>();
        try {
            String hql = "from Employee";
            Query query = session.createQuery(hql);
            employees = query.getResultList();
        }
        catch (RuntimeException e){
            System.out.println("Error in getAllEmployees method from EmployeeRepository" + e);
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return (ArrayList<Employee>) employees;
    }

    @Override
    public Employee findByEmailAndPassword(String email, String password) {
        Session session = sessionFactory.openSession();
        Employee employee = null;
        try {
            String hql = "from Employee e where e.email = :email and e.password = :password";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            query.setParameter("password", password);
            employee = (Employee) query.uniqueResult();
        }
        catch (RuntimeException e){
            System.out.println("Error in findByEmailAndPassword method from EmployeeRepository" + e);
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return employee;
    }

}
