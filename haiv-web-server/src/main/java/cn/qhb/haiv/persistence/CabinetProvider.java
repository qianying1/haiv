package cn.qhb.haiv.persistence;

import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;

/**
 * 机柜sql提供者
 */
public class CabinetProvider {

    /**
     * 按照id返回单个实体
     *
     * @param id
     * @return
     * @throws Exception
     */
    public String selectById(@NotNull @Param("id") Long id) throws Exception {
        return "select * from cabinet where 1=1 and id=#{id}";
    }
}
