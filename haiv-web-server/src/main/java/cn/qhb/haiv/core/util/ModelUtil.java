package cn.qhb.haiv.core.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体工具
 */
public class ModelUtil {

    /**
     * 字段包含类型
     */
    public enum FieldInType {
        /**
         * 包括
         */
        include,
        /**
         * 除掉
         */
        exclude
    }

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
     * @param excludeFileds
     */
    public static void modelMapperNewForExcludeFields(Object source, Object target, String... excludeFileds) throws Exception {
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
            if (excludeFileds != null && excludeFileds.length >= 1 && excludedField(setterM, excludeFileds)) {
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
     * 实体映射工具(将修改属性值映射到新对象中)
     *
     * @param source
     * @param target
     * @param excludeFileds
     */
    public static void modelMapperAlterForExcludeFields(Object source, Object target, String... excludeFileds) throws Exception {
        if (source == null || target == null) {
            return;
        }
        Class sourceClass = source.getClass();
        Class targetClass = target.getClass();
        Method[] sourceMethods = sourceClass.getMethods();
        Map<String, Object> setterTypes = new HashMap<>();
        Map<String, Object> setterVals = new HashMap<>();
        String excludedFieldStr = "";
        for (Method sourceM : sourceMethods) {
            String methodName = sourceM.getName();
            if (!methodName.startsWith("set") && !methodName.startsWith("get")) {
                continue;
            }
            boolean startWithSetter = methodName.startsWith("set");
            String filedName = methodName.trim().replaceFirst(startWithSetter ? "set" : "get", "");
            if (filedName.toLowerCase().equals(excludedFieldStr.toLowerCase()) || excludeFileds != null && excludeFileds.length >= 1 && excludedField(methodName, excludeFileds)) {
                excludedFieldStr = filedName;
                continue;
            }
            Object paramVal = targetClass.getMethod(startWithSetter ? methodName.replaceFirst("set", "get") : methodName).invoke(target);
            if (paramVal != null) {
                continue;
            }
            if (startWithSetter) {
                setterTypes.put(methodName, sourceM.getParameterTypes());
            } else {
                setterVals.put(methodName, sourceClass.getMethod(methodName).invoke(source));
            }
        }
        for (String setterM : setterTypes.keySet()) {
            targetClass.getMethod(setterM, (Class<?>[]) setterTypes.get(setterM))
                    .invoke(target, setterVals.get(setterM.replaceFirst("set", "get")));
        }
    }

    /**
     * 实体映射工具(将修改属性值映射到新对象中)
     *
     * @param source
     * @param target
     * @param includeFileds
     */
    public static void modelMapperAlterForIncludeFields(Object source, Object target, String... includeFileds) throws Exception {
        if (source == null || target == null) {
            return;
        }
        Class sourceClass = source.getClass();
        Class targetClass = target.getClass();
        Method[] sourceMethods = sourceClass.getMethods();
        Map<String, Object> setterTypes = new HashMap<>();
        Map<String, Object> setterVals = new HashMap<>();
        String excludedFieldStr = "";
        for (Method sourceM : sourceMethods) {
            String methodName = sourceM.getName();
            if (!methodName.startsWith("set") && !methodName.startsWith("get")) {
                continue;
            }
            boolean startWithSetter = methodName.startsWith("set");
            String filedName = methodName.trim().replaceFirst(startWithSetter ? "set" : "get", "");
            if (filedName.toLowerCase().equals(excludedFieldStr.toLowerCase()) || includeFileds != null && includeFileds.length >= 1 && includedField(methodName, includeFileds)) {
                excludedFieldStr = filedName;
                continue;
            }
            Object paramVal = targetClass.getMethod(startWithSetter ? methodName.replaceFirst("set", "get") : methodName).invoke(target);
            if (paramVal != null) {
                continue;
            }
            if (startWithSetter) {
                setterTypes.put(methodName, sourceM.getParameterTypes());
            } else {
                setterVals.put(methodName, sourceClass.getMethod(methodName).invoke(source));
            }
        }
        for (String setterM : setterTypes.keySet()) {
            targetClass.getMethod(setterM, (Class<?>[]) setterTypes.get(setterM))
                    .invoke(target, setterVals.get(setterM.replaceFirst("set", "get")));
        }
    }

}
