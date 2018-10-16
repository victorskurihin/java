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
    <table class="tg">
        <tr class="main-header w3-center">
            <th class="tg-0lax">
                <header class="w3-center div-header my-left">
                    <h1>Рога и копыта</h1>
                    <h2>Заведение, занимается заготовкой «когтей и хвостов» и «горчицы и щёлока»</h2>
                </header>
            </th>
            <th class="tg-lqy6">
                <div class="w3-cell w3-margin-0 my-right w3-right">
                    <form class="find">
                        <input class="find" name="search" id="search" placeholder="Искать здесь..." type="search">
                        <button class="find" onclick="goSearch()"></button>
                    </form>
                </div>

            </th>
        </tr>
        <tr>
            <td class="tg-0lax" colspan="2">
                <nav>
                    <ul>
                        <li><a href="index.jsp">Главная</a></li>
                        <li><a href="login.jsp">Вход в систему</a></li>
                        <li><a href="contacts.jsp">Контакты</a></li>
                        <li><a href="backform.jsp">Обратная связь</a></li>
                        <li><a href="archive.jsp">Архивы новостей</a></li>
                    </ul>
                </nav>
            </td>
        </tr>
        <tr>
            <td class="tg-0lax my-left">
                <main class = "w3-col m12">
                    <hr class="separator">
                    <article class="article" id="article1">
                        <header class="w3-row article">
                            <time class="w3-col m1" datetime="2018-10-14" pubdate>
                                <span>Окт</span> 14
                            </time>
                            <h1 class="w3-col m9 w3-margin-0 article">
                                <a class="article" href="#" title="Ссылка на новость" rel="bookmark">
                                    Два комбинатора
                                </a>
                            </h1>
                        </header>
                        <p class="p-text-justify text-margin">
                            Жил на свете частник бедный. Это был довольно богатый человек, владелец галантерейного магазина, расположенного наискось от кино «Капи-талий». Он безмятежно торговал бельем, кружевными прошвами, галстуками, подвязками и другим мелким, но прибыльным товаром.
                        </p>
                    </article>

                    <hr class="separator">
                    <article class="article" id="article2">
                        <header class="w3-row article">
                            <time class="w3-col m1" datetime="2018-10-11" pubdate>
                                <span>Окт</span> 11
                            </time>
                            <h1 class="w3-col m9 w3-margin-0 article">
                                <a class="article" href="#" title="Ссылка на новость" rel="bookmark">
                                    Черноморское отделение Арбатовской конторы по заготовке рогов и копыт
                                </a>
                            </h1>
                        </header>
                        <p class="p-text-justify text-margin">
                            Праздный черноморец, заглянув в магазин, мог бы заметить, что прилавки и полки исчезли, пол был чисто вымыт, стояли яичные конторские столы, а на стенах висели обыкновенные учрежденские плакаты насчет часов приема и вредности рукопожатий. Новоявленное учреждение уже пересекал барьер, выставленный против посетителей, которых, однако, еще не было.
                        </p>
                    </article>

                    <hr class="separator">
                    <article class="article" id="article3">
                        <header class="w3-row article">
                            <time class="w3-col m1" datetime="2018-10-10" pubdate>
                                <span>Окт</span> 10
                            </time>
                            <h1 class="w3-col m9 w3-margin-0 article">
                                <a class="article" href="#" title="Ссылка на новость" rel="bookmark">
                                    На базаре была куплена старая пишущая машинка «Адлер»
                                </a>
                            </h1>
                        </header>
                        <p class="p-text-justify text-margin">
                            Но даже машинка с удивительным прононсом не могла омрачить светлой радости великого комбинатора. Ему очень нравилось новое поприще. Ежечасно он прибегал в контору с покупками. Он приносил такие сложные канцелярские машины и приборы, что курьер и уполномоченный только ахали. Тут были дыропробиватели, копировальные прессы, винтовой табурет и дорогая бронзовая чернильница в виде нескольких избушек для разного цвета чернил.
                        </p>
                    </article>

                    <hr class="separator">
                    <article class="article" id="article4">
                        <header class="w3-row article">
                            <time class="w3-col m1" datetime="2018-10-09" pubdate>
                                <span>Окт</span> 9
                            </time>
                            <h1 class="w3-col m9 w3-margin-0 article">
                                <a class="article" href="#" title="Ссылка на новость" rel="bookmark">
                                    Инвесторы видят перспективу
                                </a>
                            </h1>
                        </header>
                        <p class="p-text-justify text-margin">
                            Ничто не мешает людям воспользоваться рогами зайцелопа. Однако копыт у него нет.
                        </p>
                    </article>

                    <hr class="separator">
                </main>
            </td>
            <td class="tg-0lax my-right" style="border-left: 2px dotted black;" colspan="2">
                <aside class = "w3-col w3-margin-0 my-right">
                    <ul>
                        <li><a href="index.jsp">«ГЕРКУЛЕС»</a></li>
                        <li><a href="login.jsp">«Лицом к деревне»</a></li>
                        <li><a href="contacts.jsp">«Адлер»</a></li>
                        <li><a href="archive.jsp">«Перерыв на обед»</a></li>
                    </ul>
                    <ul class="w3-margin">
                        <li id="AMD"></li>
                        <li id="GBP"></li>
                        <li id="USD"></li>
                    </ul>
                    <ul>
                        <li class="news" id="news1"></li>
                        <li class="news" id="news2"></li>
                        <li class="news" id="news3"></li>
                        <li class="news" id="news4"></li>
                    </ul>
                    <script>
                        getJSON(fillUlForex, 'cbrforex');
                        getJSON(fillUlNews, 'jsouprbc');
                    </script>
                </aside>
            </td>
        </tr>
        <tr>
            <td class="tg-0lax" colspan="2">
                <footer class="w3-container w3-light-gray">
                    <p class="w3-margin">&copy; 2018 ООО Рога и копыта. Свои права мы держим в надёжном месте.</p>
                </footer>
            </td>
        </tr>
    </table>

</body>
</html>
