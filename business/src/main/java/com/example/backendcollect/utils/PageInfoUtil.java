package com.example.backendcollect.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PageInfoUtil {

    /**
     * 将PageInfo对象泛型中的PO对象转化为VO对象后返回
     *
     * @param <P> PO类型
     * @param <V> VO类型
     */
    public static <P, V> PageInfo<V> convert(PageInfo<P> pageInfoPO, Class<V> vClass) {
        // 创建Page对象，实际上是一个ArrayList类型的集合
        Page<V> page = new Page<>(pageInfoPO.getPageNum(), pageInfoPO.getPageSize());
        page.setTotal(pageInfoPO.getTotal());
        // TODO 等会儿加个缓存吧(存HashMap), 一直反射蛮慢的
        try {
            if (pageInfoPO.getSize() > 0) {
                Constructor<V> constructor = vClass.getConstructor(pageInfoPO.getList().get(0).getClass());
                for (P p : pageInfoPO.getList()) {
                    V v = null;
                    try {
                        v = constructor.newInstance(p);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    page.add(v);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return new PageInfo<>(page);
    }
}
