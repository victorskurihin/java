package ru.otus.db;

import org.junit.*;
import org.mockito.Mockito;
import ru.otus.models.DeptEntitiesList;
import ru.otus.models.DeptEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ImporterSmallXMLTest
{
    public static final String PERSISTENCE_UNIT_NAME = "test-jpa";
    private final String filenameDeptEntities = "src/test/resources/DeptEntities.xml";

    private static EntityManagerFactory emf;
    private EntityManager entityManager;

    @BeforeClass
    public static void setUpClass()
    {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Before
    public void setUp() throws Exception
    {
        entityManager = emf.createEntityManager();
    }

    @After
    public void tearDown()
    {
        entityManager.close();
    }

    private ServletContext getServletContext(String filename) throws IOException
    {
        ServletContext ctxEntities;
        File tmp = new File(filename);
        URI uri = tmp.toURI();
        URL url = uri.toURL();
        ctxEntities = mock(ServletContext.class);
        Mockito.doReturn(url.openStream())
               .when(ctxEntities)
               .getResourceAsStream(filename);
        return ctxEntities;
    }

    @Test
    public void testImportDeptEntities() throws Exception
    {
        ServletContext sc = getServletContext(filenameDeptEntities);
        ImporterSmallXML<DeptEntitiesList> importEntities = new ImporterSmallXML<>(
            sc, filenameDeptEntities, DeptEntity.class, DeptEntitiesList.class
        );
        importEntities.saveEntities(entityManager);

        DeptEntity expected1 = new DeptEntity();
        expected1.setId(1L);
        expected1.setParentId(0L);
        expected1.setTitle("title1");
        DeptEntity dep =  entityManager.find(DeptEntity.class, 1L);
        assertNotNull(dep);
        Assert.assertEquals(expected1, dep);

        DeptEntity expected2 = new DeptEntity();
        expected2.setId(2L);
        expected2.setParentId(1L);
        expected2.setTitle("title2");
        dep =  entityManager.find(DeptEntity.class, 2L);
        assertNotNull(dep);
        Assert.assertEquals(expected2, dep);

        DeptEntity expected3 = new DeptEntity();
        expected3.setId(3L);
        expected3.setParentId(1L);
        expected3.setTitle("title3");
        dep =  entityManager.find(DeptEntity.class, 3L);
        assertNotNull(dep);
        Assert.assertEquals(expected3, dep);
    }
}