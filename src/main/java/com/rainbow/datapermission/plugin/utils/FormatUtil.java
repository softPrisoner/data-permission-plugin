package com.rainbow.datapermission.plugin.utils;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public class FormatUtil {
    public static Integer transfer2Integer(String s) {
        return Integer.valueOf(s);
    }

    public static String toLowerCase(String sql) {
        return sql.toLowerCase();
    }

   public static String replaceChineseComma(String condition) {
        return condition.replaceAll("，", ",");
    }
}
