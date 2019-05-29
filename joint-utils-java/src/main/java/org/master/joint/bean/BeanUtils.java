package org.master.joint.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanUtils {
    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * @Author: Yifan
     * @Date: 2019/5/29 11:45
     * @Description: 拷贝bean的基本类型属性到map
     */
    public static void copyPojoProps(Map<String, Object> target, Object source) {
        List<Field> fields = new ArrayList<Field>();
        Class clazz = source.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }

        for (Field field : fields) {
            String type = field.getGenericType().toString();

            if (type.equals("class java.lang.String") || type.equals("boolean") || type.equals("char") || type.equals("int") || type.equals("float") || type.equals("double") || type.equals("byte")
                    || type.equals("short") || type.equals("long") || type.equals("class java.lang.Byte") || type.equals("class java.lang.Short") || type.equals("class java.lang.Integer")
                    || type.equals("class java.lang.Long") || type.equals("class java.lang.Float") || type.equals("class java.lang.Double") || type.equals("class java.lang.Character")
                    || type.equals("class java.lang.Boolean")) {

                String fileName = field.getName();
                Object fieldValue = null;
                field.setAccessible(true);

                try {
                    fieldValue = field.get(source);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }

                target.put(fileName, fieldValue);
                field.setAccessible(false);
            }
        }
    }

    /**
     * @Author: Yifan
     * @Date: 2019/5/29 11:46
     * @Description: 将List<Entity> 转换成 List<Map>
     */
    public static <E> List<Map<String, Object>> copyPojos2Maps(List<E> sources) {
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();

        for (Object source : sources) {
            Map<String, Object> warehouseDto = new HashMap<String, Object>();
            copyPojoProps(warehouseDto, source);

            maps.add(warehouseDto);
        }

        return maps;
    }

    /**
     * @Author: Yifan
     * @Date: 2019/5/29 11:46
     * @Description: 将List<Entity> 转换成 List<Dto>
     */
    public static <E, F> List<F> copyPojos2Dtos(List<E> sources, Class<F> dtoClazz) {
        List<F> dtos = new ArrayList<F>();

        for (E source : sources) {
            F dto = null;
            try {
                dto = dtoClazz.newInstance();
            } catch (InstantiationException e) {
                logger.error("{} 类是一个抽象类或接口, 无法实例化!", dtoClazz.getName());
                logger.error(e.getMessage(), e);
                return null;

            } catch (IllegalAccessException e) {
                logger.error("{} 类的构造方法是不是公有, 无法实例化!", dtoClazz.getName());
                logger.error(e.getMessage(), e);
                return null;
            }

            org.springframework.beans.BeanUtils.copyProperties(source, dto);

            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * @Author: Yifan
     * @Date: 2019/5/29 11:46
     * @Description: 将map 转为bean
     */
    public static <T> T map2Bean(Map<String, Object> map, Class<T> clz) throws Exception {
        //new 出一个对象
        T obj = clz.newInstance();
        // 获取person类的BeanInfo对象
        BeanInfo beanInfo = Introspector.getBeanInfo(clz);
        //获取属性描述器
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            //获取属性名
            String key = propertyDescriptor.getName();
            Object value = map.get(key);
            if (key.equals("class") || !map.containsKey(key)) {
                continue;
            }
            Method writeMethod = propertyDescriptor.getWriteMethod();
            //通过反射来调用Person的定义的setName()方法
            writeMethod.invoke(obj, value);
        }
        return obj;
    }

    /**
     * @Author: Yifan
     * @Date: 2019/5/29 11:46
     * @Description: entity之间拷贝属性时忽略baseEntity中的属性
     */
    public static void copyExclude(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, new String[]{"id", "version", "createdBy", "createdDate", "lastUpdatedBy", "lastUpdatedDate", "organizationId"});
    }

    /**
     * @Author: Yifan
     * @Date: 2019/5/29 11:32
     * @Description: 获取Null元素名称
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
