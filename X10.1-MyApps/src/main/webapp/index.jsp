<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
         "http://www.w3.org/TR/html4/loose.dtd">

<%@page language="java" contentType="text/html; charset=UTF-8"
                                          pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<!-- jsp:useBean id="users" class="example.Users" scope="page" / -->

<%
   List<String> list = new ArrayList<String>();
   list.add("Продукты");
   list.add("Одежда");
   list.add("Обувь");
   list.add("Спортивный инвентарь");
   request.setAttribute("list", list);
%>

<html>
  <head>
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <title>Пример JSP JSTL</title>
  </head>
  <body>
     <h3>Список групп товаров</h3>
     <ul>
        <c:forEach items="${list}" var="i" >
          <li><c:out value="${i}" /></li><br />
        </c:forEach>
     </ul>
     <!-- h3>Герои "12-и стульев"</h3>
     <table border="1" cellspacing="0" cellpadding="2">
          <tr>
               <td>UID/td>
               <td>Name</td>
          </tr>

          <c:forEach items="${users.data}" var="user">
          <tr>
               <td>${user.id}</td>
               <td>${user.name}</td>
          </tr>
          </c:forEach>
     </table -->
  </body>
</html>