package com.rainbow.datapermission.plugin.example;

import com.rainbow.datapermission.plugin.concat.SQLConcat;
import com.rainbow.datapermission.plugin.enums.SQLTypeEnum;
import com.rainbow.datapermission.plugin.object.ConcatBodyObject;
import com.rainbow.datapermission.plugin.object.ConstraintTypeObject;
import com.rainbow.datapermission.plugin.utils.CollectionUtils;
import com.rainbow.datapermission.plugin.utils.FormatUtil;
import com.rainbow.datapermission.plugin.utils.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.rainbow.datapermission.plugin.utils.SQLUtil.*;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public class ListSQLConcat implements SQLConcat {
    @Override
    public ConcatBodyObject concat(List<ConstraintTypeObject> constraintTypeObjectList, String tableAlias) {
        String resultSql = EMPTY_STRING;
        if (CollectionUtils.isEmpty(constraintTypeObjectList)) {
            return null;
        }

        ConstraintTypeObject copy = constraintTypeObjectList.get(0);
        String fieldName = copy.getFieldName();
        String fieldType = copy.getFieldType();

        final Set<String> valuesSet = new HashSet<>();

        constraintTypeObjectList.parallelStream()
                .filter(constraintTypeObject -> !StringUtils.isEmpty(constraintTypeObject.getConstraintValue().getValue()))
                .map(constraintTypeObject -> FormatUtil.replaceChineseComma((String)
                        constraintTypeObject.getConstraintValue().getValue()).split(COMMA))
                .forEach(valueArray -> Collections.addAll(valuesSet, valueArray));

        String suffixSql = valuesSet.parallelStream().map(value ->
                (OR + (!StringUtils.isEmpty(tableAlias) ? tableAlias + ALIAS_DOT : EMPTY_STRING)
                        + fieldName + EQUALS + (SQLTypeEnum.isStringSource(fieldType)
                        ? LEFT_SINGLE_QUOTE + value + RIGHT_SINGLE_QUOTE : value)))
                .reduce((v1, v2) -> v1 + v2)
                .orElse(EMPTY_STRING);

        resultSql = resultSql.concat(suffixSql);

        if (resultSql.contains(OR)) {
            resultSql = resultSql.substring(2 + 2);
        }
        resultSql = LEFT_BRACKET + resultSql + RIGHT_BRACKET;
        return new ConcatBodyObject(tableAlias, resultSql, fieldName);
    }
}
