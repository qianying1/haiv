package cn.qhb.haiv.persistence;

import cn.qhb.haiv.core.util.SqlFilter;
import cn.qhb.haiv.model.Device;
import cn.qhb.haiv.model.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 设备资产映射
 *
 * @author qianhaibin
 */

@Mapper
@Repository("fileMapper")
public interface FileMapper {

    /**
     * 通过Id获取单个文件信息
     *
     * @param id
     * @return
     */
    @Results(id = "fileResult", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "savePath", column = "save_path"),
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "fileRealName", column = "file_real_name"),
            @Result(property = "size", column = "size"),
            @Result(property = "fileType", column = "file_type"),
            @Result(property = "insertTime", column = "insert_time"),
            @Result(property = "alterTime", column = "alter_time")
    })
    @Select({"select * from file where id=#{id}"})
    Device selectOneById(@NotNull @Param("id") Long id);

    /**
     * 通过id查找文件数据（为简单数据）
     *
     * @param id
     * @return
     */
    @ResultMap(value = "fileSimpleResult")
    @Select({"select * from file where id=#{id}"})
    Device selectSimpleOneById(@NotNull @Param("id") Long id);

    /**
     * 批量插入，Oralce需要设置useGeneratedKeys=false，不然报错
     * Oracle批量插入：  insert all into table(...) values(...) into table(...) values(...) select * from dual
     * Mysql批量插入：   insert into table(...) values(...),(...)
     *
     * @param files
     * @return
     */
    @Insert({"<script>",
            "insert into file(save_path,file_name,file_real_name,size,file_type,alter_time,insert_time)",
            "values",
            "<foreach collection=\"files\" item=\"file\" separator=\",\">" +
                    "(#{file.savePath},#{file.fileName},#{file.fileRealName},#{file.size},#{file.fileType},now(),now())",
            "</foreach>",
            "</script>"})
    @Options(useGeneratedKeys = true)
    int insertFiles(@Param("files") List<File> files);

    /**
     * 插入设备信息
     *
     * @param file
     * @return
     */
    @Insert("insert into file(save_path,file_name,file_real_name,size,file_type,alter_time,insert_time) value(#{file.savePath},#{file.fileName},#{file.fileRealName},#{file.size},#{file.fileType},now(),now())")
    @Options(useGeneratedKeys = true)
    int insertFile(@Param("file") File file) throws Exception;

    /**
     * 按照文件路径与文件名称进行文件删除
     *
     * @param path
     * @param fileSaveName
     * @return
     */
    @Delete("delete from file where save_path=#{path} and file_real_name=#{fileSaveName}")
    @Options(useGeneratedKeys = true)
    int deleteByPathAndFileSaveName(@Param("path") String path, @Param("fileSaveName") String fileSaveName) throws Exception;

    /**
     * 查询全部，引用上面的Results
     *
     * @return
     */
    @ResultMap({"fileResult"})
    @Select("select id,save_path,file_name,file_real_name,size,file_type,alter_time,insert_time from file")
    List<File> selectAll();

    /**
     * 计数文件数量
     *
     * @return
     */
    @Select({
            "<script>",
            "select count(id) from file",
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
    @ResultMap(value = "fileResult")
    @Select({
            "<script>",
            "select * from file",
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
     * @param file
     * @return
     */
    @Update({
            "update file set ",
            "save_path=#{file.savePath},",
            "file_name=#{file.fileName},",
            "file_real_name=#{file.fileRealName},",
            "size=#{file.size},",
            "file_type=#{file.fileType},",
            "alter_time=new()",
            " where id=#{file.id}"
    })
    int updateById(@Param("file") File file);
}
