package com.meow.dao.impl;

import com.meow.config.HibernateConfig;
import com.meow.dao.MatchDao;
import com.meow.entity.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class MatchDaoImpl implements MatchDao {

    private final SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

    @Override
    public Match save(Match match) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            session.persist(match);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;

        }
        return match;
    }

    @Override
    public List<Match> findAll(int page, int pageSize) {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("SELECT m FROM Match m ORDER BY m.id", Match.class)
                    .setFirstResult(page * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
    }

    @Override
    public List<Match> findByPlayerName(String name, int page, int pageSize) {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery(
                            "FROM Match m " +
                                    "WHERE m.firstPlayer.name = :name OR m.secondPlayer.name = :name " +
                                    "ORDER BY m.id",
                            Match.class
                    )
                    .setParameter("name", name)
                    .setFirstResult(page * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
    }

    @Override
    public Long count() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT COUNT(m) FROM Match m", Long.class)
                    .getSingleResult();
        }
    }

    @Override
    public Long countByPlayerName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT COUNT(m) " +
                                    "FROM Match m " +
                                    "WHERE m.firstPlayer.name = :name OR m.secondPlayer.name = :name ",
                            Long.class
                    )
                    .setParameter("name", name)
                    .getSingleResult();
        }
    }
}