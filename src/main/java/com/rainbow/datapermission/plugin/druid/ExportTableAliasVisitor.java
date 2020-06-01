package com.rainbow.datapermission.plugin.druid;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import com.rainbow.datapermission.plugin.object.TableName;
import com.rainbow.datapermission.plugin.utils.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public class ExportTableAliasVisitor extends MySqlASTVisitorAdapter {
    private final List<TableName> tableNameList;

    public ExportTableAliasVisitor(List<TableName> tableNameList) {
        this.tableNameList = tableNameList;
    }

    @Override
    public boolean visit(SQLExprTableSource expr) {
        String alias = expr.getAlias();
        SQLName name = expr.getName();
        String simpleName = name.getSimpleName();

        TableName tableName = new TableName();
        tableName.setAlias(StringUtils.isEmpty(alias) ? simpleName : alias);
        tableName.setName(simpleName);
        tableNameList.add(tableName);
        return true;
    }

    public List<TableName> getUnmodifiedAliasTableList() {
        return Collections.unmodifiableList(tableNameList);
    }
}