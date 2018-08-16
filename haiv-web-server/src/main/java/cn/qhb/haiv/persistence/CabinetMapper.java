package cn.qhb.haiv.persistence;

import cn.qhb.haiv.core.util.SqlFilter;
import cn.qhb.haiv.model.Cabinet;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("cabinetMapper")
public interface CabinetMapper {

    /**
     * 分页查询数据
     *
     * @param start
     * @param end
     * @return
     */
    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "insert_time", column = "insetTime"),
            @Result(property = "devices", column = "id", many = @Many(select = "cn.qhb.haiv.persistence.DeviceMapper.selectSimpleListByCabinetId")),
            @Result(property = "machineRoom", column = "room_id", one = @One(select = "cn.qhb.haiv.persistence.MachineRoomMapper.findSimpleOneById")),
            @Result(property = "name", column = "name"),
            @Result(property = "location", column = "location"),
            @Result(property = "usage", column = "usage")
    }, id = "cabinetResult")
    @Select({"select * from cabinet ",
            "<script>",
            "<if test=\"filters != null && filters.size()>0\">",
            "where",
            "</if>",
            "<foreach item=\"filter\" collection=\"list\">",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "</foreach>",
            "</script>",
            "limit #{start},#{end}"})
    List<Cabinet> selectPage(@Param("start") int start, @Param("end") int end, @Param("filters") List<SqlFilter> filters);

    /**
     * 按照条件计算数据量
     *
     * @param filters
     * @return
     */
    @Select({"select count(id) from cabinet ",
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
     * @param cabinet
     * @return
     */
    @Insert("inset into cabinet(name,location,usage,insert_time) values(#{cabinet.name},#{cabinet.location},#{cabinet.usage},#{cabinet.insertTime})")
    @Options(useGeneratedKeys = true)
    int insert(@Param("cabinet") Cabinet cabinet);

    /**
     * 按条件进行数据查询
     *
     * @param filters
     * @return
     */
    @ResultMap("cabinetResult")
    @Select({
            "select * from cabinet ",
            "<script>",
            "<if test=\"filters != null && filters.size()>0\">",
            "where",
            "</if>",
            "<foreach item=\"filter\" collection=\"list\">",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "</foreach>",
            "</script>",
    })
    List<Cabinet> selectListByParams(@Param("filters") List<SqlFilter> filters);

    /**
     * 按条件进行删除
     *
     * @param filters
     * @return
     */
    @Delete({
            "delete from cabinet ",
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
     * 按条件进行更新数据（默认已经有id作为条件）
     *
     * @param filters
     * @param entity
     * @return
     */
    @Update({
            "update cabinet set name=#{entity.name},location=#{entity.location},remark=#{entity.remark} where id=#{entity.id}",
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
    int updateByParams(@Param("filters") List<SqlFilter> filters, @Param("entity") Cabinet entity);

    /**
     * 通过id查找单个数据（查找的信息比较详细）
     *
     * @param id
     * @return
     */
    @ResultMap("cabinetResult")
    @Select("select * from cabinet where id=#{id}")
    Cabinet findById(@Param("id") Long id);

    /**
     * 通过id查找单个数据（查找的信息为简单）
     *
     * @param id
     * @return
     */
    @ResultMap(value = "cabinetSimpleResult")
    @Select("select * from cabinet where id=#{id}")
    Cabinet findSimpleOneById(@Param("id") Long id);

    /**
     * 通过机房id查找机柜列表数据
     *
     * @param roomId
     * @return
     */
    @ResultMap(value = "cabinetSimpleResult")
    @Select({"select * from cabinet where room_id=#{roomId}"})
    List<Cabinet> findSimpleListByRoomId(@Param("roomId") Long roomId);
}
