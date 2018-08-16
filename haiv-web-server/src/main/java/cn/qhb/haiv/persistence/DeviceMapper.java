package cn.qhb.haiv.persistence;

import cn.qhb.haiv.core.util.SqlFilter;
import cn.qhb.haiv.model.Device;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 设备资产映射
 *
 * @author qianhaibin
 */

@Mapper
@Repository("deviceMapper")
public interface DeviceMapper {

    /**
     * 通过Id获取单个设备信息
     *
     * @param id
     * @return
     */
    @Results(id = "deviceResult", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "assetModel", column = "asset_model"),
            @Result(property = "cpuModel", column = "cpu_model"),
            @Result(property = "manufacturer", column = "manufacturer"),
            @Result(property = "seriesNum", column = "series_num"),
            @Result(property = "operaSys", column = "opera_sys"),
            @Result(property = "proIp", column = "pro_ip"),
            @Result(property = "ofbVersion", column = "ofb_version"),
            @Result(property = "ofbIp", column = "ofb_ip"),
            @Result(property = "respMan", column = "resp_man"),
            @Result(property = "status", column = "status"),
            @Result(property = "usage", column = "usage"),
            @Result(property = "dataStatus", column = "data_status"),
            @Result(property = "ofd", column = "ofd", javaType = java.util.Date.class),
            @Result(property = "dofd", column = "dofd"),
            @Result(property = "insertTime", column = "insert_time"),
            @Result(property = "cabinetId", column = "cabinet_id"),
            @Result(property = "capacity", column = "capacity"),
            @Result(property = "internMemory", column = "intern_memory"),
            @Result(property = "warnings", column = "id", many = @Many(select = "cn.qhb.haiv.persistence.WarningMapper.findSimpleListByDeviceId", fetchType = FetchType.EAGER)),
            @Result(property = "cabinet", column = "cabinet_id", one = @One(select = "cn.qhb.haiv.persistence.CabinetMapper.findSimpleOneById", fetchType = FetchType.EAGER))
    })
    @Select({"select * from device where id=#{id}"})
    Device selectOneById(@NotNull @Param("id") Long id);

    /**
     * 通过id查找设备数据（为简单数据）
     *
     * @param id
     * @return
     */
    @ResultMap(value = "deviceSimpleResult")
    @Select({"select * from device where id=#{id}"})
    Device selectSimpleOneById(@NotNull @Param("id") Long id);

    /**
     * 通过机柜查找设备数据
     *
     * @param cabinetId
     * @return
     */
    @ResultMap(value = "deviceSimpleResult")
    @Select({"select * from device where cabinet_id=#{cabinetId}"})
    List<Device> selectSimpleListByCabinetId(@Param("cabinetId") Long cabinetId);

    /**
     * 批量插入，Oralce需要设置useGeneratedKeys=false，不然报错
     * Oracle批量插入：  insert all into table(...) values(...) into table(...) values(...) select * from dual
     * Mysql批量插入：   insert into table(...) values(...),(...)
     *
     * @param devices
     * @return
     */
    @Insert({"<script>",
            "insert into device(name,asset_model,cpu_model,manufacturer,series_num,opera_sys,pro_ip,OFB_version,OFB_ip,resp_man," +
                    "status,usage_,data_status,ofd,dofd,intern_memory,capacity,cabinet_id,alter_time,insert_time) ",
            "values",
            "<foreach collection=\"devices\" item=\"device\" separator=\",\">" +
                    "(#{device.name},#{device.assetModel},#{device.cpuModel},#{device.manufacturer},#{device.seriesNum},#{device.operaSys},#{device.proIp}," +
                    "#{device.ofbVersion},#{device.ofbIp},#{device.respMan},#{device.status},#{device.usage},#{device.dataStatus}," +
                    "#{device.ofd},#{device.dofd},#{device.internMemory},#{device.capacity},#{device.cabinetId},now(),now())",
            "</foreach>",
            "</script>"})
    @Options(useGeneratedKeys = true)
    int insertDevices(@Param("devices") List<Device> devices);

    @Delete({"delete from device where id=#{device.id}"})
    @Options(useGeneratedKeys = true)
    int delete(@Param("device") Device device) throws Exception;

    @Delete({"delete from device where id in (#{ids})"})
    int batchDeleteByIds(@Param("ids") String ids) throws Exception;

    /**
     * 条件查找设备列表信息
     *
     * @param filters
     * @return
     */
    @ResultMap("deviceSimpleResult")
    @Select({
            "<script>",
            "select * from device",
            "<if test=\"filters != null and filters.size>0\">",
            " where",
            "<foreach item=\"filter\" collection=\"filters\">",
            "<if test=\"filter.preBracket\">",
            "(",
            "</if>",
            "${filter.key} ${filter.operatorStr} <choose><when test=\"filter.operatorStr=='in'\">(${filter.val})</when><otherwise>#{filter.val}</otherwise></choose>",
            "<if test=\"filter.afterBracket\">",
            ")",
            "</if>",
            "<if test=\"filter.next\">",
            "${filter.linkStr}",
            "</if>",
            "</foreach>",
            "</if>",
            "</script>"
    })
    List<Device> selectListByParams(@Param("filters") List<SqlFilter> filters) throws Exception;

    /**
     * 插入设备信息
     *
     * @param device
     * @return
     */
    @Insert("insert into device(name,asset_model,cpu_model,manufacturer,series_num,opera_sys,pro_ip,OFB_version,OFB_ip,resp_man," +
            "status,usage_,data_status,ofd,dofd,intern_memory,capacity,cabinet_id,alter_time,insert_time) value(#{name},#{assetModel},#{cpuModel},#{manufacturer},#{seriesNum},#{operaSys},#{proIp}," +
            "#{ofbVersion},#{ofbIp},#{respMan},#{status},#{usage},#{dataStatus}," +
            "#{ofd},#{dofd},#{internMemory},#{capacity},#{cabinetId},now(),now())")
    @Options(useGeneratedKeys = true)
    int insertDevice(Device device);

    /**
     * 查询全部，引用上面的Results
     *
     * @return
     */
    @ResultMap({"deviceResult"})
    @Select("select id, name, location from device")
    List<Device> selectAll();

    /**
     * 计数设备数量
     *
     * @return
     */
    @Select({
            "<script>",
            "select count(id) from device",
            "<if test=\"filters != null and filters.size>0\">",
            " where",
            "<foreach item=\"filter\" collection=\"filters\" index=\"i\">",
            "<if test=\"filter.preBracket\">",
            "(",
            "</if>",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "<if test=\"filter.afterBracket\">",
            ")",
            "</if>",
            "<if test=\"filter.next\">",
            "${filter.linkStr}",
            "</if>",
            "</foreach>",
            "</if>",
            "</script>"
    })
    int count(@Param("filters") List<SqlFilter> filters);

    /**
     * 分页获取设备信息
     *
     * @param start
     * @param offset
     * @return
     */
    @ResultMap(value = "deviceResult")
    @Select({
            "<script>",
            "select * from device",
            "<if test=\"filters != null and filters.size>0\">",
            " where",
            "<foreach item=\"filter\" collection=\"filters\" index=\"i\">",
            "<if test=\"filter.preBracket\">",
            "(",
            "</if>",
            "${filter.key} ${filter.operatorStr} #{filter.val}",
            "<if test=\"filter.afterBracket\">",
            ")",
            "</if>",
            "<if test=\"filter.next\">",
            "${filter.linkStr}",
            "</if>",
            "</foreach>",
            "</if>",
            " limit #{start},#{offset}",
            "</script>"
    })
    List<Device> findPage(@Param(value = "start") int start, @Param(value = "offset") int offset, @Param("filters") List<SqlFilter> filters);

    /**
     * 更新设备信息
     *
     * @param device
     * @return
     */
    @Update({
            "update device set ",
            "name=#{device.name},",
            "asset_model=#{device.assetModel},",
            "cpu_model=#{device.cpuModel},",
            "manufacturer=#{device.manufacturer},",
            "series_num=#{device.seriesNum},",
            "opera_sys=#{device.operaSys},",
            "pro_ip=#{device.proIp},",
            "OFB_version=#{device.ofbVersion},",
            "OFB_ip=#{device.ofbIp},",
            "resp_man=#{device.respMan},",
            "status=#{device.status},",
            "usage_=#{device.usage},",
            "data_status=#{device.dataStatus},",
            "ofd=#{device.ofd},",
            "dofd=#{device.dofd},",
            "intern_memory=#{device.internMemory},",
            "capacity=#{device.capacity},",
            "cabinet_id=#{device.cabinetId}",
            "alter_time=new()",
            " where id=#{device.id}"
    })
    int updateById(@Param("device") Device device);
}
