package cn.qhb.haiv.service.assm.impl;

import cn.qhb.haiv.core.util.ModelUtil;
import cn.qhb.haiv.core.util.ProtocolConstants;
import cn.qhb.haiv.core.util.SqlFilter;
import cn.qhb.haiv.model.Device;
import cn.qhb.haiv.model.vo.AddDeviceVo;
import cn.qhb.haiv.model.vo.EditDeviceVo;
import cn.qhb.haiv.persistence.DeviceMapper;
import cn.qhb.haiv.service.assm.AssmService;
import cn.qhb.haiv.transaction.assm.DeviceTransaction;
import cn.qhb.haiv.transaction.assm.WarningTransaction;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 资产管理
 */
@Service(version = "1.0.0")
public class AssmServiceImpl implements AssmService {
    private static Logger logger = LoggerFactory.getLogger(AssmServiceImpl.class);

    @Resource(name = "deviceMapper")
    private DeviceMapper deviceMapper;

    @Resource(name = "deviceTransactionImpl")
    private DeviceTransaction deviceTransaction;
    @Resource(name = "warningTransactionImpl")
    private WarningTransaction warningTransaction;

    /**
     * 添加设备
     *
     * @param device
     * @return
     */
    @Override
    public int addEntity(AddDeviceVo device) {
        try {
            Device newDevice = new Device();
            ModelUtil.modelMapperNewForIncludeFields(device, newDevice);
            return deviceTransaction.insert(newDevice);
        } catch (Exception e) {
            logger.error("添加设备信息异常！", e);
            return 0;
        }
    }

    /**
     * 批量添加实体
     *
     * @param devices
     * @return
     */
    public int addDevices(List<Device> devices) {
        try {
            System.out.println("device size: " + devices.size());
            for (Device device : devices) {
                System.out.println(device);
            }
            return deviceTransaction.inserts(devices);
        } catch (Exception e) {
            logger.error("批量插入设备信息出错！", e);
            return 0;
        }
    }

    /**
     * 按条件查找设备
     *
     * @param params
     * @return
     */
    @Override
    public List<Device> findPageByParams(Object... params) {
        return null;
    }

    /**
     * 通过id查询设备信息
     *
     * @param id
     * @return
     */
    @Override
    public Device findOneById(Long id) {
        try {
            return deviceTransaction.findOneById(id);
        } catch (Exception e) {
            logger.error("通过id查询设备信息出错！", e);
            return null;
        }
    }

    /**
     * 更新设备信息
     *
     * @param entity
     * @return
     */
    @Override
    public String updateEntity(EditDeviceVo entity) {
        try {
            if (entity == null || entity.getId() == null || entity.getId() < 0) {
                return ProtocolConstants.INPUT_PARAM_ERROR;
            }
            Device old = deviceTransaction.findOneById(entity.getId());
            if (old == null) {
                return ProtocolConstants.DATA_NOT_FOUNT;
            }
            ModelUtil.modelMapperAlterForExcludeFields(old, entity, "insertTime", "Cabinet", "Warnings");
            Device updateDevice = new Device();
            ModelUtil.modelMapperNewForIncludeFields(entity, updateDevice);
            int result = deviceMapper.updateById(updateDevice);
            if (result >= 1) {
                return ProtocolConstants.UPDATE_SUCCESS;
            } else {
                return ProtocolConstants.UPDATE_FAIL;
            }
        } catch (Exception e) {
            logger.error("添加设备信息出错！", e);
        }
        return ProtocolConstants.UPDATE_FAIL;
    }

    /**
     * 删除设备信息
     *
     * @param entity
     * @return
     */
    @Override
    public int deleteEntity(Device entity) {
        try {
            if (entity == null) {
                return 0;
            }
            return deviceTransaction.delete(entity);
        } catch (Exception e) {
            logger.error("删除单台设备信息失败！", e);
        }
        return 0;
    }

    /**
     * @param param
     * @return
     */
    @Override
    public Device findOneByParam(Object param) {
        return null;
    }

    /**
     * 条件获取当前设备的数量
     *
     * @param keyword
     * @return
     */
    public int count(String keyword) {
        return deviceTransaction.countDevices(keyword);
    }

    /**
     * 对设备资产进行条件分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    public List<Device> findPageDevices(int pageNum, int pageSize, String keyword) throws Exception {
        int start = 0, offset = 0;
        start = (pageNum - 1) * pageSize;
        offset = pageSize;
        return deviceTransaction.findPage(start, offset, keyword);
    }

    /**
     * 按照id串进行批量删除
     *
     * @param ids
     * @return
     */
    public int batchDeleteByIds(String ids) {
        try {
            if (ids == null || ids.trim().split(",").length <= 0) {
                return 0;
            }
            return deviceTransaction.batchDelete(ids);
        } catch (Exception e) {
            logger.error("通过id字符串批量删除设备信息失败！", e);
        }
        return 0;
    }

    /**
     * 按照id数组进行批量删除
     *
     * @param ids
     * @return
     */
    public int batchDeleteByIdA(Long... ids) {
        try {
            if (ids == null || ids.length <= 0) {
                return 0;
            }
            StringBuffer idS = new StringBuffer();
            for (Long id : ids) {
                idS.append(id).append(",");
            }
            idS.replace(idS.lastIndexOf(","), idS.length(), "");
            return deviceTransaction.batchDelete(idS.toString());
        } catch (Exception e) {
            logger.error("通过id字符串批量删除设备信息失败！", e);
        }
        return 0;
    }

    /**
     * 按照id串获取设备信息列表
     *
     * @param ids
     * @return
     */
    public List<Device> findListByIds(String ids) {
        try {
            SqlFilter sqlFilter = new SqlFilter(false, "id", SqlFilter.Operator.in, ids, false, null, false);
            List<SqlFilter> filters = new ArrayList<>();
            filters.add(sqlFilter);
            return deviceTransaction.findListByParams(filters);
        } catch (Exception e) {
            logger.error("通过id串查找设备数据信息列表出错！", e);
            return null;
        }
    }
}
