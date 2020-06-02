package com.rainbow.datapermission.plugin.utils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.util.JdbcConstants;
import com.rainbow.datapermission.plugin.druid.ExportTableAliasVisitor;
import com.rainbow.datapermission.plugin.exception.SQLSyntaxException;
import com.rainbow.datapermission.plugin.object.ConcatBodyObject;
import com.rainbow.datapermission.plugin.object.TableName;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.alibaba.druid.util.JdbcConstants.MYSQL;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public final class SQLUtil {
    public static final String EMPTY_STRING = "";
    public static final String BLANK_SPACE = " ";

    public static final String FROM = "from";
    public static final String SELECT = "select";
    public static final String WHERE = "where";
    public static final String AS = "as";
    public static final String OR = BLANK_SPACE + "or" + BLANK_SPACE;
    public static final String AND = BLANK_SPACE + "and" + BLANK_SPACE;
    public static final String EQUALS = BLANK_SPACE + "=" + BLANK_SPACE;

    public static final String COMMA = ",";
    public static final String ALIAS_DOT = ".";
    public static final String LEFT_SINGLE_QUOTE = "'";
    public static final String RIGHT_SINGLE_QUOTE = "'";
    public static final String LEFT_BRACKET = "(";
    public static final String RIGHT_BRACKET = ")";
    public static final char LEFT_BRACKET_CHAR = '(';
    public static final char RIGHT_BRACKET_CHAR = ')';

    private static final String SQL_ALL = "*";

    public static List<TableName> lookUpTableNames(String sql) {
        List<TableName> tableNameList = new ArrayList<TableName>();
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
        ExportTableAliasVisitor visitor = new ExportTableAliasVisitor(tableNameList);

        for (SQLStatement stmt : stmtList) {
            stmt.accept(visitor);
        }
        return visitor.getUnmodifiedAliasTableList();
    }

    public static String aggregate(String sql, final List<ConcatBodyObject> fragmentList) {

        if (CollectionUtils.isEmpty(fragmentList)) {
            return sql;
        }

        for (ConcatBodyObject concatBodyObject : fragmentList) {
            //Expand SQL
            String tableAlias = concatBodyObject.getAlias();
            sql = aggregateSqlByAlias(sql, tableAlias, concatBodyObject.getBody());
        }

        return supply(sql, handleResultParams(sql), fragmentList);
    }

    private static String doNext(String sql, String fragmentSql) {
        String lowerCaseSql = FormatUtil.toLowerCase(sql);

        int whereIndex = lowerCaseSql.lastIndexOf(WHERE);

        if (whereIndex == -1) {

            String patternOfWhere = "(group\\s+by|limit|having|order\\s+by)";
            Pattern patOfWhere = Pattern.compile(patternOfWhere);

            Matcher matcherOfWhere = patOfWhere.matcher(lowerCaseSql);

            if (matcherOfWhere.find()) {
                int index = matcherOfWhere.start();
                String prefix = sql.substring(0, index - 1);
                String suffix = sql.substring(index - 1);
                sql = prefix + WHERE + BLANK_SPACE + fragmentSql + BLANK_SPACE + suffix + BLANK_SPACE;
            } else {
                sql = sql + WHERE + fragmentSql;
            }
        } else {
            final int whereLength = 5;
            String prefix = sql.substring(0, whereIndex + whereLength);
            String suffix = sql.substring(whereIndex + whereLength);

            sql = prefix + BLANK_SPACE + fragmentSql + BLANK_SPACE + AND + suffix;
        }
        return sql;
    }

    private static String handleResultParams(String sql) {
        String lowerCaseSql = FormatUtil.toLowerCase(sql);

        int selectIndex = lowerCaseSql.indexOf(SELECT);
        int fromIndex = lowerCaseSql.indexOf(FROM);

        if (selectIndex < 0 || fromIndex < 0) {
            return null;
        }

        return sql.substring(selectIndex + 6, fromIndex);
    }

    private static String supply(String sql, String resultParams, List<ConcatBodyObject> fragmentList) {
        if (StringUtils.isEmpty(resultParams) || CollectionUtils.isEmpty(fragmentList)) {
            return sql;
        }

        for (ConcatBodyObject fragment : fragmentList) {
            String tableName = fragment.getTableName();
            String fieldName = fragment.getFieldName();
            String alias = fragment.getAlias();
            //TODO Bug fix
            if (resultParams.contains(fieldName) ||
                    resultParams.contains(alias + ALIAS_DOT + SQL_ALL) ||
                    (tableName.equals(alias) && resultParams.contains(SQL_ALL))) {
                sql = doNext(sql, fragment.getBody());
            }
        }
        return sql;
    }


    public static String aggregateSqlByAlias(String sql, String alias, String fragment) {

        String pattern = "(" + alias + "\\s*\\.\\s*\\w+\\s*=)";
        Pattern pat = Pattern.compile(pattern);
        Matcher matcher = pat.matcher(sql);

        if (matcher.find()) {
            int index = matcher.start();
            String prefix = sql.substring(0, index - 1);
            String suffix = sql.substring(index - 1);
            sql = prefix + fragment + AND + suffix;
            return sql;
        }

        String reversePattern = "(=\\s*" + alias + "\\s*\\.\\s*\\w+\\s*)";
        Pattern reversePat = Pattern.compile(reversePattern);
        Matcher reverseMatcher = reversePat.matcher(sql);

        if (reverseMatcher.find()) {
            int index = reverseMatcher.end();
            String prefix = sql.substring(0, index);
            String suffix = sql.substring(index - 1);
            sql = prefix + AND + fragment + suffix;
            return sql;
        }
        return doNext(sql, fragment);
    }

    public static String process(String sql) {
        try {
            SQLUtils.parseStatements(sql, MYSQL);
        } catch (Exception e) {
            System.out.println(sql);
            throw new SQLSyntaxException("Syntax Error: SQL is illegal");
        }

        return sql;
    }

    public static String opsKey(String sql) throws Exception {
        sql = sql.trim();
        final int startIndex = 0;
        final int keyLengthIndex = 6;
        return sql.substring(startIndex, keyLengthIndex).toLowerCase();
    }

    public static boolean isIgnoreChildren(String[] ignoreChildren, String sql) {
        String formatSql = SQLUtils.format(sql, MYSQL);
        for (String children : ignoreChildren) {
            if (formatSql.equals(children)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isIgnoreParams(String[] ignoreParams, ConcatBodyObject concatBodyObject) {
        String fieldName = concatBodyObject.getFieldName();
        String tableName = concatBodyObject.getTableName();
        String body = concatBodyObject.getBody();

        for (String param : ignoreParams) {
            String[] splitParams = param.split("\\|");
            //Not right format
            if (splitParams.length != 2) {
                continue;
            }
            String paramTableName = splitParams[0].trim();
            String paramFieldName = splitParams[1].trim();
            if (tableName.equals(paramTableName) && fieldName.equals(paramFieldName)) {
                return true;
            }
        }
        return false;
    }
}
