package com.rainbow.datapermission.plugin.object;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public class TableName {
    /**
     * Simple Name
     */
    private String name;
    /**
     * Alias Name
     */
    private String alias;

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
