package ru.otus.l131.servlet;

import ru.otus.l131.dataset.*;
import ru.otus.l131.db.*;

public class Workload {
    private DBService dbService;

    public Workload(DBService dbService) {
        this.dbService = dbService;
    }

    public void run() {
        final int size = 20;
        int j = 0;

        for (int i = 0; i < size; i++) {
            UserDataSet expectedUserDataSet = new UserDataSet(
                i,"User " + i, new AddressDataSet("Street " + i)
            );
            expectedUserDataSet.addPhone(
                new PhoneDataSet("100" + String.format("%04d", j++))
            );
            expectedUserDataSet.addPhone(
                new PhoneDataSet("100" + String.format("%04d", j++))
            );
            dbService.save(expectedUserDataSet);
        }

        for (int i = (size - (DBServiceImpl.cacheSize + 5)); i < size; i++) {
            UserDataSet testUserDataSet = dbService.load(i, UserDataSet.class);
        }
    }
}
