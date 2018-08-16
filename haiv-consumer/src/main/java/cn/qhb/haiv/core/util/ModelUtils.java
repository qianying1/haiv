package cn.qhb.haiv.core.util;

import cn.qhb.haiv.model.Device;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体工具
 */
public class ModelUtils {
    private static Logger logger = LoggerFactory.getLogger(ModelUtils.class);

    /**
     * 实体映射工具(将属性值映射到新对象中)
     *
     * @param source
     * @param target
     * @param includeFileds
     */
    public static void modelMapperNewForIncludeFields(Object source, Object target, String... includeFileds) throws Exception {
        if (source == null || target == null) {
            return;
        }
        Class sourceClass = source.getClass();
        Class targetClass = target.getClass();
        Method[] sourceMethods = sourceClass.getMethods();
        Map<String, Object> setterTypes = new HashMap<>();
        Map<String, Object> setterVals = new HashMap<>();
        initSetterTypeAndVals(sourceClass, source, sourceMethods, setterTypes, setterVals);
        /*for (Method sourceM : sourceMethods) {
            String methodName = sourceM.getName();
            if (!methodName.startsWith("set") && !methodName.startsWith("get")) {
                continue;
            }
            if (methodName.startsWith("set")) {
                setterTypes.put(methodName, sourceM.getParameterTypes());
            } else {
                setterVals.put(methodName, sourceClass.getMethod(methodName).invoke(source));
            }
        }*/
        for (String setterM : setterTypes.keySet()) {
            if (includeFileds != null && includeFileds.length >= 1 && !includedField(setterM, includeFileds)) {
                continue;
            }
            targetClass.getMethod(setterM, (Class<?>[]) setterTypes.get(setterM))
                    .invoke(target, setterVals.get(setterM.replaceFirst("set", "get")));
        }
    }

    /**
     * 实体映射工具(将属性值映射到新对象中)
     *
     * @param source
     * @param target
     * @param excludeFields
     */
    public static void modelMapperNewForExcludeFields(Object source, Object target, String... excludeFields) throws Exception {
        if (source == null || target == null) {
            return;
        }
        Class sourceClass = source.getClass();
        Class targetClass = target.getClass();
        Method[] sourceMethods = sourceClass.getMethods();
        Map<String, Object> setterTypes = new HashMap<>();
        Map<String, Object> setterVals = new HashMap<>();
        initSetterTypeAndVals(sourceClass, source, sourceMethods, setterTypes, setterVals);
        for (String setterM : setterTypes.keySet()) {
            if (excludeFields != null && excludeFields.length >= 1 && excludedField(setterM, excludeFields)) {
                continue;
            }
            targetClass.getMethod(setterM, (Class<?>[]) setterTypes.get(setterM))
                    .invoke(target, setterVals.get(setterM.replaceFirst("set", "get")));
        }
    }

    /**
     * 初始化
     *
     * @param sourceClass
     * @param source
     * @param sourceMethods
     * @param setterTypes
     * @param setterVals
     * @throws Exception
     */
    private static void initSetterTypeAndVals(Class sourceClass, Object source, Method[] sourceMethods, Map<String, Object> setterTypes, Map<String, Object> setterVals) throws Exception {
        for (Method sourceM : sourceMethods) {
            String methodName = sourceM.getName();
            if (!methodName.startsWith("set") && !methodName.startsWith("get")) {
                continue;
            }
            if (methodName.startsWith("set")) {
                setterTypes.put(methodName, sourceM.getParameterTypes());
            } else {
                setterVals.put(methodName, sourceClass.getMethod(methodName).invoke(source));
            }
        }
    }

    /**
     * 判断是否是已排除的字段
     *
     * @param methodName
     * @param excludeFields
     * @return
     */
    private static boolean excludedField(String methodName, String... excludeFields) {
        boolean excluded = false;
        for (String excludeField : excludeFields) {
            if (excludeField.trim().toLowerCase().equals(
                    methodName.trim().replaceFirst(methodName.startsWith("set") ? "set" : "get", "").toLowerCase())) {
                excluded = true;
                break;
            }
        }
        return excluded;
    }

    /**
     * 判断是否是已排除的字段
     *
     * @param methodName
     * @param includeFields
     * @return
     */
    private static boolean includedField(String methodName, String... includeFields) {
        boolean included = false;
        for (String includeField : includeFields) {
            if (includeField.trim().toLowerCase().equals(
                    methodName.trim().replaceFirst(methodName.startsWith("set") ? "set" : "get", "").toLowerCase())) {
                included = true;
                break;
            }
        }
        return included;
    }

    /**
     * 获取一个类中的setter方法参数类型
     *
     * @param clazz
     * @return
     */
    public static Map<String, Class<?>[]> forClassMethodsParameterTypes(Class<?> clazz) {
        Map<String, Class<?>[]> parameterTypesMap = new HashMap<>();
        if (clazz == null) {
            return null;
        }
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (!method.getName().startsWith("set")) {
                continue;
            }
            parameterTypesMap.put(method.getName().replaceFirst("set", "").toLowerCase(), method.getParameterTypes());
        }
        return parameterTypesMap;
    }

    /**
     * 获取类中setter方法
     *
     * @param clazz
     * @param name
     * @param parameterTypes
     */
    public static Method getSetterMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        return clazz.getMethod("set" + name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase()), parameterTypes);
    }

    /**
     * 寻找合适的类型进行转换（在excel文档未经过format处理时有效）
     *
     * @param sourceClazz
     * @param cell
     * @param formatTarget
     * @return
     */
    public static Object forValueNotDataFormat(Class<?> sourceClazz, Cell cell, Object formatTarget) throws Exception {
        Object val = null;
        DecimalFormat df = new DecimalFormat("0");
        if (sourceClazz.isEnum()) {
            for (Object obj : sourceClazz.getEnumConstants()) {
                Enum en = (Enum) obj;
                if (en.toString().equalsIgnoreCase(formatTarget.toString())) {
                    val = en;
                    break;
                }
            }
        } else if (sourceClazz == java.util.Date.class) {
            if (Utils.isLinuxOS()) {
                logger.info("linux date type parsing >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                val = DateUtils.formatStringToDateLinux(cell.toString());
            } else {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                val = format.parse(cell.getStringCellValue());
            }
        } else if (sourceClazz == java.lang.Integer.class) {
            val = ((Double) cell.getNumericCellValue()).intValue();
        } else if (sourceClazz == java.lang.Long.class) {
            val = ((Double) cell.getNumericCellValue()).longValue();
        } else {
            Class cellC = cell.getClass();
            System.out.println(cellC);
            if ((cellC == HSSFCell.class && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) || (cellC == XSSFCell.class && cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)) {
                val = String.valueOf(df.format(cell.getNumericCellValue()));
            } else {
                val = sourceClazz.cast(formatTarget);
            }
        }
        return val;
    }

    /**
     * 字段是否为枚举类型
     *
     * @return
     */
    public static boolean isEnum(@NotNull Class<?> clazz, @NotNull String field) throws Exception {
        return clazz.getDeclaredField(field).getType().isEnum();
    }

    /**
     * 获取枚举类型内的可选值数组
     *
     * @param clazz
     * @param field
     * @return
     */
    public static String[] getEnumArray(@NotNull Class<?> clazz, @NotNull String field) throws Exception {
        if (!isEnum(clazz, field)) {
            return null;
        }
        Class<?> fieldType = clazz.getDeclaredField(field).getType();
        Object entity = clazz.newInstance();
        Object[] vals = (Object[]) fieldType.getMethod("values").invoke(entity);
        String[] result = new String[vals.length];
        for (int i = 0; i < vals.length; i++) {
            result[i] = vals[i].toString();
        }
        return result;
    }

    /**
     * 寻找合适的类型进行转换（在excel文档经过format处理之后有效）
     *
     * @param sourceClazz
     * @param cell
     * @param formatTarget
     * @return
     */
    public static Object forValueAfterDataFormat(Class<?> sourceClazz, Cell cell, Object formatTarget) throws Exception {
        Object val = null;
        DecimalFormat df = new DecimalFormat("0");
        if (sourceClazz.isEnum()) {
            for (Object obj : sourceClazz.getEnumConstants()) {
                Enum en = (Enum) obj;
                if (en.toString().equalsIgnoreCase(formatTarget.toString())) {
                    val = en;
                    break;
                }
            }
        } else if (sourceClazz == java.util.Date.class) {
            if (Utils.isLinuxOS()) {
                val = DateUtils.formatStringToDateLinux(cell.getStringCellValue());
            } else {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                val = format.parse(cell.getStringCellValue());
            }
        } else if (sourceClazz == java.lang.Integer.class) {
            val = Integer.valueOf(cell.getStringCellValue());
        } else if (sourceClazz == java.lang.Long.class) {
            val = Long.valueOf(cell.getStringCellValue());
        } else {
            Class cellC = cell.getClass();
            if ((cellC == HSSFCell.class && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) || (cellC == XSSFCell.class && cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)) {
                val = String.valueOf(df.format(cell.getStringCellValue()));
            } else {
                val = sourceClazz.cast(formatTarget);
            }
        }
        return val;
    }

    /**
     * 按照给出的字段获取对应实体的数据并以列表的形式放回
     *
     * @param clazz
     * @param entity
     * @param fields
     * @return
     */
    public static List<?> getModelValues(@NotNull Class<?> clazz, @NotNull Object entity, @NotNull String... fields) throws Exception {
        List<Object> result = new ArrayList<>();
        for (String field : fields) {
            Object obj = clazz.getMethod("get" + field.replaceFirst(field.substring(0, 1), field.substring(0, 1).toUpperCase())).invoke(entity);
            result.add(obj);
        }
        return result;
    }

    /**
     * 按照类属性类型返回对应字段类型的对象
     *
     * @param clazz
     * @param field
     * @param entity
     * @return
     */
    public static Object getTypeOfValue(Class<?> clazz, String field, Object entity) throws Exception{
       /* Field targetField=clazz.getDeclaredField(field);
        if (targetField==null){
            return null;
        }
        Type targetType=targetField.getGenericType();
        System.out.println(targetType.toString());*/
        return clazz.getMethod("get" + field.replaceFirst(field.substring(0, 1), field.substring(0, 1).toUpperCase())).invoke(entity);
    }

    public static void main(String... args) throws Exception {
        getEnumArray(Device.class, "status");
    }
}
