package cn.qhb.haiv.service.assm;

import cn.qhb.haiv.model.Device;
import cn.qhb.haiv.model.vo.AddDeviceVo;
import cn.qhb.haiv.model.vo.EditDeviceVo;
import cn.qhb.haiv.service.BaseService;

import java.util.List;

/**
 * 设备资产管理服务接口
 */
public interface AssmService extends BaseService<Device> {

    /**
     * 对设备资产进行条件计数
     *
     * @param keyword
     * @return
     */
    int count(String keyword);

    /**
     * 对设备资产进行条件分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    List<Device> findPageDevices(int pageNum, int pageSize, String keyword) throws Exception;

    /**
     * 添加实体
     *
     * @param entity
     * @return
     */
    int addEntity(AddDeviceVo entity);

    /**
     * 批量添加实体
     *
     * @param devices
     * @return
     */
    int addDevices(List<Device> devices);

    /**
     * 更新实体
     *
     * @param entity
     * @return
     */
    String updateEntity(EditDeviceVo entity);

    /**
     * 按照id串进行批量删除
     *
     * @param ids
     * @return
     */
    int batchDeleteByIds(String ids);

    /**
     * 按照id数组进行批量删除
     *
     * @param ids
     * @return
     */
    int batchDeleteByIdA(Long... ids);

    /**
     * 按照id串获取设备信息列表
     *
     * @param ids
     * @return
     */
    List<Device> findListByIds(String ids);
}
