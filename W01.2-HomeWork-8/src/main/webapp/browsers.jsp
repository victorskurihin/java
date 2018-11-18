<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.io.*,java.util.*" %>
<%@ taglib prefix="tags" uri="/custom_tag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}/" />

<!DOCTYPE html>
<html lang=ru>
<head>
    <meta charset=UTF-8>
    <title id="title">Home Work 7 of JavaEE 2018-09 | a work showcasing the feature of Freemarker</title>
    <link rel="stylesheet" href="<c:out value="${baseURL}"/>css/style-all.min.css"/>
    <script type="text/javascript" language="javascript" src="<c:out value="${baseURL}"/>js/script-all.min.js"></script>
<tags:sender_stat pageName="browsers" />
    <script type="text/javascript" language="javascript" src="<c:out value="${baseURL}"/>welcome/welcome.nocache.js"></script>
</head>

<body class="body">
    <table class="tg body">
<%@ include file = "WEB-INF/header.jsp" %>
<%@ include file = "WEB-INF/welcome.menu.jsp" %>
        <tr>
            <td class="tg-0lax my-left">
                <main id="main" class="w3-col m12 w3-margin my-left">
<%@ include file = "WEB-INF/browsers.main.jsp" %>
                </main>
            </td>
            <td class="tg-0lax my-right" style="border-left: 2px dotted black;" colspan="2">
<%@ include file = "WEB-INF/aside.jsp" %>
            </td>
        </tr>
<%@ include file = "WEB-INF/footer.jsp" %>
    </table>
</body>
</html>
