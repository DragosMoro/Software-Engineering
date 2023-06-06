package com.example.implementation.Repository;


import com.example.implementation.Model.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository implements ICompanyRepository{
    private final SessionFactory sessionFactory;

    public CompanyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Company getById(Integer id) {
        Session session = sessionFactory.openSession();
        Company company = null;
        try {
            company = session.get(Company.class, id);
        } catch (RuntimeException e) {
            System.out.println("Error in getById method from CompanyRepository" + e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return company;
    }

    public void add(Company company) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(company);
            transaction.commit();
        } catch (RuntimeException e) {
            System.out.println("Error in addCompany method from CompanyRepository" + e);
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
            Company company = session.load(Company.class, id);
            if (company != null) {
                session.delete(company);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            System.out.println("Error in deleteCompany method from CompanyRepository" + e);
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void update(Company company) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(company);
            transaction.commit();
        } catch (RuntimeException e) {
            System.out.println("Error in updateCompany method from CompanyRepository" + e);
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public ArrayList<Company> getAll() {
        Session session = sessionFactory.openSession();
        List<Company> companies = new ArrayList<>();
        try {
            String hql = "from Company";
            Query query = session.createQuery(hql);
            companies = query.getResultList();
        } catch (RuntimeException e) {
            System.out.println("Error in getAllCompanies method from CompanyRepository" + e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (ArrayList<Company>) companies;
    }
}
