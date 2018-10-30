package ru.otus.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.dataset.EmpEntity;
import ru.otus.gwt.client.service.InsideService;
import ru.otus.gwt.shared.Emp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InsideServiceImpl extends RemoteServiceServlet implements InsideService
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    // private static final EntityManagerFactory emf =
    //        Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat
    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf; // for Glassfish
    private static final String SELECT_EMPL_ENTITY = "SELECT empl FROM EmpEntity empl";

    private static final Logger LOGGER = LogManager.getLogger(InsideServiceImpl.class.getName());

    private Emp convertEmpEntityToEmp(EmpEntity entity) {
        return new Emp(entity.getFirstName(), entity.getSecondName(), entity.getSurName());
    }

    @Override
    public List<Emp> getEmpsList()
    {
        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery(SELECT_EMPL_ENTITY);
        //noinspection unchecked
        ArrayList<EmpEntity> list = new ArrayList<>(q.getResultList());

        LOGGER.info("ArrayList<EmpEntity> list: {}", list);

        return list.stream().map(this::convertEmpEntityToEmp).collect(Collectors.toList());
    }
    // LOGGER.info("name: {}, password: {}", user.getLogin(), user.getPassword());
}