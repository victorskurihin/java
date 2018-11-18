<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}/" />
        <tr>
            <td class="tg-0lax" colspan="2">
                <nav class="menu">
                    <ul>
                        <li><a id="navigation-menu-0" href="<c:out value="${baseURL}"/>inside.jsp#" onClick="return false;">Главная</a></li>
                        <li><a id="navigation-menu-1" href="<c:out value="${baseURL}"/>inside.jsp#" onClick="return false;">Контакты</a></li>
                        <li><a id="navigation-menu-2" href="<c:out value="${baseURL}"/>inside.jsp#" onClick="return false;">Обратная связь</a></li>
                        <li><a id="navigation-menu-3" href="<c:out value="${baseURL}"/>inside.jsp#" onClick="return false;">Архивы новостей</a></li>
                        <li><a id="navigation-menu-4" href="<c:out value="${baseURL}"/>inside.jsp#" onClick="return false;">Список сотрудников</a></li>
                        <li><a id="navigation-menu-r" href="<c:out value="${baseURL}"/>inside/stat.do">Статистика</a></li>
                    </ul>
                </nav>
            </td>
        </tr>
