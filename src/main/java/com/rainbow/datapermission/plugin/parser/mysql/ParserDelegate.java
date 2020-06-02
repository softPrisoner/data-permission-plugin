package com.rainbow.datapermission.plugin.parser.mysql;

import com.rainbow.datapermission.plugin.concat.SQLConcat;
import com.rainbow.datapermission.plugin.exception.SQLConcatNotFoundException;
import com.rainbow.datapermission.plugin.executor.Executor;
import com.rainbow.datapermission.plugin.object.ConcatBodyObject;
import com.rainbow.datapermission.plugin.object.ConstraintTypeObject;
import com.rainbow.datapermission.plugin.object.TableName;
import com.rainbow.datapermission.plugin.register.Register;
import com.rainbow.datapermission.plugin.utils.FormatUtil;
import com.rainbow.datapermission.plugin.utils.SQLUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rainbow.datapermission.plugin.utils.SQLUtil.*;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.toList;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public class ParserDelegate {
    private final static String VIRTUAL_T = "VT_";

    private final Register<SQLConcat> register;
    private final Executor executor;

    public ParserDelegate(Register<SQLConcat> register, Executor executor) {
        this.register = register;
        this.executor = executor;
    }

    public String divide(String sql, Map<String, String> params, String[] ignoreChildren, String[] ignoreParams) {
        Map<String, String> map = new HashMap<>(16);
        //support personalized configuration which point the SQL need to ignore
        if (SQLUtil.isIgnoreChildren(ignoreChildren, sql)) {
            return sql;
        }

        String lowerCaseSql = FormatUtil.toLowerCase(sql);
        Pattern pattern = compile("\\(\\s*select");
        Matcher matcher = pattern.matcher(lowerCaseSql);

        if (matcher.find()) {
            int startIndex = 0, endIndex;
            int count = 0;

            for (int i = 0; i < sql.length(); i++) {
                if (sql.charAt(i) == LEFT_BRACKET_CHAR) {
                    if (count == 0) {
                        startIndex = i;
                    }
                    count++;
                }

                if (sql.charAt(i) == RIGHT_BRACKET_CHAR) {
                    count--;
                    if (count == 0) {
                        endIndex = i;

                        String temp = sql.substring(startIndex + 1, endIndex);
                        Pattern matchPattern = compile("\\s*select");
                        String lowerCaseTemp = FormatUtil.toLowerCase(temp);
                        Matcher matchMatcher = matchPattern.matcher(lowerCaseTemp);

                        if (matchMatcher.find()) {
                            sql = sql.replace(temp, VIRTUAL_T + i);
                            map.put(VIRTUAL_T + i, divide(temp, params, ignoreChildren, ignoreParams));
                        }
                    }
                }
            }
        }

        sql = collectFragment(sql, params, ignoreParams);
        for (String key : map.keySet()) {
            sql = sql.replace(key, map.get(key));
        }
        return sql;
    }

    public String collectFragment(String sql, Map<String, String> params, String[] ignoreParams) {
        List<ConcatBodyObject> fragmentList = new ArrayList<ConcatBodyObject>();

        List<TableName> tableNameList = lookUpTableNames(sql);

        tableNameList.parallelStream().forEach(element -> {
            String tableAlias = element.getAlias();
            String tableName = element.getName();

            Map<String, Map<String, List<ConstraintTypeObject>>>
                    constraintTypeMap = executor.execute(tableName, params);

            Set<String> constraintTypeSet = constraintTypeMap.keySet();

            for (String constraintType : constraintTypeSet) {
                Map<String, List<ConstraintTypeObject>> classifyByNameMap = constraintTypeMap.get(constraintType);
                Collection<List<ConstraintTypeObject>> constraintTypeCollection = classifyByNameMap.values();

                for (List<ConstraintTypeObject> dataFieldNameList : constraintTypeCollection) {
                    SQLConcat sqlConcat;
                    try {
                        sqlConcat = register.lookFor(constraintType);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }

                    if (Objects.isNull(sqlConcat)) {
                        throw new SQLConcatNotFoundException("No suitable concat found");
                    }
                    ConcatBodyObject concat = sqlConcat.concat(dataFieldNameList, tableAlias);
                    if (Objects.isNull(concat)) {
                        continue;
                    }
                    concat.setTableName(tableName);
                    fragmentList.add(concat);
                }
            }
        });
        List<ConcatBodyObject> filterFragmentList = fragmentList.parallelStream()
                .filter(fragment -> !isIgnoreParams(ignoreParams, fragment)).collect(toList());
        return aggregate(sql, filterFragmentList);
    }
}
