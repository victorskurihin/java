package ru.otus.homework.services;

import org.springframework.shell.table.*;

import java.util.List;

public class DataTableBulder
{
    private TableBuilder tableBuilder;

    private List<String[]> dataList;

    private BorderStyle style = BorderStyle.fancy_light;

    public DataTableBulder(List<String[]> dataList)
    {
        this.dataList = dataList;
    }

    public DataTableBulder(List<String[]> dataList, BorderStyle style)
    {
        this(dataList);
        this.style = style;
    }

    private TableBuilder create(String[][] data)
    {
        TableModel model = new ArrayTableModel(data);
        tableBuilder = new TableBuilder(model);

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

    public TableBuilder getTableBuilder()
    {
        String[][] data = new String[dataList.size()][];
        data = dataList.toArray(data);

        return create(data).addFullBorder(style);
    }

    private CellMatcher at(final int theRow, final int col) {
        return (row, column, model) -> row == theRow && column == col;
    }
}
