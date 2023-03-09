package com.mohan.utils;


import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * bean拷贝工具类
 */
public class BeanCopyUtils {

    private BeanCopyUtils(){

    }

    /**
     * 将原对象转换为目标对象
     * @param source  原对象
     * @param clazz  目标class对象
     * @return 目标对象
     * @param <V> 目标类型
     */
    public static <V> V copyBean(Object source,Class<V> clazz){
        // 创建目标对象
        try {
            V o = clazz.newInstance();
            // 实现属性拷贝
            BeanUtils.copyProperties(source,o);
            // 返回结果
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * list集合属性转换
     * @param list 原list集合
     * @param clazz 转换目标的class对象
     * @return 转换后的list集合
     * @param <V> 转换目标
     */
    public static <V> List<V> copyBeanList(List<?> list, Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
