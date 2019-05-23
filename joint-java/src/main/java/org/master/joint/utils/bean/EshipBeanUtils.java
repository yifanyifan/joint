package org.master.joint.utils.bean;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EshipBeanUtils {
    private static Logger logger = LoggerFactory.getLogger(EshipBeanUtils.class);

    /**
     * 只拷贝bean的基本类型属性到map
     * 
     * @param target
     * @param source
     */
    public static void copyPojoProps(Map<String, Object> target, Object source) {
        List<Field> fields = Lists.newArrayList();
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
     * 将List<Entity> 转换成 List<Map>, 供Dubbo通讯
     * 
     * @author LJ
     * @param sources
     * @return
     */
    public static <E> List<Map<String, Object>> copyPojos2Maps(List<E> sources) {
        List<Map<String, Object>> maps = Lists.newArrayList();

        for (Object source : sources) {
            Map<String, Object> warehouseDto = Maps.newHashMap();
            copyPojoProps(warehouseDto, source);

            maps.add(warehouseDto);
        }

        return maps;
    }

    /**
     * 将List<Entity> 转换成 List<Dto> , 供Dubbo通讯
     * 
     * @author LJ
     * @param sources
     * @param dtoClazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <E, F> List<F> copyPojos2Dtos(List<E> sources, Class<F> dtoClazz) {
        List<F> dtos = Lists.newArrayList();

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

            BeanUtils.copyProperties(source, dto);

            dtos.add(dto);
        }

        return dtos;
    }
    /**
    * @Description: 将map 转为bean
    * @param:
    * @Date: 15:09 2017/9/12
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
            if (key.equals("class") || !map.containsKey(key) ) {
                continue;
            }
            Method writeMethod = propertyDescriptor.getWriteMethod();
            //通过反射来调用Person的定义的setName()方法
            writeMethod.invoke(obj, value);
        }
        return obj;
    }

    /**
    * @Author: HuKai
    * @Date: 2017/12/26 15:07
    * @Description: entity之间拷贝属性时忽略baseEntity中的属性
    */
    public static void copyExclude(Object source, Object target) {
        BeanUtils.copyProperties(source, target, new String[]{"id", "version", "createdBy", "createdDate", "lastUpdatedBy", "lastUpdatedDate", "organizationId"});
    }

    /**
     * 判断高低估值 TuShiDing
     * @param price
     * @return
     */
    public static String getAppraisement(float coefficientXp,float coefficientValue,float price){
        if(price * 0.24 >=15){
            return "H";
        }else{
            return "L";
        }
    }

}
