package com.yoyodev.springblog.common.enumerations.converter;

import com.yoyodev.springblog.common.enumerations.TransformableEnum;
import com.yoyodev.springblog.common.exception.InvalidStoredEnumValueException;

import java.util.Arrays;

public class EnumConverter {
    public static <T, E extends Enum<E> & TransformableEnum<T>> E convert(T value, Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(enumValue -> enumValue.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new InvalidStoredEnumValueException("Invalid value for enum class: " + enumClass.getSimpleName() + ", value: " + value));
    }
}
