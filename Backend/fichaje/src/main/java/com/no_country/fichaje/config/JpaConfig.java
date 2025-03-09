package com.no_country.fichaje.config;

import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
/*
@Configuration
@EnableJpaRepositories(basePackages = "com.no_country.fichaje.repository")
@EntityScan(basePackages = "com.no_country.fichaje.domain")
public class JpaConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.no_country.fichaje") // Asegúrate de que esta ruta es correcta
                .persistenceUnit("fichajePU")
                .build();
    }
    @Override
    public int hashCode() {
        return this.getClass().getName().hashCode();
    }
}
*/