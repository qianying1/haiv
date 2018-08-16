package cn.qhb.haiv.transaction.assm;

import cn.qhb.haiv.core.mapper_potting.MapperHelperI;
import cn.qhb.haiv.model.Warning;

import java.util.List;

public interface WarningTransaction /*extends MapperHelperI<Warning> */{

    int countWarning();

    /**
     * 分页查询设备信息
     *
     * @param start
     * @param offset
     * @return
     */
    List<Warning> findPage(int start, int offset) throws Exception;

    /**
     * 通过主键查找一个实体
     *
     * @return
     */
    Warning findOneById(Long id) throws Exception;

    /**
     * 通过一个key关键字查找一个实体
     *
     * @param key
     * @return
     */
    Warning findOneByOneKey(String key,Object val);

    /**
     * 更新实体
     *
     * @param entity
     * @return
     */
    int update(Warning entity) throws Exception;
}
