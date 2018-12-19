<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Список пользователей</title>
</head>
<body>

    <ol>
        <c:forEach items="${depts}" var="dept">
            <li>
                ${dept.id} ${dept.pid} - ${dept.title}
            </li>
        </c:forEach>
    </ol>

</body>
</html>