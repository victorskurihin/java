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
        <div class="div-header">
            <h1>Рога и копыта</h1>
            <h2>Заведение, занимается заготовкой «когтей и хвостов» и «горчицы и щёлока»</h2>
        </div>
        <label class="search-label" for="search">
            Поиск: <input type="search" id="search" name="search">
        </label>
    </header>

    <nav>
        <ul>
            <li><a href="index.jsp">«ГЕРКУЛЕС»</a></li>
            <li><a href="login.jsp">«Лицом к деревне»</a></li>
            <li><a href="contacs.jsp">«Адлер»</a></li>
            <li><a href="archive.jsp">«Перерыв на обед»</a></li>
        </ul>
    </nav>

    <main class = "w3-row w3-gray w3-margin-0">
        <section class = "w3-col m9 w3-white w3-margin-0" style="width: 85%">
            <hr class="separator">
            <form method="post" action="j_security_check" class="loginForm">
                <div class="w3-row text-margin">
                    <div class="w3-col m1">
                        <label for="login">Login:</label>
                    </div>
                    <div class="w3-col m2">
                        <input type="text" id="login" name="j_username" autocomplete="off" placeholder="Type your login"/>
                    </div>
                </div>
                <div class="w3-row text-margin">
                    <div class="w3-col m1">
                        <label for="password">Password:</label>
                    </div>
                    <div class="w3-col m2">
                        <input type="password" id="password" name="j_password" autocomplete="off" placeholder="Type your password"/>
                    </div>
                </div>
                <div class="w3-row text-margin">
                    <div class="w3-col m1 submitArea">
                        <input type="submit" id="submit" value="Logon"/>
                    </div>
                </div>
            </form>
            <script>
                var checkLoginForm = function() {
                    var checked = document.getElementById('login').value != ''
                        && document.getElementById('password').value != '';
                    if (checked) {
                        document.getElementById('submit').removeAttribute('disabled');
                    }
                    else {
                        document.getElementById('submit').setAttribute('disabled', 'disabled')
                    }
                };

                checkLoginForm();

                var focusListener = function(event) {
                    event.target.style.background = "#528394";
                };
                var blurListener = function(event) {
                    event.target.style.background = "";
                    checkLoginForm();
                };
                var inputElement = document.getElementById('login');
                inputElement.addEventListener("focus", focusListener, true);
                inputElement.addEventListener("blur", blurListener, true);

                inputElement = document.getElementById('password');
                inputElement.addEventListener("focus", focusListener, true);
                inputElement.addEventListener("blur", blurListener, true);
            </script>
            <hr class="separator">
        </section>

        <aside class = "w3-col m1 w3-margin-0" style="width: 15%">
            <ul>
                <li><a href="index.jsp">«Адлер»</a></li>
                <li><a href="login.jsp">«Лицом к деревне»</a></li>
                <li><a href="contacs.jsp">«ГЕРКУЛЕС»</a></li>
                <li><a href="archive.jsp">«Перерыв на обед»</a></li>
            </ul>
        </aside>
    </main>

    <footer class="w3-container w3-light-gray">
        <p>&copy; 2018 ООО Рога и копыта. Свои права мы держим в надёжном месте.</p>
    </footer>
</body>
</html>
