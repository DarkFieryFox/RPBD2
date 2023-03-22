package ru.nstu.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.nstu.util.HibernateUtils;

import java.io.Serializable;
import java.util.List;

public abstract class BaseRepo<T, ID extends Serializable> {

    public List<T> findAll() {
        String hql = String.format("From %s", getTableName());

        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<T> query = session.createQuery(hql, getType());

        List<T> list = query.list();
        session.close();
        return list;
    }

    public T findById(ID id) {
        String hql = String.format("From %s where id = :id", getTableName());

        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<T> query = session.createQuery(hql, getType());
        query.setParameter("id", id);

        T singleResult = query.getSingleResult();
        session.close();
        return singleResult;
    }

    public void save(T entity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(entity);
        tx1.commit();
        session.close();
    }

    public void edit(T entity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(entity);
        tx1.commit();
        session.close();
    }

    public void delete(T entity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(entity);
        tx1.commit();
        session.close();
    }

    protected abstract Class<T> getType();

    protected abstract String getTableName();

}
