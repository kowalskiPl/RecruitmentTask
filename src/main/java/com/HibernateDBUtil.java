package com;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateDBUtil {
    public static void updateOrInsert(QueryCriteria queryCriteria, int reportId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from ReportEntity RE where RE.reportId = :repId");
        query.setParameter("repId", 1);
        if (query.list().size() != 0) {
            Query updateQuery = session.createQuery("update ReportEntity RE set RE.queryCriteriaCharacterPhrase = :characterPhrase," +
                    " RE.queryCriteriaPlanetName = :planetName where RE.reportId = :id");
            updateQuery.setParameter("characterPhrase", queryCriteria.getQuery_criteria_character_phrase());
            updateQuery.setParameter("planetName", queryCriteria.getQuery_criteria_planet_name());
            updateQuery.setParameter("id", reportId);
            updateQuery.executeUpdate();
        } else {
            ReportEntity entity = SWAPIUtility
                    .searchAndGenerateReport(queryCriteria.getQuery_criteria_character_phrase(), queryCriteria.getQuery_criteria_planet_name(), reportId);
            session.save(entity);
        }
        session.getTransaction().commit();
        session.close();
    }

    public static ReportEntity getReport(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from ReportEntity RE where RE.reportId = :reportId").setParameter("reportId", id);
        ReportEntity re = (ReportEntity) query.list().get(0);
        return re;
    }

    public static List getReports(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from ReportEntity");
        List results = query.getResultList();
        session.close();
        return results;
    }

    public static void deleteReports(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from ReportEntity");
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public static void deleteReport(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from ReportEntity RE where RE.reportId = :id").setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
