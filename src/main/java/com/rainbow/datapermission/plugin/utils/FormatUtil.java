package com.rainbow.datapermission.plugin.utils;

import com.alibaba.druid.sql.SQLUtils;

import static com.alibaba.druid.util.JdbcConstants.MYSQL;

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

    public static String[] formatIgnoreChildren(final String[] ignoreChildren) {
        for (int i = 0; i < ignoreChildren.length; i++) {
            try {
                ignoreChildren[i] = SQLUtils.format(ignoreChildren[i], MYSQL);
            } catch (Exception e) {

            }
        }
        return ignoreChildren;
    }

    public static String[] formatIgnoreParams(final String[] ignoreParams) {
        for (int i = 0; i < ignoreParams.length; i++) {
            try {
                ignoreParams[i] = ignoreParams[i].trim();
            } catch (Exception e) {
                continue;
            }
        }
        return ignoreParams;
    }
}
