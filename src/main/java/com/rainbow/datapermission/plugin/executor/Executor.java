package com.rainbow.datapermission.plugin.executor;

import com.rainbow.datapermission.plugin.object.ConstraintTypeObject;

import java.util.List;
import java.util.Map;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
@FunctionalInterface
public interface Executor {
    Map<String/*<constraintType>*/, Map<String/*fieldName*/,
            List<ConstraintTypeObject>/*value*/>> execute(String tableName, Map<String, String> params);
}
