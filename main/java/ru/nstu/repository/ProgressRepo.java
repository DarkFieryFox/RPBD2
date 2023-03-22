package ru.nstu.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.nstu.entity.Progress;
import ru.nstu.entity.Progress;
import ru.nstu.util.HibernateUtils;

import java.util.List;

public class ProgressRepo extends BaseRepo<Progress, Long>{

    @Override
    protected Class<Progress> getType() {
        return Progress.class;
    }

    @Override
    protected String getTableName() {
        return "Progress";
    }
    public List<Progress> findAllByClasss(String classs) {
        String hql = String.format("From %s where classs like :classs ", getTableName());

        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Progress> query = session.createQuery(hql, getType());
        query.setParameter("classs", "%" + classs + "%");

        List<Progress> list = query.list();
        session.close();
        return list;
    }
}
