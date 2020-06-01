package com.rainbow.datapermission.plugin.request;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public final class SQLRequest implements Serializable {
    private String sql;
    private Map<String, String> params;
    private String[] ignoreChildren;
    private String[] ignoreParams;


    public SQLRequest(String sql, Map<String, String> params, String[] ignoreChildren, String[] ignoreParams) {
        this.sql = sql;
        this.params = params;
        this.ignoreChildren = ignoreChildren;
        this.ignoreParams = ignoreParams;
    }

    public void setIgnoreChildren(String[] ignoreChildren) {
        this.ignoreChildren = ignoreChildren;
    }

    public String[] getIgnoreChildren() {
        return ignoreChildren;
    }

    public void setIgnoreParams(String[] ignoreParams) {
        this.ignoreParams = ignoreParams;
    }

    public String[] getIgnoreParams() {
        return ignoreParams;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
