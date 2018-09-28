<%--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>
<%@page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="JAVA" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<-- Основные теги создания циклов, определения условий, вывода информации на страницу и т .д. -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Теги для работы с XML-документами -->
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<!-- Теги для работы с базами данных -->
<!-- %@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/sql" % -->

<!-- Теги для форматирования и интернационализации информации (i10n и i18n) -->
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@taglib prefix="eg" uri="hellotag" %>

<%
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy");
    request.setAttribute("year", sdf.format(new java.util.Date()));
    request.setAttribute("tomcatUrl", "https://tomcat.apache.org/");
    request.setAttribute("tomcatDocUrl", "/docs/");
    request.setAttribute("tomcatExamplesUrl", "/examples/");
    List<String> list = new ArrayList<String>();
    list.add("Продукты");
    list.add("Одежда");
    list.add("Обувь");
    list.add("Спортивный инвентарь");
    request.setAttribute("list", list);
%>

<jsp:useBean id="appslist" scope="page" class="su.svn.appslist.MyAppsListBean" />

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title><%=request.getServletContext().getServerInfo() %></title>
        <link href="/favicon.ico" rel="icon" type="image/x-icon" />
        <link href="/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <link href="/tomcat.css" rel="stylesheet" type="text/css" />
    </head>

    <body>
        <div id="wrapper">
            <div id="navigation" class="curved container">
                <span id="nav-home"><a href="${tomcatUrl}">Home</a></span>
                <span id="nav-hosts"><a href="${tomcatDocUrl}">Documentation</a></span>
                <span id="nav-config"><a href="${tomcatDocUrl}config/">Configuration</a></span>
                <span id="nav-examples"><a href="${tomcatExamplesUrl}">Examples</a></span>
                <span id="nav-wiki"><a href="https://wiki.apache.org/tomcat/FrontPage">Wiki</a></span>
                <span id="nav-lists"><a href="${tomcatUrl}lists.html">Mailing Lists</a></span>
                <span id="nav-help"><a href="${tomcatUrl}findhelp.html">Find Help</a></span>
                <br class="separator" />
            </div>
            <div id="asf-box">
                <eg:hello />
                <!-- h1>${pageContext.servletContext.serverInfo}</h1 -->
            </div>
            <div id="upper" class="curved container">
                <div id="congrats" class="curved container">
                    <h2>If you're seeing this, you've successfully installed Tomcat. Congratulations!</h2>
                </div>
                <div id="notice">
                    <img src="/tomcat.png" alt="[tomcat logo]" />
                    <div id="tasks">
                        <h3>Recommended Reading:</h3>
                        <h4><a href="${tomcatDocUrl}security-howto.html">Security Considerations HOW-TO</a></h4>
                        <h4><a href="${tomcatDocUrl}manager-howto.html">Manager Application HOW-TO</a></h4>
                        <h4><a href="${tomcatDocUrl}cluster-howto.html">Clustering/Session Replication HOW-TO</a></h4>
                    </div>
                </div>
                <div id="actions">
                    <div class="button">
                        <a class="container shadow" href="/manager/status"><span>Server Status</span></a>
                    </div>
                    <div class="button">
                        <a class="container shadow" href="/manager/html"><span>Manager App</span></a>
                    </div>
                    <div class="button">
                        <a class="container shadow" href="/host-manager/html"><span>Host Manager</span></a>
                    </div>
                </div>
                <!--
                <br class="separator" />
                -->
                <br class="separator" />
            </div>
            <div id="middle" class="curved container">
                <h3>Developer Quick Start</h3>
                <div class="col25">
                    <div class="container">
                        <p><a href="/myapps">My Applications</a></p>
                    </div>
                </div>
                <div class="col25">
                    <div class="container">
                        <p><a href="servlet">Click here to see myapp servlet</a>
                    </div>
                </div>
                <div class="col25">
                    <div class="container">
                        <p><a href="/myhello">myhello Maven Webapp</a>
                    </div>
                </div>
                <div class="col25">
                    <div class="container">
                        <!-- <p><a href="/myreqinfo">Click here to see servlet</a> -->
                    </div>
                </div>
                <br class="separator" />
            </div>


            <!-- отображение текста 16+64*2 -->
            <c:out value="16+64*2" />

            <!-- отображение значения 144 -->
            <c:out value="${16+64*2}" />

            <c:set var="salary" scope="page" value="${23401*2}"/>
            <c:if test="${salary > 45000}">
                <p>Salary = <c:out value="${salary}"/><p>
            </c:if>

            <h3>Список групп товаров</h3>
            <ul>
               <c:forEach items="${list}" var="i" >
                 <li><c:out value="${i}" /></li><br />
               </c:forEach>
            </ul>

            <table>
                <c:forEach items="${appslist.items}" var="item">
                    <tr><td>${item.name}</td></tr>
                </c:forEach>
            </table>

            <div id="footer" class="curved container">
                <div class="col20">
                    <div class="container">
                        <h4>Other Downloads</h4>
                        <ul>
                            <li><a href="${tomcatUrl}download-connectors.cgi">Tomcat Connectors</a></li>
                            <li><a href="${tomcatUrl}download-native.cgi">Tomcat Native</a></li>
                            <li><a href="${tomcatUrl}taglibs/">Taglibs</a></li>
                            <li><a href="${tomcatDocUrl}deployer-howto.html">Deployer</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col20">
                    <div class="container">
                        <h4>Other Documentation</h4>
                        <ul>
                            <li><a href="${tomcatUrl}connectors-doc/">Tomcat Connectors</a></li>
                            <li><a href="${tomcatUrl}connectors-doc/">mod_jk Documentation</a></li>
                            <li><a href="${tomcatUrl}native-doc/">Tomcat Native</a></li>
                            <li><a href="${tomcatDocUrl}deployer-howto.html">Deployer</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col20">
                    <div class="container">
                        <h4>Get Involved</h4>
                        <ul>
                            <li><a href="${tomcatUrl}getinvolved.html">Overview</a></li>
                            <li><a href="${tomcatUrl}svn.html">SVN Repositories</a></li>
                            <li><a href="${tomcatUrl}lists.html">Mailing Lists</a></li>
                            <li><a href="https://wiki.apache.org/tomcat/FrontPage">Wiki</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col20">
                    <div class="container">
                        <h4>Miscellaneous</h4>
                        <ul>
                            <li><a href="${tomcatUrl}contact.html">Contact</a></li>
                            <li><a href="${tomcatUrl}legal.html">Legal</a></li>
                            <li><a href="https://www.apache.org/foundation/sponsorship.html">Sponsorship</a></li>
                            <li><a href="https://www.apache.org/foundation/thanks.html">Thanks</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col20">
                    <div class="container">
                        <h4>Apache Software Foundation</h4>
                        <ul>
                            <li><a href="${tomcatUrl}whoweare.html">Who We Are</a></li>
                            <li><a href="${tomcatUrl}heritage.html">Heritage</a></li>
                            <li><a href="https://www.apache.org">Apache Home</a></li>
                            <li><a href="${tomcatUrl}resources.html">Resources</a></li>
                        </ul>
                    </div>
                </div>
                <br class="separator" />
            </div>
            <p class="copyright">Copyright &copy;1999-${year} Apache Software Foundation.  All Rights Reserved</p>
        </div>
    </body>
</html>
