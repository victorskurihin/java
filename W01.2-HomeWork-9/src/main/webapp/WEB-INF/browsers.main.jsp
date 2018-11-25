<%@ page contentType="text/html; charset=UTF-8" %>
<%
String hrefIE = "href='https://support.microsoft.com/ru-kz/help/17621'";
String hrefChrome = "href='https://www.google.com/chrome'";
String hrefOpera = "href='https://www.opera.com/computer'";
String hrefSafari = "href='https://safari-windows.ru/'";
%>
                    <table class="my-browser-table">
                        <thead>
                        <tr>
                            <th class="tg-0lax" colspan="2"><h3>Вы используете неподдерживаемую версию браузера. Пожалуйста, воспользуйтесь последней версией одного из указанных браузеров:</h3>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><a <%= hrefIE %> target="_blank"><img src="img/ie.png" class="my-browser-icon"></a></td>
                            <td><a <%= hrefIE %> target="_blank">Internet Explorer</a></td>
                        </tr>
                        <tr>
                            <td><a <%= hrefChrome %> target="_blank"><img src="img/chrome.png" class="my-browser-icon"></a></td>
                            <td><a <%= hrefChrome %> target="_blank">Google Chrome</a></td>
                        </tr>
                        <tr>
                            <td><a <%= hrefOpera %> target="_blank"><img src="img/opera.png" class="my-browser-icon"></a></td>
                            <td><a <%= hrefOpera %> target="_blank">Opera</a></td>
                        </tr>
                        <tr>
                            <td><a <%= hrefSafari %> target="_blank"><img src="img/safari.png" class="my-browser-icon"></a></td>
                            <td><a <%= hrefSafari %> target="_blank">Safari</a></td>
                        </tr>
                        </tbody>
                    </table>
