package ru.otus.l101.dao;

/**
 * This is helper class used to contained the SQL command structure and contains
 * additional information such as primary key of the object, table name for the
 * object.
 */
public class SQLCommand {
    private final String tableName;
    private long id;
    private String sql;

    SQLCommand(String sql) {
        tableName = "";
        this.sql = sql;
    }

    SQLCommand(String sql, String tableName) {
        this.tableName = tableName;
        this.sql = sql + tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SQLCommand closeParenthesis() {
        sql = sql.concat(" )");
        return this;
    }

    public SQLCommand openParenthesis() {
        sql = sql.concat(" (");
        return this;
    }

    public SQLCommand concat(String s) {
        sql = sql.concat(s);
        return this;
    }

    public SQLCommand valuesWord() {
        return this.concat(" VALUES");
    }

    @Override
    public String toString() {
        return "SQLCommand{" +
            "sql='" + sql + '\'' +
            '}';
    }
}
