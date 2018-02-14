package ru.otus.l101;

import ru.otus.l101.dao.UsersDataSetMyDAO;
import ru.otus.l101.dataset.ComplexDataSet;
import ru.otus.l101.dataset.UsersDataSet;
import ru.otus.l101.db.*;

/**
 * Created by VSkurikhin.
 *
 * Solution for L09.1
 *
 * PreReq: PostgreSQL
 *
 * Configure application in db/DBConf.java
 *
 * To start:
 * mvn clean package
 *
 * ./run.sh
 * or
 * run.bat
 */

public class Main {

    public static void main(String[] args) throws Exception {
        try (DBService dbService = new DBServiceMyImpl(UsersDataSetMyDAO.class)) {
            DBConf dbConf = new DBConf(dbService.getConnection());
            dbConf.dropTables(Loader.classGetNameToTableName(UsersDataSet.class));
            dbConf.dropTables(Loader.classGetNameToTableName(ComplexDataSet.class));
            dbService.createTables(ComplexDataSet.class);
            ComplexDataSet complexDataSet = new ComplexDataSet(1);
            complexDataSet.setF0(false);
            complexDataSet.setF1((byte) 10);
            complexDataSet.setF2('a');
            complexDataSet.setF3((short) 30);
            complexDataSet.setF4(40);
            complexDataSet.setF5(50);
            dbService.save(complexDataSet);
            ComplexDataSet restoredComplexDataSet = dbService.load(1, ComplexDataSet.class);
            System.out.println("restoredComplexDataSet = " + restoredComplexDataSet);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
