package cn.qhb.haiv.core.mapper_potting;

import cn.qhb.haiv.core.annotation.Column;
import cn.qhb.haiv.core.annotation.Id;
import cn.qhb.haiv.core.annotation.Table;
import cn.qhb.haiv.core.annotation.Transition;
import cn.qhb.haiv.core.exception.ListValueNotFoundException;
import cn.qhb.haiv.core.util.ApplicationContextUtil;
import cn.qhb.haiv.core.util.Constant;
import cn.qhb.haiv.core.util.SqlFilter;
import cn.qhb.haiv.core.util.SqlTemplateEntity;
import cn.qhb.haiv.persistence.SqlTemplate;
import org.apache.ibatis.annotations.ResultMap;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础映射
 */
public class MapperHelper<T> {
    private static Logger logger = LoggerFactory.getLogger(MapperHelper.class);
    /**
     * 实体类名称
     */
    private String entityName;

    /**
     * 字段
     */
    private String fields;
    /**
     * 值列表
     */
    private String vals;

    /**
     * 键值对映射
     */
    private Map<Object, Object> keyVals;

    /**
     * sql模板文件路径
     */
    @Value("${custom.sql_template}")
    private String templatePath;
    /**
     * sql会话模板
     */
    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * sql映射模板
     */
//    private SqlTemplateProvider<T> sqlTemplate;

    /**
     * 当前实例
     */
    private Class clazz;

    /**
     * 默认构造方法，为当前实例赋予类型
     */
    public MapperHelper() throws Exception{
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class) type.getActualTypeArguments()[0];
        initTable();
        initFields();
    }

    /**
     * 插入
     *
     * @param entity
     * @return
     * @throws Exception
     */
    public int insert(T entity) throws Exception {
        initFieldsAndVals(entity);
        SqlTemplateEntity templateEntity = new SqlTemplateEntity(table(), this.fields, this.vals, entity);
        return sqlSessionTemplate.insert(templatePath + ".insert", templateEntity);
    }

    /**
     * 批量插入数据
     *
     * @param entities
     * @return
     * @throws Exception
     */
    public int inserts(List<T> entities) throws Exception {
        initFieldsAndVals(entities.get(0));
        SqlTemplateEntity templateEntity = new SqlTemplateEntity(table(), this.fields, this.vals, entities);
        return sqlSessionTemplate.insert(templatePath + ".inserts", templateEntity);
    }

    /**
     * 通过条件更新数据
     *
     * @param entity  更新对象
     * @param filters 过滤条件
     * @return
     * @throws Exception
     */
    public int updateByParams(T entity, List<SqlFilter> filters) throws Exception {
        initFieldsAndValsToMap(entity);
        SqlTemplateEntity templateEntity = new SqlTemplateEntity(table(), this.keyVals, filters, entity);
        return sqlSessionTemplate.update(templatePath + ".updateByParams", templateEntity);
    }

    /**
     * 通过id更新数据
     *
     * @param entity
     * @return
     */
    public int updateById(T entity) {
        SqlTemplateEntity templateEntity = new SqlTemplateEntity(table(), this.keyVals, entity);
        return sqlSessionTemplate.update(templatePath + ".updateById", templateEntity);
    }

    /**
     * 通过id查找数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public T selectOneById(Long id) throws Exception {
        SqlTemplateEntity templateEntity = new SqlTemplateEntity(table(), id);
        return sqlSessionTemplate.selectOne(templatePath + ".selectOneById", templateEntity);
    }

    /**
     * 通过条件查询数据
     *
     * @param filters
     * @return
     * @throws Exception
     */
    public List<T> selectListByParams(List<SqlFilter> filters) throws Exception {
        SqlTemplateEntity templateEntity = new SqlTemplateEntity(table(), filters);
        return sqlSessionTemplate.selectList(templatePath + ".selectByParams", templateEntity);
    }

    /**
     * 分页获取数据
     *
     * @param start
     * @param offset
     * @return
     * @throws Exception
     */
    public List<T> selectPage(int start, int offset) throws Exception {
        initFields();
        String table = table();
        alterResultPath(getResultPath(table), "selectPage", String.class, String.class, Integer.class, Integer.class);
        SqlTemplateEntity templateEntity = new SqlTemplateEntity(table, this.fields, start, offset);
        List<T> result = sqlSessionTemplate.selectList(templatePath + ".selectPage", templateEntity);
        System.out.println(result);
        return result;
    }

    /**
     * 条件分页获取数据
     *
     * @param start
     * @param offset
     * @return
     * @throws Exception
     */
    public List<T> selectPageByParams(List<SqlFilter> filters, int start, int offset) throws Exception {
        initFields();
        SqlTemplateEntity templateEntity = new SqlTemplateEntity(table(), this.fields, filters, start, offset);
        return sqlSessionTemplate.selectList(templatePath + ".selectPageByParams", templateEntity);
    }

    /**
     * 条件删除数据
     *
     * @param filtersArr
     * @return
     * @throws Exception
     */
    public int deleteByParamsArray(SqlFilter... filtersArr) throws Exception {
        SqlTemplateEntity templateEntity = new SqlTemplateEntity(table(), filtersArr);
        return sqlSessionTemplate.delete(templatePath + ".deleteByParamsArray", templateEntity);
    }

    /**
     * 条件删除数据
     *
     * @param filters
     * @return
     * @throws Exception
     */
    public int deleteByParamsList(List<SqlFilter> filters) throws Exception {
        SqlTemplateEntity templateEntity = new SqlTemplateEntity(table(), filters);
        return sqlSessionTemplate.delete(templatePath + ".deleteByParamsList", templateEntity);
    }

    /**
     * 通过id删除数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteById(Long id) throws Exception {
        SqlTemplateEntity sqlTemplateEntity = new SqlTemplateEntity(table(), id);
        return sqlSessionTemplate.delete(templatePath + ".deleteById", sqlTemplateEntity);
    }

    /**
     * 计算数据量
     *
     * @return
     */
    public int count() throws Exception {
        String table = table();
        alterResultPath(getResultPath(table), "count");
        SqlTemplateEntity templateEntity = new SqlTemplateEntity(table);
        return sqlSessionTemplate.selectOne(templatePath + ".selectPage", templateEntity);
    }

    /**
     * 初始化dao层参数
     */
    private void initTable() {
        /**
         * dao层路径
         */
        this.entityName = this.clazz.getName().substring(this.clazz.getName().lastIndexOf(".") + 1, this.clazz.getName().length());
        if (this.entityName == null || this.entityName.trim().equals(""))
            logger.error("获取类名称出错");
    }

    /**
     * 获取表名称
     *
     * @return
     */
    private String table() {
        Annotation annotation = this.clazz.getDeclaredAnnotation(Table.class);
        if (annotation != null) {
            Table table = (Table) annotation;
            if (!table.value().trim().equals(""))
                return table.value();
        }
        if (this.entityName == null || this.entityName.trim().equals(""))
            initTable();
        return this.entityName.toLowerCase();
    }

    /**
     * 将字段与映射值塞到Map映射中
     *
     * @param entity
     * @throws Exception
     */
    private void initFieldsAndValsToMap(T entity) throws Exception {
        if (this.clazz == null) {
            logger.error("获取对应实体类型出错！");
        }
        Map<Object, Object> keyValMap = new HashMap<Object, Object>();
        Field[] fields = this.clazz.getDeclaredFields();
        Class superClazz = this.clazz.getSuperclass();
        getFieldsStrAndInitValsToMap(keyValMap, entity, fields);
        while (superClazz != null) {
            Field[] superFields = superClazz.getDeclaredFields();
            getFieldsStrAndInitValsToMap(keyValMap, entity, superFields);
            superClazz = superClazz.getSuperclass();
        }
        this.keyVals = keyValMap;
    }

    /**
     * 将字段与对应值映射到map映射中
     *
     * @param keyValMap
     * @param fields
     * @throws Exception
     */
    private void getFieldsStrAndInitValsToMap(Map<Object, Object> keyValMap, T entity, Field... fields) throws Exception {
        for (Field field : fields) {
            if (field == null)
                continue;
            Annotation annotation = field.getAnnotation(Column.class);
            Annotation annotation1 = field.getAnnotation(Id.class);
            Annotation annotation2 = field.getAnnotation(Transition.class);
            if (annotation2 != null || annotation1 != null) {
                continue;
            }
            Object val = entity.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase()
                    + field.getName().substring(1, field.getName().length())).invoke(entity);
            if (ObjectUtils.isEmpty(val)) {
                continue;
            }
            if (annotation != null && annotation1 == null) {
                Column column = (Column) annotation;
                keyValMap.put(column.name(), val);
            } else {
                keyValMap.put(field.getName(), val);
            }
        }
    }

    /**
     * 获取字段列及字段值
     *
     * @return
     * @throws Exception
     */
    private void initFieldsAndVals(T entity) throws Exception {
        if (this.clazz == null) {
            logger.error("获取对应实体类型出错！");
        }
        StringBuffer buffer = new StringBuffer();
        StringBuffer valBuffer = new StringBuffer();
        Field[] fields = this.clazz.getDeclaredFields();
        Class superClazz = this.clazz.getSuperclass();
        getFieldsStrAndInitVals(buffer, valBuffer, entity, fields);
        while (superClazz != null) {
            Field[] superFields = superClazz.getDeclaredFields();
            getFieldsStrAndInitVals(buffer, valBuffer, entity, superFields);
            superClazz = superClazz.getSuperclass();
        }
        buffer.replace(buffer.lastIndexOf(","), buffer.length(), "");
        valBuffer.replace(valBuffer.lastIndexOf(","), valBuffer.length(), "");
        this.fields = buffer.toString();
        this.vals = valBuffer.toString();
    }

    /**
     * 获取字段列
     *
     * @return
     * @throws Exception
     */
    private void initFields() throws Exception {
        if (this.clazz == null) {
            logger.error("获取对应实体类型出错！");
        }
        StringBuffer buffer = new StringBuffer();
        Field[] fields = this.clazz.getDeclaredFields();
        Class superClazz = this.clazz.getSuperclass();
        getFieldsStr(buffer, fields);
        while (superClazz != null) {
            Field[] superFields = superClazz.getDeclaredFields();
            getFieldsStr(buffer, superFields);
            superClazz = superClazz.getSuperclass();
        }
        buffer.replace(buffer.lastIndexOf(","), buffer.length(), "");
        this.fields = buffer.toString();
    }

    /**
     * 拼接字段串
     *
     * @param buffer
     * @param fields
     * @throws Exception
     */
    private void getFieldsStr(StringBuffer buffer, Field... fields) throws Exception {
        for (Field field : fields) {
            if (field == null)
                continue;
            Annotation annotation = field.getAnnotation(Column.class);
            Annotation annotation1 = field.getAnnotation(Id.class);
            Annotation annotation2 = field.getAnnotation(Transition.class);
            if (annotation2 != null || annotation1 != null) {
                continue;
            }
            if (annotation != null && annotation1 == null) {
                Column column = (Column) annotation;
                buffer.append(column.name());
            } else {
                buffer.append(field.getName());
            }
            buffer.append(",");
        }
    }

    /**
     * 拼接字段串和值串
     *
     * @param buffer
     * @param fields
     * @throws Exception
     */
    private void getFieldsStrAndInitVals(StringBuffer buffer, StringBuffer valBuffer, T entity, Field... fields) throws Exception {
        for (Field field : fields) {
            if (field == null)
                continue;
            Annotation annotation = field.getAnnotation(Column.class);
            Annotation annotation1 = field.getAnnotation(Id.class);
            Annotation annotation2 = field.getAnnotation(Transition.class);
            if (annotation2 != null || annotation1 != null) {
                continue;
            }
            Object val = entity.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase()
                    + field.getName().substring(1, field.getName().length())).invoke(entity);
            if (ObjectUtils.isEmpty(val)) {
                continue;
            }
            valBuffer.append("#{entity.");
            valBuffer.append(field.getName());
            valBuffer.append("}");
            if (annotation != null && annotation1 == null) {
                Column column = (Column) annotation;
                buffer.append(column.name());
            } else {
                buffer.append(field.getName());
            }
            buffer.append(",");
            valBuffer.append(",");
        }
    }

    public Map<Object, Object> getKeyVals() {
        return keyVals;
    }

    /**
     * 修改返回映射值
     *
     * @param resultPath
     * @param operatorName
     * @param parameterTypes
     * @throws Exception
     */
    private void alterResultPath(String resultPath, String operatorName, Class<?>... parameterTypes) throws Exception {
        SqlTemplate sqlTemplate0 = (SqlTemplate) ApplicationContextUtil.getBean("sqlTemplate");
        System.out.println(sqlTemplate0);
        Class<SqlTemplate> sqlTemplate = (Class<SqlTemplate>) sqlTemplate0.getClass();/*(Class<SqlTemplateProvider>) ClassLoader.getSystemClassLoader().loadClass(templatePath);*/
        Method[] methods = sqlTemplate.getMethods();
        Method operatorMethod0 = null;
        for (Method method : methods) {
            System.out.println("method: === "+method.toString());
//            System.out.println("args: ==="+);
            if (method.getName().trim().equals(operatorName.trim())) {
                operatorMethod0 = method;
                break;
            }
        }
        Method operatorMethod=sqlTemplate.getMethod(operatorName.trim(), parameterTypes);

        Annotation annotation = operatorMethod.getAnnotation(ResultMap.class);
        if (annotation != null) {
            ResultMap resultMap = (ResultMap) annotation;
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(resultMap);
            Field declaredField = invocationHandler.getClass().getDeclaredField("memberValues");
            // 因为这个字段事 private final 修饰，所以要打开权限
            declaredField.setAccessible(true);
            // 获取 memberValues
            Map memberValues = (Map) declaredField.get(invocationHandler);
            // 修改 value 属性值
            memberValues.put("value", resultPath);
        }
    }

    /**
     * 获取返回映射地址
     *
     * @param tableName
     * @return
     */
    private String getResultPath(String tableName) throws Exception {
        if (Constant.resultPaths == null || Constant.resultPaths.size() <= 0) {
            throw new ListValueNotFoundException("无法从常量池中找到相应的返回地址串！");
        }
        for (String result : Constant.resultPaths) {
            if (result.trim().contains(tableName.trim())) {
                return result;
            }
        }
        return "";
    }

}
