package ru.otus.web;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.db.dao.DeptDAO;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class EJBClientTest
{
    private static InitialContext ctxByPropertyFile;
    private static InitialContext defaultCtx;

    // @Before
    public void setUp() throws Exception
    {
        Properties props = new Properties();
        try {
            props.load(EJBClientTest.class.getResourceAsStream("/jndi.properties"));
            ctxByPropertyFile = new InitialContext(props);
            defaultCtx = new InitialContext();
        } catch (IOException | NamingException ex) {
            ex.printStackTrace();
        }
    }

    // @Test
    public void testGetTypeParameterClass() throws NamingException
    {
        // glassfish specific test case (non-portable JNDI name)
        DeptDAO dao = (DeptDAO) ctxByPropertyFile.lookup("ejb/DeptDAO");
        System.out.println("dao.getClass().getName() = " + dao.getClass().getName());
        assertEquals("ru.otus.db.dao", dao.getClass().getName().substring(0,14));
    }

    // @Test
    public void testSimpleBeanGlobally() throws Exception {
        DeptDAO dao = (DeptDAO) defaultCtx.lookup("java:global/HomeWork11Core/HomeWorkPersistent-11.1-7/DeptDAO!ru.otus.db.dao.DeptDAO");
        System.out.println("dao.getClass().getName() = " + dao.getClass().getName());
        assertEquals("ru.otus.db.dao", dao.getClass().getName().substring(0,14));
    }
}
