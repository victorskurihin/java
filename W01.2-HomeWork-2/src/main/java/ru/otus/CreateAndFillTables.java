package ru.otus;

/**
 * mvn clean compile dependency:copy-dependencies -DoutputDirectory=${project.build.directory}/lib
 */

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import java.util.EnumSet;

public class CreateAndFillTables {

    public static Metadata getMetadata(StandardServiceRegistryBuilder builder) {
        StandardServiceRegistry registry = builder.build();
        MetadataSources sources = new MetadataSources(registry);

        sources.addAnnotatedClass(DirectoryEntity.class);
        sources.addAnnotatedClass(RegistryEntity.class);
        sources.addAnnotatedClass(UserEntity.class);

        return sources.buildMetadata();
    }

    public static void createTables() {
        try {
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder.configure("createSchema.hibernate.cfg.xml");

            Metadata metadata = getMetadata(builder);

            new SchemaExport() //
                .setOutputFile("db-schema.hibernate5.ddl") //
                .create(EnumSet.of(TargetType.DATABASE, TargetType.SCRIPT), metadata);

            metadata.buildSessionFactory().close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createTables();
    }
}
