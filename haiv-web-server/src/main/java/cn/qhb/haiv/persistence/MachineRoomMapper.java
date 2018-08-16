package cn.qhb.haiv.persistence;

import cn.qhb.haiv.core.util.SqlFilter;
import cn.qhb.haiv.model.MachineRoom;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Mapper
@Repository("machineMapper")
public interface MachineRoomMapper {

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
            @Result(property = "cabinets", column = "id", many = @Many(select = "cn.qhb.haiv.persistence.CabinetMapper.findSimpleListByRoomId")),
            @Result(property = "name", column = "name"),
            @Result(property = "location", column = "location"),
            @Result(property = "remark", column = "remark")
    }, id = "machineRoomResult")
    @Select({"select * from machine_room ",
            "<script>",
            "<if test=\"filters != null && filters.size()>0\">",
            "where",
            "</if>",
            "<foreach item=\"filter\" collection=\"list\">",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "</foreach>",
            "</script>",
            "limit #{start},#{end}"})
    List<MachineRoom> selectPage(@Param("start") int start, @Param("end") int end, @Param("filters")List<SqlFilter> filters);

    @ResultMap("machineRoomResult")
    @Select("select * from machine_room where id=#{id}")
    MachineRoom selectOneById(@NotNull @Param("id") Long id);
    /**
     * 按照条件计算数据量
     *
     * @param filters
     * @return
     */
    @Select({"select count(id) from machine_room ",
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
     * @param machineRoom
     * @return
     */
    @Insert("inset into machine_room(name,location,remark,insert_time) values(#{machineRoom.name},#{machineRoom.location},#{machineRoom.remark},#{machineRoom.insertTime})")
    @Options(useGeneratedKeys = true)
    int insert(@NotNull @Param("machineRoom") MachineRoom machineRoom);

    /**
     * 按条件进行数据查询（filters为null则查询所有数据）
     *
     * @param filters
     * @return
     */
    @ResultMap("machineRoomResult")
    @Select({
            "select * from machine_room ",
            "<script>",
            "<if test=\"filters != null && filters.size()>0\">",
            "where",
            "</if>",
            "<foreach item=\"filter\" collection=\"list\">",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "</foreach>",
            "</script>",
    })
    List<MachineRoom> selectListByParams(@Param("filters") List<SqlFilter> filters);

    /**
     * 按条件进行删除
     *
     * @param filters
     * @return
     */
    @Delete({
            "delete from machine_room ",
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
    int deleteByParams(@NotNull @Param("filters") List<SqlFilter> filters);

    /**
     * 按条件进行更新数据（默认已经有id作为条件）
     *
     * @param filters
     * @param entity
     * @return
     */
    @Update({
            "update machine_room set name=#{entity.name},location=#{entity.location},remark=#{entity.remark} where id=#{entity.id}",
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
    int updateByParams(@Param("filters") List<SqlFilter> filters,@NotNull @Param("entity") MachineRoom entity);

    /**
     * 通过id查找数据
     *
     * @param id
     * @return
     */
    @ResultMap("machineRoomResult")
    @SelectProvider(type = MachineRoomProvider.class,method = "findOneById")
    MachineRoom findOneById(@Param("id") Long id);

    /**
     * 通过id查找数据
     *
     * @param id
     * @return
     */
    @ResultMap("machineRoomSimpleResult")
    @Select("select * from machine_room where id=#{id}")
    MachineRoom findSimpleOneById(@Param("id") Long id);
}
