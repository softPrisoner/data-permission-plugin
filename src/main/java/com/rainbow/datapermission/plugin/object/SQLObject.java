package com.rainbow.datapermission.plugin.object;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public abstract class SQLObject {
    /**
     * Field Name
     */
    private String fieldName;
    /**
     * Field Type
     */
    private String fieldType;

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldType() {
        return fieldType;
    }
}
