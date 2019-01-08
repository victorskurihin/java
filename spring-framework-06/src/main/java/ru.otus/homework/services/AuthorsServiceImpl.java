package ru.otus.homework.services;

import org.springframework.shell.table.*;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.services.dao.JdbcAuthorDao;

@Service
public class AuthorsServiceImpl extends TableBuilderService<Author, JdbcAuthorDao> implements AuthorsService
{
    private JdbcAuthorDao authorDao;

    private TableBuilder tableBuilder;

    public AuthorsServiceImpl(JdbcAuthorDao authorDao)
    {
        this.authorDao = authorDao;
    }

    @Override
    public TableBuilder getTableBuilder()
    {
        return tableBuilder;
    }

    private static String[] unfoldAuthor(Author author)
    {
        return new String[]{Long.toString(author.getId()), author.getFirstName(), author.getLastName()};
    }

    @Override
    public int createTableForFindAll()
    {
        tableBuilder = super.createTableForFindAll(
            authorDao, JdbcAuthorDao.FIND_ALL_HEADER, AuthorsServiceImpl::unfoldAuthor
        );

        return tableBuilder.getModel().getRowCount() - 1;
    }

    @Override
    public long insert(String firstName, String lastName)
    {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorDao.insert(author);

        return author.getId();
    }

    @Override
    public long update(long id, String firstName, String lastName)
    {
        Author author = new Author();
        author.setId(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorDao.update(author);

        return author.getId();
    }

    @Override
    public void delete(long id)
    {
        authorDao.delete(id);
    }
}
