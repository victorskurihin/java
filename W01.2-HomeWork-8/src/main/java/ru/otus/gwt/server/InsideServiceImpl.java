package ru.otus.gwt.server;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.models.EmpEntity;
import ru.otus.gwt.client.service.InsideService;
import ru.otus.gwt.shared.Emp;
import ru.otus.gwt.shared.Search;
import ru.otus.services.DbService;
import ru.otus.services.SearchCacheService;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.otus.gwt.shared.Constants.CACHE_SERVICE;
import static ru.otus.gwt.shared.Constants.DB_SERVICE;

public class InsideServiceImpl extends RemoteServiceServlet implements InsideService
{
    private static final Logger LOGGER = LogManager.getLogger(InsideServiceImpl.class.getName());

    private Emp convertEmpEntityToEmp(EmpEntity entity) {
        return new Emp(
            entity.getId(), entity.getFirstName(), entity.getSecondName(), entity.getSurName(),
            entity.getJob(), entity.getCity(), entity.getAge().toString()
        );
    }

    @Override
    public List<Emp> getEmpsList()
    {
        LOGGER.info("getEmpsList.");
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        List<EmpEntity> list = dbService.getEmpEntities();

        return list.stream().map(this::convertEmpEntityToEmp).collect(Collectors.toList());
    }

    private EmpEntity convertToEmpEntity(Emp emp)
    {
        EmpEntity entity = new EmpEntity();

        entity.setId(-1L);
        entity.setFirstName(emp.getFirstName());
        entity.setSecondName(emp.getSecondName());
        entity.setSurName(emp.getSurName());
        entity.setJob(emp.getJob());
        entity.setCity(emp.getCity());
        entity.setAge(Long.parseLong(emp.getAge()));

        return entity;
    }

    @Override
    public void addNewEmp(Emp emp)
    {
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        dbService.saveEntity(convertToEmpEntity(emp));
        LOGGER.info("Added new Emp: {}", emp);
    }

    @Override
    public void setEmpFirstName(long id, String value)
    {
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        dbService.updateFirstNameInEmpEntityById(id, value);
        LOGGER.info("Update Emp with id: {} firstName: {}.", id, value);
    }

    @Override
    public void setEmpSecondName(long id, String value)
    {
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        dbService.updateSecondNameInEmpEntityById(id, value);
        LOGGER.info("Update Emp with id: {} secondName: {}.", id, value);
    }

    @Override
    public void setEmpSurName(long id, String value)
    {
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        dbService.updateSecondNameInEmpEntityById(id, value);
        LOGGER.info("Update Emp with id: {} surName: {}.", id, value);
    }

    @Override
    public void deleteEmp(long id)
    {
        LOGGER.info("EmpEntity deliting by id: {} ...");
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        dbService.deleteEmpEntityById(id);
        LOGGER.info("EmpEntity by id: {} deleted.");
    }

    private List<Emp> searchEmpInDb(Search search)
    {
        Map<String, Object> attrs = new HashMap<>();

        if (null != search.getFio())
            attrs.put("name", "%" + search.getFio() + "%");

        if (null != search.getJob())
            attrs.put("job", "%" + search.getJob() + "%");

        if (null != search.getCity())
            attrs.put("city", "%" + search.getCity() + "%");

        if (null != search.getAge()) {
            LOGGER.info("age: {}", search.getAge());
            LOGGER.info("parse age: {}", Long.parseLong(search.getAge()));
            attrs.put("age", Long.parseLong(search.getAge()));
        }

        if ( ! attrs.isEmpty()) {
            DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
            List<EmpEntity> empEntities = dbService.searchEmpEntity(attrs);
            return empEntities.stream().map(this::convertEmpEntityToEmp).collect(Collectors.toList());
        }

        return getEmpsList();
    }

    @Override
    public List<Emp> searchEmp(Search search)
    {
        ServletContext sc = getServletContext();
        SearchCacheService cacheService = (SearchCacheService) sc.getAttribute(CACHE_SERVICE);

        List<Emp> result = cacheService.searchInCache(search);
        if (result != null) return result;

        LOGGER.info("Direct request to the database with hash: {}", search.hashCode());

        result = searchEmpInDb(search);
        cacheService.putToCache(search, result);

        return result;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
