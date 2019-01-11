package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.*;
import ru.otus.homework.services.AuthorsServiceImpl;
import ru.otus.homework.services.MessagesService;

import java.util.List;

@ShellComponent
public class AuthorCommands
{
    private MessagesService msg;

    private AuthorsServiceImpl authorsService;

    public AuthorCommands(MessagesService msg, AuthorsServiceImpl authorsService)
    {
        this.msg = msg;
        this.authorsService = authorsService;
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

    @ShellMethod(value = "Show Authors from table", group = "Show")
    public Table showAllAuthors()
    {
        List<String[]> dataList = authorsService.findAll();
        System.out.println(msg.get("number_of_rows", new Object[]{dataList.size()}));

        String[][] data = new String[dataList.size()][];
        data = dataList.toArray(data);

        return createTableBuilder(data).addFullBorder(BorderStyle.fancy_light).build();
    }

    @ShellMethod(value = "Insert Author to table", group = "Insert")
    public String insertAuthor(String firstName, String lastName)
    {
        String sid = Long.toString(authorsService.insert(firstName, lastName));

        return msg.get("inserted_with_id", new Object[]{sid});
    }

    @ShellMethod(value = "Update Author in table", group = "Update")
    public String updateAuthor(long id, String firstName, String lastName)
    {
        String sid = Long.toString(authorsService.update(id, firstName, lastName));

        return msg.get("record_with_with_id_updated", new Object[]{sid});
    }

    @ShellMethod(value = "Delete Author from table", group = "Delete")
    public void daleteAuthor(long id)
    {
        authorsService.delete(id);
    }
}
