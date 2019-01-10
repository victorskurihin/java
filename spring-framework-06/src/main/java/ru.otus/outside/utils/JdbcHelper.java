package ru.otus.outside.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcHelper
{
    public static int getIntOrDefault(ResultSet rs, String filedName)
    {
        try {
            return  rs.getInt(filedName);
        } catch (SQLException e) {
            return 0;
        }
    }

    public static long getLongOrDefault(ResultSet rs, String filedName)
    {
        try {
            return  rs.getLong(filedName);
        } catch (SQLException e) {
            return 0;
        }
    }

    public static Object getObjectOrDefault(ResultSet rs, String filedName)
    {
        try {
            return  rs.getString(filedName);
        } catch (SQLException e) {
            return null;
        }
    }

    public static String getStringOrDefault(ResultSet rs, String filedName)
    {
        try {
            return  rs.getString(filedName);
        } catch (SQLException e) {
            return null;
        }
    }
}
