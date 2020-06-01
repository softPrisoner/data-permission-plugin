package com.rainbow.datapermission.plugin.enums;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public enum SQLOpsEnum {
    /**
     * SELECT
     */
    SELECT("select", "Select"),
    /**
     * UPDATE
     */
    UPDATE("update", "update"),
    /**
     * DELETE
     */
    DELETE("delete", "delete"),
    /**
     * INSERT
     */
    INSERT("insert", "insert");

    /**
     * Key
     */
    public String key;
    /**
     * Description
     */
    public String description;

    SQLOpsEnum(String key, String description) {
        this.key = key;
        this.description = description;
    }

}
