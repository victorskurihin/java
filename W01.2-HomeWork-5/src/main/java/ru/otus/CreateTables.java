package ru.otus;

/*
 * Created by VSkurikhin at autumn 2018.
 */

/**
 *
 * mvn clean compile dependency:copy-dependencies
 * createSchema.sh or createSchema.bat
 */

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import ru.otus.dataset.DeptEntity;
import ru.otus.dataset.EmpEntity;
import ru.otus.dataset.GroupEntity;
import ru.otus.dataset.UserEntity;

import java.util.EnumSet;

public class CreateTables {
    public static final String DB_SCHEMA_DDL = "db-schema.hibernate5.ddl";

    public static Metadata getMetadata(StandardServiceRegistryBuilder builder) {
        StandardServiceRegistry registry = builder.build();
        MetadataSources sources = new MetadataSources(registry);

        sources.addAnnotatedClass(DeptEntity.class);
        sources.addAnnotatedClass(EmpEntity.class);
        sources.addAnnotatedClass(UserEntity.class);
        sources.addAnnotatedClass(GroupEntity.class);

        return sources.buildMetadata();
    }

    public static void createTables() {
        try {
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder.configure("createSchema.hibernate.cfg.xml");

            Metadata metadata = getMetadata(builder);

            new SchemaExport() //
                .setOutputFile(DB_SCHEMA_DDL) //
                .create(EnumSet.of(TargetType.DATABASE, TargetType.SCRIPT), metadata);

            metadata.buildSessionFactory().close();
            System.out.println("Ok. You can view  DB Schema in file: " + DB_SCHEMA_DDL);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createTables();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
