package ru.nstu.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.nstu.entity.Schoolchild;
import ru.nstu.util.HibernateUtils;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class SchoolchildRepo extends BaseRepo<Schoolchild, Long> {

    @Override
    protected Class<Schoolchild> getType() {
        return Schoolchild.class;
    }

    @Override
    protected String getTableName() {
        return "Schoolchild";
    }


    public List<Schoolchild> findAllByName(String name) {
        String hql = String.format("From %s where name like :name ", getTableName());

        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Schoolchild> query = session.createQuery(hql, getType());
        query.setParameter("name", "%" + name + "%");

        List<Schoolchild> list = query.list();
        session.close();
        return list;
    }
    public List<Schoolchild> findAllBySurname(String surname) {
        String hql = String.format("From %s where surname like :surname ", getTableName());

        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Schoolchild> query = session.createQuery(hql, getType());
        query.setParameter("surname", "%" + surname + "%");

        List<Schoolchild> list = query.list();
        session.close();
        return list;
    }
    public List<Schoolchild> findAllByPatronymic(String patronymic) {
        String hql = String.format("From %s where patronymic like :patronymic ", getTableName());

        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Schoolchild> query = session.createQuery(hql, getType());
        query.setParameter("patronymic", "%" + patronymic + "%");

        List<Schoolchild> list = query.list();
        session.close();
        return list;
    }

    public List<Schoolchild> findAllByAddress(String address) {
        String hql = String.format("From %s where address like :address ", getTableName());

        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Schoolchild> query = session.createQuery(hql, getType());
        query.setParameter("address", "%" + address + "%");

        List<Schoolchild> list = query.list();
        session.close();
        return list;
    }

    public List<Schoolchild> findAllByYear_admission(Integer year_admission) {
        String hql = String.format("From %s where upper(cast(year_admission as string)) like :year_admission ", getTableName());

        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Schoolchild> query = session.createQuery(hql, getType());
        query.setParameter("year_admission", "%" + year_admission + "%");

        List<Schoolchild> list = query.list();
        session.close();
        return list;
    }

    public List<Schoolchild> findAllByBirthday(Date birthday) {
        String hql = String.format("From %s where upper(cast(birthday as string)) like :birthday ", getTableName());

        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Schoolchild> query = session.createQuery(hql, getType());
        query.setParameter("birthday", "%" + birthday + "%");

        List<Schoolchild> list = query.list();
        session.close();
        return list;
    }
   
}
