package com.example.backendcollect.utils.Converter;


import com.example.backendcollect.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Rikka
 * @date: 2022-02-26 23:37:52
 * @description 工厂方法模式, 返回一个转换器
 * 这个转换器的目的就是将 Spring Boot 不能自动转换的参数转换成后端的实体
 * 所以追求前后端的参数一致不是必要的. whatever
 */
@Component
public class StringToEnumConverterFactory implements ConverterFactory<String, BaseEnum> {
    private static final Map<Class, Converter> CONVERTER_MAP = new HashMap<>();

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter<String, T> converter = CONVERTER_MAP.get(targetType);
        if (converter == null) {
            converter = new IntergerToEnumConverter<>(targetType);
            CONVERTER_MAP.put(targetType, converter);
        }
        return converter;
    }

    private class IntergerToEnumConverter<T extends BaseEnum> implements Converter<String, T> {

        private final Map<String, T> enumMap = new HashMap<>();

//    public static void main(String[] args) {
//        Class<TaskType> taskTypeClass = TaskType.class;
//        IntergerToEnumConverter<TaskType> intergerToEnumConverter = new IntergerToEnumConverter(taskTypeClass);
//        intergerToEnumConverter.convert(1);
//    }

        /**
         * 将数字转换为参数指定的枚举类中的元素
         *
         * @param enumType 目标枚举类
         */
        public IntergerToEnumConverter(Class<T> enumType) {
            T[] enums = enumType.getEnumConstants();
            for (T e : enums) {
                enumMap.put(e.getCode().toString(), e);
            }
        }

        @Override
        public T convert(String source) {
            T t = enumMap.get(source);
            if (t == null) {
                throw new IllegalArgumentException();
            }
            return t;
        }
    }
}
