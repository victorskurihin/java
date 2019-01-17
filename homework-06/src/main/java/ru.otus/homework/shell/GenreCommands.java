package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.Table;
import ru.otus.homework.services.GenresServiceImpl;
import ru.otus.homework.services.DataTableBulder;
import ru.otus.homework.services.MessagesService;

import java.util.List;

@ShellComponent
public class GenreCommands
{
    private MessagesService msg;

    private GenresServiceImpl genresService;

    public GenreCommands(MessagesService msg, GenresServiceImpl genresService)
    {
        this.msg = msg;
        this.genresService = genresService;
    }

    @ShellMethod(value = "Show genres from table", group = "Show")
    public Table showAllGenres()
    {
        List<String[]> dataList = genresService.findAll();
        System.out.println(msg.get("number_of_rows", new Object[]{dataList.size()}));

        return new DataTableBulder(dataList).getTableBuilder().build();
    }

    @ShellMethod(value = "Insert genre to table", group = "Insert")
    public String insertGenre(String genre)
    {
        String sid = Long.toString(genresService.insert(genre));

        return msg.get("inserted_with_id", new Object[]{sid});
    }

    @ShellMethod(value = "Update genre in table", group = "Update")
    public String updateGenre(long id, String genre)
    {
        String sid = Long.toString(genresService.update(id, genre));

        return msg.get("record_with_with_id_updated", new Object[]{sid});
    }

    @ShellMethod(value = "Delete genre from table", group = "Delete")
    public void deleteGenre(long id)
    {
        genresService.delete(id);
    }
}
