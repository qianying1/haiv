package cn.qhb.haiv.core.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * sql模板实体
 */
public class SqlTemplateEntity<T> implements Serializable {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 实体字段
     */
    private String fields;

    /**
     * 字段的数组表示
     */
    private String[] fieldArr;

    /**
     * 值列表
     */
    private String vals;

    /**
     * 值的数组表示
     */
    private String[] valArr;

    /**
     * 键值对映射
     */
    private Map<Object, Object> keyVals;

    /**
     * 条件子句
     */
    private String condition;

    /**
     * 过滤条件列表
     */
    private List<SqlFilter> filters;

    /**
     * 过滤条件数组
     */
    private SqlFilter[] filtersArr;

    /**
     * 唯一键
     */
    private Long id;

    /**
     * 开始位置
     */
    private int start;

    /**
     * 位移量
     */
    private int offset;

    /**
     * 目标实体
     */
    private T entity;

    /**
     * insert模板
     *
     * @param tableName
     * @param fields
     * @param vals
     * @param entity
     */
    public SqlTemplateEntity(String tableName, String fields, String vals, T entity) {
        this.tableName = tableName;
        this.fields = fields;
        this.vals = vals;
        this.entity = entity;
    }

    /**
     * 分页获取数据模板
     *
     * @param tableName
     * @param fields
     * @param start
     * @param offset
     */
    public SqlTemplateEntity(String tableName, String fields, int start, int offset) {
        this.tableName = tableName;
        this.fields = fields;
        this.start = start;
        this.offset = offset;
    }

    /**
     * 条件分页获取数据模板
     *
     * @param tableName
     * @param fields
     * @param start
     * @param offset
     */
    public SqlTemplateEntity(String tableName, String fields, List<SqlFilter> filters, int start, int offset) {
        this.tableName = tableName;
        this.fields = fields;
        this.filters = filters;
        this.start = start;
        this.offset = offset;
    }

    /**
     * 条件删除数据模板
     *
     * @param tableName
     * @param filtersArr
     */
    public SqlTemplateEntity(String tableName, SqlFilter... filtersArr) {
        this.tableName = tableName;
        this.filtersArr = filtersArr;
    }

    /**
     * 通过id查询数据模板
     *
     * @param tableName
     * @param id
     */
    public SqlTemplateEntity(String tableName, Long id) {
        this.tableName = tableName;
        this.id = id;
    }

    /**
     * 条件查询模板
     *
     * @param tableName
     * @param filters
     */
    public SqlTemplateEntity(String tableName, List<SqlFilter> filters) {
        this.tableName = tableName;
        this.filters = filters;
    }

    /**
     * 单个update模板
     *
     * @param tableName
     * @param keyVals
     * @param filters
     * @param entity
     */
    public SqlTemplateEntity(String tableName, Map<Object, Object> keyVals, List<SqlFilter> filters, T entity) {
        this.tableName = tableName;
        this.keyVals = keyVals;
        this.filters = filters;
        this.entity = entity;
    }

    /**
     * 单个update模板
     *
     * @param tableName
     * @param keyVals
     * @param entity
     */
    public SqlTemplateEntity(String tableName, Map<Object, Object> keyVals, T entity) {
        this.tableName = tableName;
        this.keyVals = keyVals;
        this.entity = entity;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getVals() {
        return vals;
    }

    public void setVals(String vals) {
        this.vals = vals;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "SqlTemplateEntity{" +
                "tableName='" + tableName + '\'' +
                ", fields='" + fields + '\'' +
                ", vals='" + vals + '\'' +
                ", condition='" + condition + '\'' +
                ", entity=" + entity +
                '}';
    }

    public String[] getFieldArr() {
        return fieldArr;
    }

    public void setFieldArr(String[] fieldArr) {
        this.fieldArr = fieldArr;
    }

    public String[] getValArr() {
        return valArr;
    }

    public void setValArr(String[] valArr) {
        this.valArr = valArr;
    }

    public List<SqlFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<SqlFilter> filters) {
        this.filters = filters;
    }

    public Map<Object, Object> getKeyVals() {
        return keyVals;
    }

    public void setKeyVals(Map<Object, Object> keyVals) {
        this.keyVals = keyVals;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SqlFilter[] getFiltersArr() {
        return filtersArr;
    }

    public void setFiltersArr(SqlFilter[] filtersArr) {
        this.filtersArr = filtersArr;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
