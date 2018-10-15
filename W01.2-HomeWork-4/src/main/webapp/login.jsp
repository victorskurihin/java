<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang=en>
<head>
    <meta charset=UTF-8>
    <title>Home Work 3 of JavaEE 2018-09 | a work showcasing the feature of HTML5 &amp; CSS3</title>
    <link rel="stylesheet" href="css/style-all.min.css"/>
    <script type="text/javascript" language="javascript" src="js/script-all.min.js"></script>
</head>

<body class="body">
    <header class="w3-container main-header w3-center">
        <div class="div-header" style="width: 79%; min-width: 800px">
            <h1>Рога и копыта</h1>
            <h2>Заведение, занимается заготовкой «когтей и хвостов» и «горчицы и щёлока»</h2>
        </div>
        <div class="w3-cell w3-margin-0" style="width: 21%; min-width: 212px">
            <form class="find">
                <input class="find" name="search" id="search" placeholder="Искать здесь..." type="search">
                <button class="find" onclick="goSearch()"></button>
            </form>
        </div>
    </header>

    <nav>
        <ul>
            <li><a href="index.jsp">Главная</a></li>
            <li><a href="login.jsp">Вход в систему</a></li>
            <li><a href="contacts.jsp">Контакты</a></li>
            <li><a href="backform.jsp">Обратная связь</a></li>
            <li><a href="archive.jsp">Архивы новостей</a></li>
        </ul>
    </nav>

    <main class = "w3-row w3-gray w3-margin-0">
        <section class = "w3-col m9 w3-white w3-margin-0" style="width: 85%">
            <hr class="separator w3-margin">
            <hr class="separator w3-margin">
            <form method="post" action="j_security_check" class="loginForm" onsubmit="return checkPassword(this);">
                <div class="w3-row login-margin">
                    <div class="w3-col m1 text-margin">
                        <label for="login">Login: </label>
                    </div>
                    <div class="w3-col m2">
                        <input type="text" id="login" name="j_username" autocomplete="off" placeholder="Type your login"/>
                    </div>
                </div>
                <div class="w3-row login-margin">
                    <div class="w3-col m1 text-margin">
                        <label for="password">Password: </label>
                    </div>
                    <div class="w3-col m2">
                        <input type="password" id="password" name="j_password" autocomplete="off" placeholder="Type your password"/>
                    </div>
                </div>
                <div class="w3-row login-margin">
                    <div class="w3-col m1 submitArea text-margin">
                        <input type="submit" id="submit" value="Logon"/>
                    </div>
                </div>
            </form>

            <script>
                checkLoginForm();

                var inputElement = document.getElementById('login');
                inputElement.addEventListener("focus", focusListener, true);
                inputElement.addEventListener("blur", blurListener, true);

                inputElement = document.getElementById('password');
                inputElement.addEventListener("focus", focusListener, true);
                inputElement.addEventListener("blur", blurListener, true);
            </script>

            <hr class="separator w3-margin">
            <p class="w3-margin"> &nbsp; </p>
        </section>

        <aside class = "w3-col m1 w3-margin-0" style="width: 15%">
            <ul>
                <li><a href="index.jsp">«Адлер»</a></li>
                <li><a href="login.jsp">«Лицом к деревне»</a></li>
                <li><a href="contacts.jsp">«ГЕРКУЛЕС»</a></li>
                <li><a href="archive.jsp">«Перерыв на обед»</a></li>
            </ul>
            <ul class="w3-margin">
                <li id="AMD"></li>
                <li id="GBP"></li>
                <li id="USD"></li>
            </ul>
            <script>getCBRJSON(fillUl);</script>
        </aside>
    </main>

    <footer class="w3-container w3-light-gray">
        <p class="w3-margin">&copy; 2018 ООО Рога и копыта. Свои права мы держим в надёжном месте.</p>
    </footer>
</body>
</html>
