package cn.qhb.haiv.persistence;

import org.apache.ibatis.annotations.Param;

/**
 * 机房sql提供者
 */
public class MachineRoomProvider {

    /**
     * 通过id查找数据
     *
     * @param id
     * @return
     */
    public String findOneById(@Param("id") Long id) {
        String sql = "select * from machine_room where id=#{id}";
        return sql;
    }
}
