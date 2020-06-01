package com.rainbow.datapermission.plugin.example;

import com.rainbow.datapermission.plugin.executor.Executor;
import com.rainbow.datapermission.plugin.object.ConstraintTypeObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 * @description
 */
public class MyExecutor implements Executor {
    @Override
    public Map<String/*<constraintType>*/, Map<String/*fieldName*/, List<ConstraintTypeObject>/*value*/>> execute(String tableName, Map<String, String> params) {
        Map<String/*<constraintType>*/, Map<String/*fieldName*/, List<ConstraintTypeObject>/*value*/>> resultMap = new HashMap<>();
        HashMap<String, List<ConstraintTypeObject>> constraintTypeObjectMap = new HashMap<>(16);
        ListValue<String> value = new ListValue<String>();
        value.setValue("1,2,3");

        ConstraintTypeObject object = new ConstraintTypeObject(value);
        object.setConstraintType("LIST");

        object.setFieldName("id");
        object.setFieldType("int");
        List<ConstraintTypeObject> list = new ArrayList<>();
        list.add(object);
        constraintTypeObjectMap.put("id", list);
        resultMap.put("LIST", constraintTypeObjectMap);
        return resultMap;
    }
}
