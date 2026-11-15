package com.yoyodev.springblog.common.enumerations;

public enum PermissionLevel implements TransformableEnum<Integer> {
    None(0),
    Read(1),
    Read_Write(2);
    private final int value;

    PermissionLevel(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
