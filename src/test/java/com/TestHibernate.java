package com;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TestHibernate {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        //add new ReportEntity
        ReportEntity entity = new ReportEntity();
        entity.setReportId(1);
        entity.setFilmId(2);
        entity.setQueryCriteriaPlanetName("Earth");
        entity.setQueryCriteriaCharacterPhrase("Hans");
        entity.setPlanetId(1);
        entity.setCharacterId(3);
        session.save(entity);
        session.getTransaction().commit();
        Query query = session.createQuery("from ReportEntity");
        List results = query.getResultList();
        //printed as error to make it more visible
        System.err.println(results.toString());
        HibernateUtil.shutdown();
    }
}
