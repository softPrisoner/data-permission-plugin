package com.rainbow.datapermission.plugin.example.enums;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 * @description
 */
public enum ContinuousOpsEnum {
    /**
     * 大于
     */
    GT(">", "大于"),
    /**
     * 小于
     */
    LT("<", "小于"),
    /**
     * 大于等于
     */
    GE(">=", "大于等于"),
    /**
     * 小于等于
     */
    LE("<=", "小于等于"),
    /**
     * 不等于
     */
    NEQ("!=", "不等于");

    /**
     * 值
     */
    public String value;

    /**
     * 描述信息
     */
    public String description;

    ContinuousOpsEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
}
