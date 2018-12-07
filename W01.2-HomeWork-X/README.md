# otus-java-2018-09-VSkurikhin
## Для курса "Разработчик Java Enterprise" в Otus.ru

#### Группа 2018-09
##### Виктор Скурихин (Victor Skurikhin)

### Домашнее задание 9
 * Настроки GlassFish:
   * Configurations -> server-config -> Security -> Realms
     Создать `jdbc-realm` со следующими параметрами:
     *     Realm Name:                    jdbc-realm
           Class Name:                    com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm
           JAAS Context:                  jdbcRealm
           JNDI:                          jdbc/PostgresMyDB
           User Table:                    users
           User Name Column:              login
           Password Column:               password
           Group Table:                   user_groups
           Group Table User Name Column:  login
           Group Name Column:             groupname
           Password Encryption Algorithm: none
           Assign Groups:
           Database User:
           Database Password:
           Digest Algorithm:              SHA-256
           Encoding:                      Base64
           Charset:
 * Имя пользователя: `funt` 
 * Пароль: `funt`
 * Создан SOAP сервис ru.otus.soap.wservice.corptax.* и servlet.
 * Создан web интерфейс ru.otus.gwt.client.widget.TaxView.  
 * Консольный клиент ru.otus.CurrentTax.
 * Импортирован пакет ru.otus.models.cbr из src/main/resources/DailyInfo.wsdl.
 * Созданы два REST сервиса ru.otus.rest.cbr.CursOnDate и ru.otus.rest.cbr.LastDate.
 * Для SOAP запроса getCursOnDateXML создан GetCursOnDateXMLAdapter.
