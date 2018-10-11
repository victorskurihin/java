package ru.otus.json.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import ru.otus.xml.model.DeptEntity;
import ru.otus.xml.model.EmpEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/gson")
public class GSONServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(GSONServlet.class.getName());

    private static final Gson JSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DeptEntity department = new DeptEntity();
        department.setDeptno(100L);
        department.setDname("Accountant");
        department.setLoc("Moscow");

        EmpEntity employee = new EmpEntity();
        employee.setEmpno(1L);
        employee.setEname("Ivanov Vitalii Andreevich");
        employee.setMgr(1L);
        employee.setSal(5000L);
        employee.setComm(200L);
        employee.setHiredate(new Date());
        employee.setJob("BOSS");
        employee.setDeptNo(department);

        response.setHeader("Content-type", "application/json");
        try (PrintWriter pw = response.getWriter()) {
            String json = JSON.toJson(employee);
            EmpEntity emp = JSON.fromJson(json, EmpEntity.class);
            logger.info("JSON string converted to object : " + emp);
            pw.println(json);
            System.out.println(json);
        }

    }
}
