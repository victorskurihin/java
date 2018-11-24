package ru.otus.db.dao.jdbc;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.db.Executor;
import ru.otus.db.ResultHandler;
import ru.otus.exeptions.ExceptionSQL;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DeptEntity;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class DeptController extends AbstractController <DeptEntity, Long>
{
    public static final String SELECT_ALL = "SELECT * FROM dep_directory";
    public static final String SELECT_BY_ID = "SELECT * FROM dep_directory WHERE id = ?";
    public static final String INSERT = "INSERT INTO dep_directory (id, pid, title) VALUES (?, ?, ?)";
    public static final String UPDATE = "UPDATE dep_directory SET pid = ?, title = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM dep_directory WHERE id = ?";

    DeptController(DataSource dataSource)
    {
        super(dataSource);
    }

    private DeptEntity setDeptEntity(DeptEntity deptEntity, ResultSet resultSet) throws ExceptionSQL
    {
        try {
            deptEntity.setId(resultSet.getLong("id"));
            deptEntity.setParentId(resultSet.getLong("pid"));
            deptEntity.setTitle(resultSet.getString("title"));
        } catch (SQLException e) {
            throw new ExceptionSQL(e);
        }

        return deptEntity;
    }

    private DeptEntity getDeptEntity(ResultSet resultSet) throws ExceptionSQL
    {
        DeptEntity deptEntity = new DeptEntity();
        setDeptEntity(deptEntity, resultSet);

        return deptEntity;
    }

    @Override
    public List<DeptEntity> getAll() throws ExceptionThrowable
    {
        try {
            return getArrayListAll(SELECT_ALL, this::getDeptEntity);
        } catch (SQLException e) {
            throw new ExceptionThrowable(e);
        }
    }

    @Override
    public DeptEntity getEntityById(Long id) throws ExceptionThrowable
    {
        AtomicBoolean exists = new AtomicBoolean(false);
        final DeptEntity result = new DeptEntity();

        ResultHandler handler = resultSet -> {
            if (resultSet.next()) {
                DeptController.this.setDeptEntity(result, resultSet);
                exists.set(true);
            }
        };

        execQueryEntityById(SELECT_BY_ID, handler, getConsumerLongId(id));

        return exists.get() ? result : null;
    }

    public Consumer<PreparedStatement> getConsumerInsertDeptEntity(DeptEntity entity)
    {
        return preparedStatement -> {
            try {
                preparedStatement.setLong(1, entity.getId());
                preparedStatement.setLong(2, entity.getParentId());
                preparedStatement.setString(3, entity.getTitle());
            } catch (SQLException e) {
                throw new ExceptionSQL(e);
            }
        };
    }

    public Consumer<PreparedStatement> getConsumerUpdateDeptEntity(DeptEntity entity)
    {
        return preparedStatement -> {
            try {
                preparedStatement.setLong(1, entity.getParentId());
                preparedStatement.setString(2, entity.getTitle());
                preparedStatement.setLong(3, entity.getId());
            } catch (SQLException e) {
                throw new ExceptionSQL(e);
            }
        };
    }

    @Override
    public DeptEntity update(DeptEntity entity) throws ExceptionThrowable
    {
        try {
            Executor executor = new Executor(getDataSource().getConnection());
            int count = executor.execUpdate(UPDATE, getConsumerUpdateDeptEntity(entity));

            return count > 0 ? entity : null;
        } catch (SQLException e) {
            throw new ExceptionThrowable(e);
        }
    }

    @Override
    public boolean delete(Long id) throws ExceptionThrowable
    {
        try {
            Executor executor = new Executor(getDataSource().getConnection());
            int count = executor.execUpdate(DELETE, getConsumerLongId(id));

            return count > 0;
        } catch (SQLException e) {
            throw new ExceptionThrowable(e);
        }
    }

    @Override
    public boolean create(DeptEntity entity) throws ExceptionThrowable
    {
        try {
            Executor executor = new Executor(getDataSource().getConnection());
            int count = executor.execUpdate(INSERT, getConsumerInsertDeptEntity(entity));

            return count > 0;
        } catch (SQLException e) {
            throw new ExceptionThrowable(e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
