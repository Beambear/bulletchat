package com.bulletchat.util;

import com.bulletchat.code.StatusCode;
import com.bulletchat.exception.ConditionException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import java.beans.PropertyDescriptor;
import java.util.stream.Stream;

public class BeanPropertyUtil {
    //私有的构造方法，确保该工具类不能被实例化
    private BeanPropertyUtil(){}

    /**
     * 将源对象的非null属性复制到目标对象中。
     *
     * @param source 源对象。
     * @param target 目标对象。
     */
    public static void copyProperties(Object source, Object target){
        // 获取源对象中所有为null的属性名
        String[] nullPropertyNames = getNullPropertyNames(source);
        // 使用Spring的BeanUtils工具类将非null属性从源对象复制到目标对象
        BeanUtils.copyProperties(source, target, nullPropertyNames);
        if(!isPropertiesMatch(source,target)) throw new ConditionException(StatusCode.UPDATE_FAILED.getCode(),StatusCode.UPDATE_FAILED.getInfo());
    }

    /**
     * 工具方法，用于获取对象中所有为null的属性的名称。
     *
     * @param source 要检查的对象。
     * @return 为null的属性的名称数组。
     */
    public static String[] getNullPropertyNames(Object source){
        // 使用Spring的BeanWrapper将对象包装，方便后续对属性的检查
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                // 将每个属性描述符映射到其属性名称
                .map(PropertyDescriptor::getName)
                // 过滤出值为null的属性
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                // 将属性名称的流转换为数组
                .toArray(String[]::new);
    }

    /**
     * 比较两个对象的非null属性是否相等。
     *
     * @param source 源对象。
     * @param target 目标对象。
     * @return 如果所有非null属性相等，则返回true，否则返回false。
     */
    public static boolean isPropertiesMatch(Object source, Object target) {
        // 使用BeanWrapper对两个对象进行包装
        BeanWrapper sourceWrapper = new BeanWrapperImpl(source);
        BeanWrapper targetWrapper = new BeanWrapperImpl(target);

        // 对源对象的每一个属性进行比较
        return Stream.of(sourceWrapper.getPropertyDescriptors())
                // 检查每个属性是否相等
                .allMatch(descriptor -> comparePropertyValue(descriptor, sourceWrapper, targetWrapper));
    }

    /**
     * 对比两个对象的一个属性是否相等。
     *
     * @param descriptor 属性描述符。
     * @param sourceWrapper 源对象的包装器。
     * @param targetWrapper 目标对象的包装器。
     * @return 如果属性相等，则返回true，否则返回false。
     */
    private static boolean comparePropertyValue(PropertyDescriptor descriptor, BeanWrapper sourceWrapper, BeanWrapper targetWrapper) {
        // 获取属性名称
        String propertyName = descriptor.getName();
        // 使用BeanWrapper获取源对象的属性值
        Object sourceValue = sourceWrapper.getPropertyValue(propertyName);

        // 如果源值为null，则跳过比较，因为我们只关心非null值
        if (sourceValue == null) {
            return true;
        }

        // 使用BeanWrapper获取目标对象的属性值
        Object targetValue = targetWrapper.getPropertyValue(propertyName);

        // 使用equals方法比较两个属性值是否相等
        return sourceValue.equals(targetValue);
    }
}
