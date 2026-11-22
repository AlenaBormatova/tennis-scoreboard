package com.meow.config;

import org.hibernate.Session;

public class HibernateTest {

    public static void main(String[] args) {

        try (Session session = HibernateConfig
                .getSessionFactory()
                .openSession()) {

            System.out.println("Hibernate работает!");

        } finally {
            HibernateConfig.shutdown();
        }
    }
}