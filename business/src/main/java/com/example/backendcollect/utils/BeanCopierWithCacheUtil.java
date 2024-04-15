package com.example.backendcollect.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author izhouy
 * @title BeanUtils
 * @Decription 对象转换工具类
 * @CreateDate 2021/6/29 14:00
 */

@Slf4j
public class BeanCopierWithCacheUtil {
    private static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIERS = new ConcurrentHashMap<>();

    /**
     * 将不用类型转化的copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false) 放入内存
     * 第一次初始化的时候将BeanCopier，初始到内存中，下次再次使用时。直接使用。
     *
     * @param srcObj
     * @param destObj
     */
    public static void copy(Object srcObj, Object destObj) {
        String key = genKey(srcObj.getClass(), destObj.getClass());
        BeanCopier copier;
        if (!BEAN_COPIERS.containsKey(key)) {
            copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);
            BEAN_COPIERS.put(key, copier);
        } else {
            copier = BEAN_COPIERS.get(key);
        }
        copier.copy(srcObj, destObj, null);
    }


    /**
     * 将转化的类的名字和被转化类型的名字作为key，保存到内存中
     *
     * @param srcClazz
     * @param destClazz
     * @return
     */
    private static String genKey(Class<?> srcClazz, Class<?> destClazz) {
        return srcClazz.getName() + destClazz.getName();
    }

}

