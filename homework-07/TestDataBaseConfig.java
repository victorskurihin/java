package ru.otus.homework.configs;

import org.hibernate.metamodel.internal.MetamodelImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.otus.homework.repository")
public class TestDataBaseConfig
{
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return mock(EntityManagerFactory.class);
    }

    @Bean
    public EntityManager entityManager() {
        EntityManager entityManagerMock = mock(EntityManager.class);
        when(entityManagerMock.getMetamodel()).thenReturn(mock(MetamodelImpl.class));
        return entityManagerMock;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return mock(JpaTransactionManager.class);
    }
}
