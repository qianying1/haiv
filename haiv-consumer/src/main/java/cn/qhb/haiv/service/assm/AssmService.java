package cn.qhb.haiv.service.assm;

import cn.qhb.haiv.model.Device;
import cn.qhb.haiv.model.vo.AddDeviceVo;
import cn.qhb.haiv.model.vo.EditDeviceVo;
import cn.qhb.haiv.service.BaseService;

import java.util.List;

/**
 * 设备资产服务层接口
 */
public interface AssmService extends BaseService<Device> {

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
//   List<Device> findPageByParams(Object... params);
//
//    Device findOneById(Long id);
//
//    int updateEntity(Device entity);
//
//    int deleteEntity(Device entity);
//
//    /**
//     *
//     * @param entity
//     * @return
//     */
//    Device findOneBy(Device entity);

    /**
     * 对设备信息进行计数
     *
     * @return
     */
    int count(String keyword);

    /**
     * 条件分页获取设备信息
     *
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    List<Device> findPageDevices(int pageNum, int pageSize, String keyword);

    /**
     * 按照id串获取设备信息列表
     *
     * @param ids
     * @return
     */
    List<Device> findListByIds(String ids);
}
