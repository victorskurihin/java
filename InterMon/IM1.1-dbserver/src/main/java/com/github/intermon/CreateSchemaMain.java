package com.github.intermon;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import com.github.intermon.db.DBServiceImpl;

import java.util.EnumSet;
import java.util.concurrent.Executors;

public class CreateSchemaMain {

    public static void main(String[] args) {
        try {
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder.configure("createSchema.hibernate.cfg.xml");

            Metadata metadata = DBServiceImpl.getMetadata(builder);

            new SchemaExport() //
                    .setOutputFile("db-schema.hibernate5.ddl") //
                    .create(EnumSet.of(TargetType.DATABASE, TargetType.SCRIPT), metadata);

            metadata.buildSessionFactory().close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
StandardServiceRegistryBuilder.configure xml
 */
//EOF
