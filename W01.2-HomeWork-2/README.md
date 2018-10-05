# otus-java-2018-09-VSkurikhin
## Для курса "Разработчик Java Enterprise" в Otus.ru

#### Группа 2018-09
##### Виктор Скурихин (Victor Skurikhin)

### Домашнее задание 2 
 * Нулевой этап – анализ произведён в домашнем задании 1.
 * Первый этап - проектирование в каталоге `doc` файлы `HomeWork-2.pdf` и `HomeWork-2.png`.
 * Второй этап – подготовка схемы БД. Скрипты `createSchema.bat` и `createSchema.sh`. 
   Предварительно скомпилировать: 
   *     mvn clean compile dependency:copy-dependencies. 
   Настройки доступа к БД (PostgreSQL):
   *     src/main/resources/META-INF/persistence.xml
         src/main/resources/createSchema.hibernate.cfg.xml
         src/main/java/ru/otus/db/DBConf.java.
 * Третий этап – подготовка Application уровня. Сервлеты `JPARegistryServlet` и 
`JDBCDirectoryServlet`.
  Первые два пункта в `index.jsp`.
 * Четвертый этап – наполнение таблиц. Скрипты `loadTables.bat` и `loadTables.sh`.
 * Пятый шаг – поиск, модификация и удаление данных. Сервлеты `JPAChangeServlet` 
 `JPADeleteServlet`. Пункты 3-7 в `index.jsp`.
 * Шестой шаг – работа с хранимыми процедурами. Скрипты `createFunction.bat` и 
  `createFunction.sh` а так же сервлет `JDBCMaxSalaryServlet` Пункт 8.
