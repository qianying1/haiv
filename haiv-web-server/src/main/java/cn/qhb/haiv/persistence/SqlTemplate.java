package cn.qhb.haiv.persistence;

import cn.qhb.haiv.core.util.SqlFilter;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * sql操作模板
 *
 * @param <T>
 */
public interface SqlTemplate<T> {
    /*String deviceResultPath = "cn.qhb.haiv.persistence.DeviceMapper.deviceResult";
    String warningResultPath = "cn.qhb.haiv.persistence.WarningMapper.warningResult";
    String resultPath = "";*/

    /**
     * 插入单条数据信息
     *
     * @param tableName
     * @param fields
     * @param vals
     * @param entity
     * @return
     * @throws Exception
     */
    @Insert({"insert into ${tableName}(${fields}) values(${vals})"})
    @Options(useGeneratedKeys = true)
    int insert(@Param("tableName") String tableName, @Param("fields") String fields, @Param("vals") String vals, @Param("entity") T entity) throws Exception;

    /**
     * 批量插入
     *
     * @param tableName
     * @param fields
     * @param vals
     * @param entities
     * @return
     */
    @Insert({
            "<script>",
            "insert into ${tableName}(${fields}) values",
            "<foreach collection=\"list\" item=\"entity\" separator=\",\">",
            "(${vals})",
            "</foreach>",
            "</script>"
    })
    @Options(useGeneratedKeys = true)
    int inserts(@Param("tableName") String tableName, @Param("fields") String fields, @Param("vals") String vals, @Param("entities") List<T> entities);

    /**
     * 通过id更新对应数据
     *
     * @param tableName
     * @param keyVals
     * @param entity
     * @return
     */
    @Update({
            "<script>",
            "update ${tableName} set ",
            "<foreach collection=\"keyVals.entrySet()\" item=\"value\" index=\"key\" separator=\",\">",
            "${key}=${value}",
            "</foreach>",
            " where id=#{entity.id}",
            "</script>"
    })
    @Options(useGeneratedKeys = true)
    int updateById(@Param("tableName") String tableName, @Param("keyVals") Map<Object, Object> keyVals, @Param("entity") T entity);

    /**
     * 按照条件更新数据
     *
     * @param tableName
     * @param keyVals
     * @param filters
     * @param entity
     * @return
     */
    @Update({
            "<script>",
            "update ${tableName} set ",
            "<foreach collection=\"keyVals.entrySet()\" item=\"value\" index=\"key\" separator=\",\">",
            "${key}=${value}",
            "</foreach>",
            " where ",
            "<foreach collection=\"list\" item=\"filter\" separator=\" and \">",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "</foreach>",
            "</script>"
    })
    @Options(useGeneratedKeys = true)
    int updateByParams(@Param("tableName") String tableName, @Param("keyVals") Map<Object, Object> keyVals, @Param("filters") List<SqlFilter> filters, @Param("entity") T entity);

    /**
     * 通过id删除数据
     *
     * @param tableName
     * @param id
     * @return
     */
    @Delete({"delete from ${tableName} where id=#{id}"})
    @Options(useGeneratedKeys = true)
    int deleteById(@Param("tableName") String tableName, @Param("id") Long id);

    /**
     * 按照条件删除数据
     *
     * @param tableName
     * @param filters
     * @return
     */
    @Delete({
            "<script>",
            "delete from ${tableName} where ",
            "<foreach collection=\"list\" item=\"filter\" separator=\" and \">",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "</foreach>",
            "</script>"
    })
    @Options(useGeneratedKeys = true)
    int deleteByParamsList(@Param("tableName") String tableName, @Param("filters") List<SqlFilter> filters);

    /**
     * 按照条件删除数据
     *
     * @param tableName
     * @param filtersArr
     * @return
     */
    @Delete({
            "<script>",
            "delete from ${tableName} where ",
            "<foreach collection=\"list\" item=\"filter\" separator=\" and \">",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "</foreach>",
            "</script>"
    })
    @Options(useGeneratedKeys = true)
    int deleteByParamsArray(@Param("tableName") String tableName, @Param("filtersArr") SqlFilter... filtersArr);

    /**
     * 通过id查询实体数据
     *
     * @param tableName
     * @param id
     * @return
     */
    @ResultMap("")
    @Select({"select * from ${tableName} where id=#{id}"})
    T selectOneById(@Param("tableName") String tableName, @Param("id") Long id);

    /**
     * 按条件进行单数据查询
     *
     * @param tableName
     * @param filters
     * @return
     */
    @ResultMap("")
    @Select({
            "select * from ${tableName} <script><if test=\"filters != null\">where </if>",
            "<foreach collection=\"list\" item=\"filter\" separator=\" and \">",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "</foreach>",
            "</script>"})
    List<T> selectByParams(@Param("tableName") String tableName, @Param("filters") List<SqlFilter> filters);

    /**
     * 条件分页获取数据
     *
     * @param tableName
     * @param fields
     * @param filters
     * @param start
     * @param offset
     * @return
     */
    @ResultMap("")
    @Select({
            "select * from ${tableName} <script><if test=\"filters != null\">where </if>",
            "<foreach collection=\"list\" item=\"filter\" separator=\" and \">",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "</foreach>",
            "limit #{start},#{offset}",
            "</script>"})
    List<T> selectPageByParams(@Param("tableName") String tableName, @Param("fields") String fields, @Param("filters") List<SqlFilter> filters, @Param("start") int start, @Param("offset") int offset);

    /**
     * 分页获取数据
     *
     * @param tableName
     * @param fields
     * @param start
     * @param offset
     * @return
     */
//    @ResultMap("cn.qhb.haiv.persistence.DeviceMapper.deviceResult")
    @Select({
            "select * from ${tableName}",
            " limit #{start},#{offset}"
    })
    List<T> selectPage(@Param("tableName") String tableName, @Param("fields") String fields, @Param("start") Integer start, @Param("offset") Integer offset);

    /**
     * 计算数据量
     *
     * @param tableName
     * @return
     */
    @Select("select count(id) from ${tableName}")
    int count(@Param("tableName") String tableName);
}