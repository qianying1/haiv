package cn.qhb.haiv.persistence;

import cn.qhb.haiv.core.util.SqlFilter;
import cn.qhb.haiv.model.Warning;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository("warningMapper")
public interface WarningMapper {

    /**
     * 通过设备id查找警告设备列表
     *
     * @param id
     * @return
     */
    @Results(id = "warningResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "msg", column = "msg"),
            @Result(property = "actionTime", column = "action_time", javaType = java.util.Date.class),
            @Result(property = "insertTime", column = "insert_time", javaType = java.util.Date.class),
            @Result(property = "status", column = "status"),
            @Result(property = "warningLevel", column = "warning_level"),
            @Result(property = "device", column = "device_id", one = @One(select = "cn.qhb.haiv.persistence.DeviceMapper.selectSimpleOneById"))
    })
    @Select({"select * from warning where device_id=#{id}"})
    Set<Warning> findListByDeviceId(@Param("id") Long id);

    /**
     * 通过设备id查找警告数据列表
     *
     * @param id
     * @return
     */
    @ResultMap(value = "warningSimpleResult")
    @Select({"select * from warning where device_id=#{id}"})
    Set<Warning> findSimpleListByDeviceId(@Param("id") Long id);

    /**
     * 分页查询数据
     *
     * @param start
     * @param end
     * @return
     */
    @ResultMap("warningResult")
    @Select({"select * from warning ",
            "<script>",
            "<if test=\"filters != null && filters.size()>0\">",
            "where",
            "</if>",
            "<foreach item=\"filter\" collection=\"list\">",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "</foreach>",
            "</script>",
            "limit #{start},#{end}"})
    List<Warning> selectPage(@Param("start") int start, @Param("end") int end, @Param("filters") List<SqlFilter> filters);

    /**
     * 按照条件计算数据量
     *
     * @param filters
     * @return
     */
    @Select({"select count(id) from warning ",
            "<script>",
            "<if test=\"filters != null && filters.size()>0\">",
            "where",
            "</if>",
            "<foreach item=\"filter\" collection=\"list\">",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "</foreach>",
            "</script>",
    })
    int countByParams(@Param("filters") List<SqlFilter> filters);

    /**
     * 插入单个数据
     *
     * @param entity
     * @return
     */
    @Insert("inset into waring(msg,status,warning_level,action_time,insert_time) values(#{entity.msg},#{entity.status},#{entity.warningLevel},#{entity.actionTime},#{entity.insertTime})")
    @Options(useGeneratedKeys = true)
    int insert(@Param("entity") Warning entity);

    /**
     * 按条件进行数据查询
     *
     * @param filters
     * @return
     */
    @ResultMap("warningResult")
    @Select({
            "select * from warning ",
            "<script>",
            "<if test=\"filters != null && filters.size()>0\">",
            "where",
            "</if>",
            "<foreach item=\"filter\" collection=\"list\">",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "</foreach>",
            "</script>",
    })
    List<Warning> selectListByParams(@Param("filters") List<SqlFilter> filters);

    /**
     * 按条件进行删除
     *
     * @param filters
     * @return
     */
    @Delete({
            "delete from warning ",
            "<script>",
            "<if test=\"filters != null && filters.size()>0\">",
            "where",
            "</if>",
            "<foreach item=\"filter\" collection=\"list\">",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "</foreach>",
            "</script>",
    })
    @Options(useGeneratedKeys = true)
    int deleteByParams(@Param("filters") List<SqlFilter> filters);

    /**
     * 按照设备id删除警告信息
     *
     * @param id
     * @return
     */
    @Delete({
            "delete from warning where device_id=#{deviceId}"
    })
    @Options(useGeneratedKeys = true)
    int deleteByDeviceId(@Param("deviceId") Long id) throws Exception;

    /**
     * 按照设备id批量删除警告信息
     *
     * @param deviceIds
     * @return
     * @throws Exception
     */
    @Delete({"delete from device where device_id in (#{ids})"})
    @Options(useGeneratedKeys = true)
    int batchDeleteByDeviceIds(@Param("ids") String deviceIds) throws Exception;

    /**
     * 按条件进行更新数据（默认已经有id作为条件）
     *
     * @param filters
     * @param entity
     * @return
     */
    @Update({
            "update warning set msg=#{entity.msg},status=#{entity.status},warning_level=#{entity.warningLevel},action_time=#{entity.actionTime} where id=#{entity.id}",
            "<script>",
            "<if test=\"filters != null && filters.size()>0\">",
            " and ",
            "</if>",
            "<foreach item=\"filter\" collection=\"list\",separator=\" and \">",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "</foreach>",
            "</script>",
    })
    @Options(useGeneratedKeys = true)
    int updateByParams(@Param("filters") List<SqlFilter> filters, @Param("entity") Warning entity);
}
