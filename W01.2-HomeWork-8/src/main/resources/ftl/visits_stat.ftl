<#assign tags=JspTaglibs["/WEB-INF/custom_tag.tld"]>

<!DOCTYPE html>
<html lang=ru>
<head>
    <meta charset=UTF-8>
    <title id="title">Home Work 7 of JavaEE 2018-09 | a work showcasing the feature of Freemarker</title>
    <link rel="stylesheet" href="${base_url}css/style-all.min.css"/>
    <script type="text/javascript" language="javascript" src="${base_url}js/script-all.min.js"></script>
<@tags.sender_stat pageName = "visits_stat" />
    <script type="text/javascript" language="javascript" src="${base_url}inside/ws.js"></script>
    <script type="text/javascript" language="javascript" src="${base_url}inside/inside.nocache.js"></script>
</head>

<body class="body">
    <table class="tg body">
<@include_page path="/WEB-INF/header.jsp"/>
<@include_page path="/WEB-INF/inside.menu.jsp"/>
        <tr>
            <td class="tg-0lax my-left">
                <main id="main-statistic" class="w3-col m12 w3-margin-0 my-left">
                    Включен сбор статистики: <span id = "isCollectionEnabled">
                    <#if stat_enabled>
                        да
                    <#else>
                        нет
                    </#if>
                    </span>
                    <br>
                    <input type="button" value="Включить/отключить сбор статистики" onclick="switchCollectionEnabled()">

                    <div id="statistic-table">
                        <table class="tg1 body">
                            <tr class="tg1 main-header w3-center">
                                <th class="tg1-0lax">ID</th>
                                <th class="tg1-0lax">Marker</th>
                                <th class="tg1-0lax">Page</th>
                                <th class="tg1-0lax">IP</th>
                                <th class="tg1-0lax">Time</th>
                                <th class="tg1-0lax">Session-Id</th>
                                <th class="tg1-0lax">User-Name</th>
                                <th class="tg1-0lax">Previous-Id</th>
                            </tr>
                            <#list stat_elememts as elem>
                            <tr class="tg1">
                                <td class="tg1-0lax" style="text-align:right!important" rowspan="2">${elem.id}&nbsp;</td>
                                <td class="tg1-0lax">${elem.nameMarker}</td>
                                <td class="tg1-0lax w3-center">${elem.jspPageName}</td>
                                <td class="tg1-0lax w3-center">${elem.ipAddress}</td>
                                <td class="tg1-0lax w3-center"><#if elem.clientTime??>${elem.clientTime}</#if></td>
                                <td class="tg1-0lax w3-center"><#if elem.sessionId??>${elem.sessionId}</#if></td>
                                <td class="tg1-0lax w3-center"><#if elem.user??>${elem.user.name}</#if></td>
                                <td class="tg1-0lax w3-center"><#if elem.previousId??>${elem. previousId}</#if></td>
                            </tr>
                            <tr class="tg1">
                                <td class="tg1-0lax w3-center" colspan="7"><b>User-Agent:&nbsp;|&nbsp;</b>${elem.userAgent}</td>
                            </tr>
                            </#list>
                        </table>
                    </div>
                </main>
            </td>
            <td class="tg-0lax my-right" style="border-left: 2px dotted black;" colspan="2">
<@include_page path="/WEB-INF/aside.jsp"/>
            </td>
        </tr>
<@include_page path="/WEB-INF/footer.jsp"/>
    </table>
</body>
</html>
