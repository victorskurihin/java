package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.*;
import ru.otus.homework.configs.YamlProperties;
import ru.otus.homework.services.AuthorsServiceImpl;
import ru.otus.homework.services.MessagesService;

@ShellComponent
public class AuthorCommands
{
    private MessagesService msg;

    private YamlProperties yp;

    private AuthorsServiceImpl authorsService;

    public AuthorCommands(MessagesService msg, YamlProperties yp, AuthorsServiceImpl authorsService)
    {
        this.msg = msg;
        this.yp = yp;
        this.authorsService = authorsService;
    }

    @ShellMethod(value = "Show Authors from table", group = "Show")
    public Table showAllAuthors()
    {
        int numRows = authorsService.createTableForFindAll();
        System.out.println(msg.get("number_of_rows", new Object[]{numRows}));

        return authorsService.getTableBuilder().addFullBorder(BorderStyle.fancy_light).build();
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
