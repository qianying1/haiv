package cn.qhb.haiv.transaction.assm.impl;

import cn.qhb.haiv.core.util.SqlFilter;
import cn.qhb.haiv.model.Device;
import cn.qhb.haiv.persistence.DeviceMapper;
import cn.qhb.haiv.persistence.WarningMapper;
import cn.qhb.haiv.transaction.assm.DeviceTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备资产数据操作事务
 */
@Repository
public class DeviceTransactionImpl /*extends MapperHelper<Device> */ implements DeviceTransaction {
    private static Logger logger = LoggerFactory.getLogger(DeviceTransactionImpl.class);

    @Resource(name = "deviceMapper")
    private DeviceMapper deviceMapper;
    @Resource(name = "warningMapper")
    private WarningMapper warningMapper;

    /**
     * 对设备资产进行统计计数
     *
     * @return
     */
    @Transactional(readOnly = true)
    public int countDevices(String keyword) {
        try {
            List<SqlFilter> filters = null;
            if (keyword != null && !keyword.trim().equals("")) {
                filters = new ArrayList<>();
                filters.add(new SqlFilter(true, "name", SqlFilter.Operator.like, "%" + keyword.trim() + "%", true, SqlFilter.FilterLinkStr.or, false));
                filters.add(new SqlFilter(false, "cpu_model", SqlFilter.Operator.like, "%" + keyword.trim() + "%", false, null, true));
            }
            return deviceMapper.count(filters);
        } catch (Exception sqle) {
            logger.error("执行sql语句出现错误！", sqle);
            return 0;
        }
    }

    /**
     * 条件分页查询设备信息
     *
     * @param start
     * @param offset
     * @param keyword
     * @return
     */
    @Transactional(readOnly = true)
    public List<Device> findPage(int start, int offset, String keyword) throws Exception {
        try {
//            return selectPage(start,offset);
            List<SqlFilter> filters = null;
            if (keyword != null && !keyword.trim().equals("")) {
                filters = new ArrayList<>();
                filters.add(new SqlFilter(false, "name", SqlFilter.Operator.like, "%" + keyword.trim() + "%", true, SqlFilter.FilterLinkStr.or, false));
                filters.add(new SqlFilter(false, "cpu_model", SqlFilter.Operator.like, "%" + keyword.trim() + "%", false, null, false));
            }
            return deviceMapper.findPage(start, offset, filters);
        } catch (Exception e) {
            logger.error("执行分页查询sql出错！", e);
            throw e;
        }
    }

    /**
     * 更新设备信息
     *
     * @param device
     * @return
     */
    @Transactional
    public int update(Device device) throws Exception {
        return deviceMapper.updateById(device);
    }

    /**
     * 通过id查找设备信息
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Device findOneById(Long id) throws Exception {
        return deviceMapper.selectOneById(id);
    }

    /**
     * 通过一个参数查找设备信息
     *
     * @param key
     * @return
     */
    @Transactional(readOnly = true)
    public Device findOneByOneKey(String key, Object val) {
//        SqlFilter<String>
        return null;
    }

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    public int insert(Device entity) {
        return deviceMapper.insertDevice(entity);
    }

    /**
     * 批量插入数据
     *
     * @param entities
     * @return
     * @throws Exception
     */
    public int inserts(List<Device> entities) throws Exception {
        return deviceMapper.insertDevices(entities);
    }

    /**
     * 删除单个设备信息
     *
     * @param device
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int delete(Device device) throws Exception {
        if (device == null || device.getId() == null) {
            return 0;
        }
        Long id = device.getId();
        warningMapper.deleteByDeviceId(id);
        return deviceMapper.delete(device);
    }

    /**
     * 通过id字符串批量删除设备信息
     *
     * @param ids
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int batchDelete(String ids) throws Exception {
        if (ids == null || ids.trim().equals("")) {
            return 0;
        }
        warningMapper.batchDeleteByDeviceIds(ids);
        return deviceMapper.batchDeleteByIds(ids);
    }

    /**
     * 条件查找设备列表信息
     *
     * @param filters
     * @return
     */
    public List<Device> findListByParams(List<SqlFilter> filters) throws Exception{
        return deviceMapper.selectListByParams(filters);
    }

}
