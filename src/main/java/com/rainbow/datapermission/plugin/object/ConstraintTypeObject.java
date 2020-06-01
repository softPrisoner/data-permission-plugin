package com.rainbow.datapermission.plugin.object;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public class ConstraintTypeObject extends SQLObject {
    /**
     * Constraint Type
     */
    private String constraintType;

    /**
     * Constraint Value
     */
    private Value<?> constraintValue;

    public ConstraintTypeObject(Value<?> constraintValue) {
        this.constraintValue = constraintValue;
    }

    public void setConstraintValue(Value<?> constraintValue) {
        this.constraintValue = constraintValue;
    }

    public Value<?> getConstraintValue() {
        return constraintValue;
    }

    public void setConstraintType(String constraintType) {
        this.constraintType = constraintType;
    }

    public String getConstraintType() {
        return constraintType;
    }
}
