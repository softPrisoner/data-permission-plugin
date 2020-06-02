package com.rainbow.datapermission.plugin.object;

import java.io.Serializable;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public class ConcatBodyObject implements Serializable {

    private String tableName;
    /**
     * Alias Name
     */
    private String alias;
    /**
     * SQL Body
     */
    private String body;

    /**
     * Field Name
     */
    private String fieldName;

    public ConcatBodyObject(String alias, String body, String fieldName) {
        this.alias = alias;
        this.body = body;
        this.fieldName = fieldName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
