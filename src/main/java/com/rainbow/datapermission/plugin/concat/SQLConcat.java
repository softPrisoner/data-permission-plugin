package com.rainbow.datapermission.plugin.concat;

import com.rainbow.datapermission.plugin.object.ConcatBodyObject;
import com.rainbow.datapermission.plugin.object.ConstraintTypeObject;

import java.util.List;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public interface SQLConcat {

    ConcatBodyObject concat(List<ConstraintTypeObject> constraintTypeObjectList, String tableAlias);
}
