package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.Table;
import ru.otus.homework.configs.YamlProperties;
import ru.otus.homework.services.MessagesService;
import ru.otus.homework.services.PublishersServiceImpl;

@ShellComponent
public class PublisherCommands
{
    private MessagesService msg;

    private YamlProperties yp;

    private PublishersServiceImpl publishersService;

    public PublisherCommands(MessagesService msg, YamlProperties yp, PublishersServiceImpl publishersService)
    {
        this.msg = msg;
        this.yp = yp;
        this.publishersService = publishersService;
    }

    @ShellMethod(value = "Show Publishers from table", group = "Show")
    public Table showAllPublishers()
    {
        int numRows = publishersService.createTableForFindAll();
        System.out.println(msg.get("number_of_rows", new Object[]{numRows}));

        return publishersService.getTableBuilder().addFullBorder(BorderStyle.fancy_light).build();
    }

    @ShellMethod(value = "Show books with details from table", group = "Show")
    public Table showAllPublisherBooksWithDetails()
    {
        int numRows = publishersService.createTableAllBookWithDetail();
        System.out.println(msg.get("number_of_rows", new Object[]{numRows}));

        return publishersService.getTableBuilder().addFullBorder(BorderStyle.fancy_light).build();
    }

    @ShellMethod(value = "Insert Publisher to table", group = "Insert")
    public String insertPublisher(String publisherName)
    {
        String sid = Long.toString(publishersService.insert(publisherName));

        return msg.get("inserted_with_id", new Object[]{sid});
    }

    @ShellMethod(value = "Update Publisher in table", group = "Update")
    public String updatePublisher(long id, String publisherName)
    {
        String sid = Long.toString(publishersService.update(id, publisherName));

        return msg.get("record_with_with_id_updated", new Object[]{sid});
    }

    @ShellMethod(value = "Delete Publisher from table", group = "Delete")
    public void daletePublisher(long id)
    {
        publishersService.delete(id);
    }
}
