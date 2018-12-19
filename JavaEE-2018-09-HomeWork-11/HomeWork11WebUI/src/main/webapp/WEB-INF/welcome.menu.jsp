<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}/" />
        <tr>
            <td class="tg-0lax" colspan="2">
                <nav class="menu">
                    <ul>
                        <li><a id="navigation-menu-0" href="<c:out value="${baseURL}"/>welcome.jsp#" onClick="return false;">Главная</a></li>
                        <li><a id="navigation-menu-4" href="<c:out value="${baseURL}"/>inside.jsp#">Авторизация</a></li>
                    </ul>
                </nav>
            </td>
        </tr>
