<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.io.*,java.util.*" %>

<!DOCTYPE html>
<html lang=ru>
<head>
    <meta charset=UTF-8>
    <title id="title">Home Work 5 of JavaEE 2018-09 | a work showcasing the feature of GWT</title>
    <link rel="stylesheet" href="css/style-all.min.css"/>
    <script type="text/javascript" language="javascript" src="js/script-all.min.js"></script>
    <script type="text/javascript" language="javascript" src="login/login.nocache.js"></script>
</head>

<body class="body">
    <table class="tg body">
<%@ include file = "WEB-INF/header.jsp" %>
<%@ include file = "WEB-INF/inside.menu.jsp" %>
        <tr>
            <td class="tg-0lax my-left">
                <main class = "w3-col m12 w3-margin-0 my-left">
                    <div id="inside-container"></div>
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
