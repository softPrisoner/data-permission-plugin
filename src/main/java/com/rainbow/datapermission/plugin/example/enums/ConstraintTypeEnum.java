package com.rainbow.datapermission.plugin.example.enums;

import com.alibaba.druid.util.StringUtils;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 * @description
 */
public enum ConstraintTypeEnum {
    /**
     *
     */
    LIST("LIST", "列表"),
    /**
     *
     */
    TREE("TREE", "树状结构"),
    /**
     *
     */
    CONTINUOUS("CONTINUOUS", "连续值");
    /**
     *
     */
    public String name;
    /**
     *
     */
    public String description;

    ConstraintTypeEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static String transferToName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        for (ConstraintTypeEnum type : ConstraintTypeEnum.values()) {
            if (type.name.equals(name)) {
                return type.description;
            }
        }
        return null;
    }

    public static ConstraintTypeEnum transfer2Enum(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        for (ConstraintTypeEnum type : ConstraintTypeEnum.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }

}
