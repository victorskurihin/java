package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.Table;
import ru.otus.homework.services.DataTableBulder;
import ru.otus.homework.services.ReviewsServiceImpl;
import ru.otus.homework.services.MessagesService;

import java.util.List;

@ShellComponent
public class ReviewCommands
{
    private MessagesService msg;

    private ReviewsServiceImpl reviewsService;

    public ReviewCommands(MessagesService msg, ReviewsServiceImpl reviewsService)
    {
        this.msg = msg;
        this.reviewsService = reviewsService;
    }

    @ShellMethod(value = "Show reviews from table", group = "Show")
    public Table showAllReviews()
    {
        List<String[]> dataList = reviewsService.findAll();
        System.out.println(msg.get("number_of_rows", new Object[]{dataList.size()}));

        return new DataTableBulder(dataList).getTableBuilder().build();
    }

    @ShellMethod(value = "Insert review to table", group = "Insert")
    public String insertReview(String review)
    {
        String sid = Long.toString(reviewsService.insert(review));

        return msg.get("inserted_with_id", new Object[]{sid});
    }

    @ShellMethod(value = "Update review in table", group = "Update")
    public String updateReview(long id, String review)
    {
        String sid = Long.toString(reviewsService.update(id, review));

        return msg.get("record_with_with_id_updated", new Object[]{sid});
    }

    @ShellMethod(value = "Delete review from table", group = "Delete")
    public void deleteReview(long id)
    {
        reviewsService.delete(id);
    }
}
