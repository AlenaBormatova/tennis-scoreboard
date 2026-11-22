package com.meow.config;

import org.flywaydb.core.Flyway;

public class FlywayConfig {

    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource(
                        "jdbc:postgresql://localhost:5432/tennis",
                        "postgres",
                        ""
                )
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();
    }
}