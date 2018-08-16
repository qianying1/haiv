package cn.qhb.haiv.transaction.assm;

import cn.qhb.haiv.core.util.SqlFilter;
import cn.qhb.haiv.model.Device;

import java.util.List;

/**
 * 设备资产事务
 */
public interface DeviceTransaction /*extends MapperHelperI<Device>*/ {

    /**
     * 计数设备信息
     *
     * @return
     */
    int countDevices(String keyword);

    /**
     * 条件分页查询设备信息
     *
     * @param start
     * @param offset
     * @param keyword
     * @return
     */
    List<Device> findPage(int start, int offset, String keyword) throws Exception;

    /**
     * 通过主键查找一个实体
     *
     * @return
     */
    Device findOneById(Long id) throws Exception;

    /**
     * 通过一个key关键字查找一个实体
     *
     * @param key
     * @return
     */
    Device findOneByOneKey(String key, Object val);

    /**
     * 更新实体
     *
     * @param entity
     * @return
     */
    int update(Device entity) throws Exception;

    /**
     * 插入数据
     *
     * @param entity
     * @return
     * @throws Exception
     */
    int insert(Device entity) throws Exception;

    /**
     * 批量插入数据
     *
     * @param entities
     * @return
     * @throws Exception
     */
    int inserts(List<Device> entities) throws Exception;

    /**
     * 删除单个设备信息
     *
     * @param device
     * @return
     */
    int delete(Device device) throws Exception;

    /**
     * 通过id字符串批量删除设备信息
     *
     * @param ids
     * @return
     */
    int batchDelete(String ids) throws Exception;

    /**
     * 条件查找设备列表信息
     *
     * @param filters
     * @return
     */
    List<Device> findListByParams(List<SqlFilter> filters) throws Exception;
}
