package com.meow.dao.impl;

import com.meow.config.HibernateConfig;
import com.meow.dao.PlayerDao;
import com.meow.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

public class PlayerDaoImpl implements PlayerDao {

    private final SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

    @Override
    public Optional<Player> findByName(String name) {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("FROM Player WHERE name = :name", Player.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
        }
    }

    @Override
    public Player save(Player player) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            session.persist(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }

        return player;
    }
}