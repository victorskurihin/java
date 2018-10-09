package ru.otus.web;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.otus.dataset.EmpEntitiesList;
import ru.otus.dataset.EmpEntity;
import ru.otus.xml.DOMUtil;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/xmlmarshal", "/xmlmarshal/*"})
public class MarshalXMLServlet extends HttpServlet
{
    public static final String EMPTY_EMPLOYEES =
            "<?xml version='1.0' encoding='UTF-8' standalone='yes'?>\n" +
            "<employees>\n" +
            "</employees>";
    private static InputStream EMPTY_EMPLOYEES_STREAM = new ByteArrayInputStream(
            EMPTY_EMPLOYEES.getBytes(StandardCharsets.UTF_8)
    );
    private static final String PERSISTENCE_UNIT_NAME = "jpa";
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat
    private static final String DATA_FILE_LOCATION = "XMLDataFileLocation";
    private static final String SELECT_EMPL_ENTITY = "SELECT empl FROM EmpEntity empl";
    static final String GET = "get";
    static final String OK = "ok";
    static final String SAVE = "save";
    static final String DOMFILTER = "domfilter";

    private EmpEntitiesList getEmpEntitiesList(EntityManager em, EntityTransaction transaction)
    {
        transaction.begin();
        Query q = em.createQuery(SELECT_EMPL_ENTITY);
        //noinspection unchecked
        ArrayList<EmpEntity> list = new ArrayList<EmpEntity>(q.getResultList());
        transaction.commit();

        return new EmpEntitiesList(list);
    }

    private void marshalEmpEntitiesList(PrintWriter out, EmpEntitiesList result, boolean save)
    {
        try {
            JAXBContext context = JAXBContext.newInstance(result.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            if (save) {
                String path = getServletContext().getInitParameter(DATA_FILE_LOCATION);
                File file = Paths.get(new URI(path)).toFile();
                m.marshal(result, file);
            }
            m.marshal(result, out);

        } catch (JAXBException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Document getDataFileDocument()
    throws URISyntaxException, ParserConfigurationException, IOException, SAXException
    {
        String path = getServletContext().getInitParameter(DATA_FILE_LOCATION);
        return DOMUtil.getDocument(new URI(path).toString());
    }

    private Transformer getTransformer() throws TransformerConfigurationException
    {
        return TransformerFactory.newInstance().newTransformer();
    }

    private XPathExpression getXPathExpression(String expression) throws XPathExpressionException
    {
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();

        return xpath.compile(expression);
    }

    private NodeList getNodeList(Document document, XPathExpression expr)
    throws XPathExpressionException
    {
        return (NodeList) expr.evaluate(document, XPathConstants.NODESET);
    }

    private void filterByAverageSalary(PrintWriter out)
    throws URISyntaxException, IOException, SAXException, ParserConfigurationException,
           XPathExpressionException, TransformerException
    {
        String expression = "/employees/employee[@salary > " +
               "(sum(/employees/employee/@salary) div count(/employees/employee/@salary))]";
        Document document = getDataFileDocument();
        // Use a Transformer for output
        Transformer transformer = getTransformer();
        XPathExpression expr = getXPathExpression(expression);
        NodeList nodes = getNodeList(document, expr);
        Document resultXML = DOMUtil.getDocument(EMPTY_EMPLOYEES_STREAM);
        Element employees = resultXML.createElement("employees");

        for (int i = 0; i < nodes.getLength(); ++i) {
            Node copyNode = nodes.item(i).cloneNode(true);
            resultXML.adoptNode(copyNode);
            employees.appendChild(copyNode);
        }

        DOMSource source = new DOMSource(employees);
        StreamResult resultOut = new StreamResult(out);
        transformer.transform(source, resultOut);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();

        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();

        try {
            String command = ServletUtil.retrieveCommand(request);
            if (command == null) {
                command = GET;
            }
            if (command.equals(OK)) {
                ServletUtil.okXML(out);
            } else {
                EmpEntitiesList result = getEmpEntitiesList(em, transaction);
                if (command.equals(DOMFILTER)) {
                    filterByAverageSalary(out);
                } else if (command.equals(SAVE)) {
                    marshalEmpEntitiesList(out, result, true);
                } else {
                    marshalEmpEntitiesList(out, result, false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
