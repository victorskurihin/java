package ru.otus.l092;

import org.apache.ibatis.jdbc.SQL;

public class CityQueties {

    public String findCityByCountry(String country) {
        return new SQL() {{
                SELECT("name", "state");
                FROM("city");

                if (null != country) {
                    WHERE("country = #{country}");
                }
                ORDER_BY("name");
            }}.toString();
    }
}
