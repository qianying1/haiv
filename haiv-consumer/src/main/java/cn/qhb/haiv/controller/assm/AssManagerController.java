package cn.qhb.haiv.controller.assm;

import cn.qhb.haiv.controller.api.AssManagerApi;
import cn.qhb.haiv.core.util.Constants;
import cn.qhb.haiv.core.util.JsonMessage;
import cn.qhb.haiv.core.util.Page;
import cn.qhb.haiv.core.util.ProtocolConstants;
import cn.qhb.haiv.model.Device;
import cn.qhb.haiv.model.vo.AddDeviceVo;
import cn.qhb.haiv.model.vo.EditDeviceVo;
import cn.qhb.haiv.service.assm.AssmService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


/**
 * 资产管理
 */
@RestController
@RequestMapping(value = "/assm", produces = {"application/json;charset=UTF-8", "application/x-www-form-urlencoded"})
public class AssManagerController implements AssManagerApi {
    private static Logger logger = LoggerFactory.getLogger(AssManagerController.class);
    @Reference(version = "1.0.0")
    private AssmService assmService;

    /**
     * 按条件查询设备分页数据
     *
     * @return
     */
    @RequestMapping(value = "/list/{pageNum}/{pageSize}/{keyword}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public @ResponseBody
    Object list(@PathVariable(value = "pageNum", required = false) int pageNum,
                @PathVariable(value = "pageSize", required = false) int pageSize,
                @PathVariable(value = "keyword", required = false) String keyword) {
        try {
            System.out.println("keyword: == " + keyword);
            keyword = (keyword != null && !keyword.trim().equals("{keyword}")) ? keyword : null;
            int total = assmService.count(keyword);
            if (Page.isPageNumOverFlowPage(pageNum, pageSize, total)) {
                return JsonMessage.successList(Constants.QUERY_SUCCESS, null);
            } else {
                Page<Device> page = new Page<>();
                page.setPageNum(pageNum);
                page.setPageSize(pageSize);
                page.setTotal(total);
                page.setList(assmService.findPageDevices(pageNum, pageSize, keyword));
                return JsonMessage.successObj(Constants.QUERY_SUCCESS, page);
            }
        } catch (Exception e) {
            logger.error("分页查询设备资产数据出现错误！", e);
            return JsonMessage.error("分页查询设备资产数据出现错误！");
        }
    }

    /**
     * 添加设备
     *
     * @param device
     * @return
     */
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8", "application/x-www-form-urlencoded"}, method = RequestMethod.POST)
    public @ResponseBody
    Object add(AddDeviceVo device) {
        try {
            int result = assmService.addEntity(device);
            if (result >= 1)
                return JsonMessage.successMsg("添加设备数据成功");
        } catch (Exception e) {
            logger.error("添加设备数据失败", e);
        }
        return JsonMessage.successMsg("添加设备数据失败!");
    }

    /**
     * 编辑设备信息
     *
     * @return
     */
    @RequestMapping(value = "/edit", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public @ResponseBody
    Object edit(EditDeviceVo device) {
        try {
            if (device == null || device.getId() == null || device.getId() < 0) {
                return JsonMessage.error("请先选择设备！");
            }
            String result = assmService.updateEntity(device);
            if (result.trim().equals(ProtocolConstants.UPDATE_SUCCESS)) {
                return JsonMessage.successMsg("更新数据成功！");
            } else if (result.trim().equals(ProtocolConstants.DATA_NOT_FOUNT)) {
                return JsonMessage.error("不存在对应的设备数据！");
            } else if (result.trim().equals(ProtocolConstants.INPUT_PARAM_ERROR)) {
                return JsonMessage.error("传入的更新数据不正确！");
            }
        } catch (Exception e) {
            logger.error("更新设备信息失败", e);
        }
        return JsonMessage.error("更新设备数据失败！");
    }

    /**
     * 通过id获取单个设备信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById/{id}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public
    @ResponseBody
    Object findById(@PathVariable(value = "id") Long id) {
        try {
            return JsonMessage.successObj("设备信息查询成功！", assmService.findOneById(id));
        } catch (Exception e) {
            logger.error("通过id查找设备信息失败！", e);
            return JsonMessage.error("通过id查找设备信息失败！");
        }
    }

    /**
     * 删除设备信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    @Override
    public @ResponseBody
    Object delete(@PathVariable(value = "id") Long id) {
        Device device = assmService.findOneById(id);
        if (id == null || id.compareTo(new Long(0)) < 0 || device == null) {
            return JsonMessage.error("不存在id【" + id + "】对应的设备");
        }
        int result = assmService.deleteEntity(device);
        if (result >= 1)
            return JsonMessage.successMsg("删除设备信息成功！");
        return JsonMessage.error("删除设备信息失败！");
    }

    /**
     * 按照id串批量删除设备信息
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/batchDeleteByIds/{ids}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    @Override
    public @ResponseBody
    Object batchDeleteByIds(@PathVariable(value = "ids") String ids) {
        int result = assmService.batchDeleteByIds(ids);
        if (result >= 1 && result < ids.split(",").length) {
            return JsonMessage.successMsg("删除成功条数为【" + result + "】");
        }
        if (result == ids.split(",").length)
            return JsonMessage.successMsg("全部设备数据删除成功！");
        return JsonMessage.error("删除设备信息失败！");
    }

    /**
     * 按照id数组批量删除设备信息
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/batchDeleteByIdA", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    @Override
    public @ResponseBody
    Object batchDeleteByIdA(Long... ids) {
        int result = assmService.batchDeleteByIdA(ids);
        if (result >= 1 && result < ids.length) {
            return JsonMessage.successMsg("删除成功条数为【" + result + "】");
        }
        if (result == ids.length)
            return JsonMessage.successMsg("全部设备数据删除成功！");
        return JsonMessage.error("删除设备信息失败！");
    }
}
