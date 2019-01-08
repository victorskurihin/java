package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.Table;
import ru.otus.homework.configs.YamlProperties;
import ru.otus.homework.services.BooksServiceImpl;
import ru.otus.homework.services.MessagesService;

@ShellComponent
public class BookCommands
{
    private MessagesService msg;

    private YamlProperties yp;

    private BooksServiceImpl booksService;

    public BookCommands(MessagesService msg, YamlProperties yp, BooksServiceImpl authorsService)
    {
        this.msg = msg;
        this.yp = yp;
        this.booksService = authorsService;
    }

    @ShellMethod(value = "Show books from table", group = "Show")
    public Table showAllBooks()
    {
        int numRows = booksService.createTableForFindAll();
        System.out.println(msg.get("number_of_rows", new Object[]{numRows}));

        return booksService.getTableBuilder().addFullBorder(BorderStyle.fancy_light).build();
    }

    @ShellMethod(value = "Show books with details from table", group = "Show")
    public Table showAllBooksWithDetails()
    {
        int numRows = booksService.createTableForFindAllWithDetail();
        System.out.println(msg.get("number_of_rows", new Object[]{numRows}));

        return booksService.getTableBuilder().addFullBorder(BorderStyle.fancy_light).build();
    }

    @ShellMethod(value = "Insert book to table", group = "Insert")
    public String insertBook(String isbn, String title, int editionNumber, String copyright, long publisherId)
    {
        String sid = Long.toString(booksService.insert(isbn, title, editionNumber, copyright, publisherId));

        return msg.get("inserted_with_id", new Object[]{sid});
    }

    @ShellMethod(value = "Update book in table", group = "Update")
    public String updateBook(long id, String isbn, String title, int editionNumber, String copyright, long publisherId)
    {
        String sid = Long.toString(booksService.update(id, isbn, title, editionNumber, copyright, publisherId));

        return msg.get("record_with_with_id_updated", new Object[]{sid});
    }

    @ShellMethod(value = "Delete book from table", group = "Delete")
    public void daleteBook(long id)
    {
        booksService.delete(id);
    }
}
