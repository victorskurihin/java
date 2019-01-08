package ru.otus.homework.services;

import org.springframework.shell.table.*;
import ru.otus.homework.models.DataSet;
import ru.otus.homework.services.dao.Dao;

import java.util.List;
import java.util.function.Function;

public abstract class TableBuilderService<T extends DataSet, D extends Dao<T>>
{
    public TableBuilder createTableForFindAll(D dao, String[] header, Function<T, String[]> f)
    {
        List<T> dataList = dao.findAll();
        int findAllNumRows =  dataList.size() + 1;
        int numColumns = header.length;

        String[][] data = new String[findAllNumRows][numColumns];
        data[0] = header;

        TableModel model = new ArrayTableModel(data);
        TableBuilder tableBuilder = new TableBuilder(model);

        for (int j = 0; j < numColumns; j++) {
            tableBuilder.on(at(0, j)).addAligner(SimpleHorizontalAligner.center);
        }

        for (int i = 1; i < findAllNumRows; i++) {
            T entry = dataList.get(i - 1);

            data[i] = f.apply(entry);

            for (int j = 0; j < numColumns; j++) {
                tableBuilder.on(at(i, j)).addAligner(SimpleHorizontalAligner.left);
                tableBuilder.on(at(i, j)).addAligner(SimpleVerticalAligner.middle);
            }
            tableBuilder.on(at(i, 0)).addAligner(SimpleHorizontalAligner.right);
        }

        return tableBuilder;
    }

    public static TableBuilder createTableBuilder(String[][] data)
    {
        TableModel model = new ArrayTableModel(data);
        TableBuilder tableBuilder = new TableBuilder(model);

        for (int j = 0; j < data.length; j++) {
            tableBuilder.on(at(0, j)).addAligner(SimpleHorizontalAligner.center);
        }

        for (int i = 1; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                tableBuilder.on(at(i, j)).addAligner(SimpleHorizontalAligner.left);
                tableBuilder.on(at(i, j)).addAligner(SimpleVerticalAligner.middle);
            }
            tableBuilder.on(at(i, 0)).addAligner(SimpleHorizontalAligner.right);
        }

        return tableBuilder;
    }

    public static CellMatcher at(final int theRow, final int col) {
        return (row, column, model) -> row == theRow && column == col;
    }
}
