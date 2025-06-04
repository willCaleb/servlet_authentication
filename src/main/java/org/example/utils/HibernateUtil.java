package org.example.utils;

import org.example.model.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.reflections.Reflections;

import jakarta.persistence.Entity;
import java.util.Set;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();

            MetadataSources metadataSources = new MetadataSources(registry);

            Reflections reflections = new Reflections(User.class.getPackage().getName());

            Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);

            entityClasses.forEach(metadataSources::addAnnotatedClass);

            return metadataSources.buildMetadata().buildSessionFactory();

        } catch (Exception ex) {
            throw new ExceptionInInitializerError("Falha ao inicializar o Hibernate: " + ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}