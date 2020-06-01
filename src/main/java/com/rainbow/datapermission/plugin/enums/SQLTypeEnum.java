package com.rainbow.datapermission.plugin.enums;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 * @description
 */
public enum SQLTypeEnum {
    /**
     * char
     */
    CHAR("char", "char"),

    /**
     * varchar
     */
    VARCHAR("varchar", "varchar"),

    /**
     * date
     */
    DATE("date", "date"),
    /**
     * time
     */
    TIME("time", "time"),
    /**
     * year
     */
    YEAR("year", "year"),
    /**
     * timestamp
     */
    TIME_STAMP("timestamp", "timestamp"),
    /**
     * datetime
     */
    DATE_TIME("datetime", "datetime"),
    /**
     * tinytext
     */
    TINY_TEXT("tinytext", "tinytext"),
    /**
     * mediumtext
     */
    MEDIUM_TEXT("mediumtext", "mediumtext"),
    /**
     * longtext
     */
    LONG_TEXT("longtext", "longtext"),
    /**
     * enum
     */
    ENUM("enum", "enum");
    /**
     * key
     */
    public String key;
    /**
     * description
     */
    public String description;

    SQLTypeEnum(String key, String description) {
        this.key = key;
        this.description = description;
    }

    /**
     * Is String type?
     *
     * @param type type
     * @return It's type true|false
     */
    public static boolean isStringSource(String type) {
        for (SQLTypeEnum t : SQLTypeEnum.values()) {
            if (t.key.equals(type)) {
                return true;
            }
        }
        return false;
    }
}
