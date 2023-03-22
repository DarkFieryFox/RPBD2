
package ru.nstu.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.nstu.entity.Parent;

import ru.nstu.util.HibernateUtils;

import java.util.List;

public class ParentRepo extends BaseRepo<Parent, Long> {

    @Override
    protected Class<Parent> getType() {
        return Parent.class;
    }

    @Override
    protected String getTableName() {
        return "Parent";
    }



    public List<Parent> findAllByName(String name) {
        String hql = String.format("From %s where name like :name ", getTableName());

        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Parent> query = session.createQuery(hql, getType());
        query.setParameter("name", "%" + name + "%");

        List<Parent> list = query.list();
        session.close();
        return list;
    }

    public List<Parent> findAllBySurname(String surname) {
        String hql = String.format("From %s where surname like :surname ", getTableName());

        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Parent> query = session.createQuery(hql, getType());
        query.setParameter("surname", "%" + surname + "%");

        List<Parent> list = query.list();
        session.close();
        return list;
    }
    public List<Parent> findAllByPatronymic(String patronymic) {
        String hql = String.format("From %s where patronymic like :patronymic ", getTableName());

        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Parent> query = session.createQuery(hql, getType());
        query.setParameter("patronymic", "%" + patronymic + "%");

        List<Parent> list = query.list();
        session.close();
        return list;
    }

    public List<Parent> findAllByAddress(String address) {
        String hql = String.format("From %s where address like :address ", getTableName());

        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Parent> query = session.createQuery(hql, getType());
        query.setParameter("address", "%" + address + "%");

        List<Parent> list = query.list();
        session.close();
        return list;
    }

    public List<Parent> findAllByDegree(String degree) {
        String hql = String.format("From %s where degree like :degree ", getTableName());

        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Parent> query = session.createQuery(hql, getType());
        query.setParameter("degree", "%" + degree + "%");

        List<Parent> list = query.list();
        session.close();
        return list;
    }

}
