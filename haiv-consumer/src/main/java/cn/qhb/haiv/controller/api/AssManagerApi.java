package cn.qhb.haiv.controller.api;

import cn.qhb.haiv.model.vo.AddDeviceVo;
import cn.qhb.haiv.model.vo.EditDeviceVo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备资产管理模块API
 */
@RestController
@Api(value = "AssManagerController", description = "资产管理", tags = "AssManager")
@RequestMapping(value = "/assm", produces = {"application/json;charset=UTF-8", "application/x-www-form-urlencoded"})
public interface AssManagerApi {
    /**
     * 设备分页列表
     *
     * @return
     */
    @ApiOperation(value = "设备列表", notes = "展示设备列表"/*,tags = "资产，添加"*/, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "int", paramType = "path", value = "页码", required = false, defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", dataType = "int", paramType = "path", value = "页面大小", defaultValue = "15", required = false),
            @ApiImplicitParam(name = "keyword", dataType = "string", paramType = "path", value = "搜索关键字", required = false)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 201, message = "已请求，未响应"),
            @ApiResponse(code = 401, message = "未授权的请求"),
            @ApiResponse(code = 403, message = "请求被禁止"),
            @ApiResponse(code = 404, message = "未找到请求资源"),
            @ApiResponse(code = 400, message = "请求参数不正确")
    })
    Object list(int pageNum, int pageSize, String keyword);

    /**
     * 添加设备
     *
     * @param device
     * @return
     */
    @ApiOperation(value = "添加设备", notes = "向设备资产数据中添加资产设备"/*,tags = "资产，添加"*/, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", dataType = "string", paramType = "form", value = "名称"),
            @ApiImplicitParam(name = "assetModel", dataType = "string", paramType = "form", value = "设备型号"),
            @ApiImplicitParam(name = "cpuModel", dataType = "string", paramType = "form", value = "cpu型号"),
            @ApiImplicitParam(name = "manufacturer", dataType = "string", paramType = "form", value = "厂商"),
            @ApiImplicitParam(name = "seriesNum", dataType = "string", paramType = "form", value = "序列号"),
            @ApiImplicitParam(name = "operaSys", dataType = "string", paramType = "form", value = "操作系统"),
            @ApiImplicitParam(name = "proIp", dataType = "string", paramType = "form", value = "生产ip"),
            @ApiImplicitParam(name = "ofbVersion", dataType = "string", paramType = "form", value = "带外版本"),
            @ApiImplicitParam(name = "ofbIp", dataType = "string", paramType = "form", value = "带外ip"),
            @ApiImplicitParam(name = "respMan", dataType = "string", paramType = "form", value = "负责人"),
            @ApiImplicitParam(name = "status", dataType = "string", paramType = "form", value = "状态：在用/下线[online/offline]，默认为offline"),
            @ApiImplicitParam(name = "usage", dataType = "string", paramType = "form", value = "用途"),
            @ApiImplicitParam(name = "dataStatus", dataType = "string", paramType = "form", value = "信息状态：填报状态，缺/ok[lack/ok]；默认为lack"),
            @ApiImplicitParam(name = "ofd", dataType = "string", paramType = "form", format = "datetime", example = "yyyy-MM-dd HH:mm:ss", value = "过保时间", required = true),
            @ApiImplicitParam(name = "dofd", dataType = "int", paramType = "form", value = "过保天数"),
            @ApiImplicitParam(name = "cabinetId", dataType = "long", paramType = "form", value = "所属机柜的id", required = true),
            @ApiImplicitParam(name = "capacity", dataType = "string", paramType = "form", value = "硬盘容量（以G为单位）"),
            @ApiImplicitParam(name = "internMemory", dataType = "string", paramType = "form", value = "内存容量（以G为单位）"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 201, message = "已请求，未响应"),
            @ApiResponse(code = 401, message = "未授权的请求"),
            @ApiResponse(code = 403, message = "请求被禁止"),
            @ApiResponse(code = 404, message = "未找到请求资源"),
            @ApiResponse(code = 400, message = "请求参数错误")
    })
    Object add(AddDeviceVo device);

    /**
     * 编辑设备信息
     *
     * @return
     */
    @ApiOperation(value = "编辑设备信息", notes = "编辑设备资产信息"/*,tags = "资产，添加"*/, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "long", paramType = "form", value = "主键", required = true),
            @ApiImplicitParam(name = "name", dataType = "string", paramType = "form", value = "名称"),
            @ApiImplicitParam(name = "assetModel", dataType = "string", paramType = "form", value = "设备型号"),
            @ApiImplicitParam(name = "cpuModel", dataType = "string", paramType = "form", value = "cpu型号"),
            @ApiImplicitParam(name = "manufacturer", dataType = "string", paramType = "form", value = "厂商"),
            @ApiImplicitParam(name = "seriesNum", dataType = "string", paramType = "form", value = "序列号"),
            @ApiImplicitParam(name = "operaSys", dataType = "string", paramType = "form", value = "操作系统"),
            @ApiImplicitParam(name = "proIp", dataType = "string", paramType = "form", value = "生产ip"),
            @ApiImplicitParam(name = "ofbVersion", dataType = "string", paramType = "form", value = "带外版本"),
            @ApiImplicitParam(name = "ofbIp", dataType = "string", paramType = "form", value = "带外ip"),
            @ApiImplicitParam(name = "respMan", dataType = "string", paramType = "form", value = "负责人"),
            @ApiImplicitParam(name = "status", dataType = "string", paramType = "form", value = "状态：在用/下线[online/offline]，默认为offline"),
            @ApiImplicitParam(name = "usage", dataType = "string", paramType = "form", value = "用途"),
            @ApiImplicitParam(name = "dataStatus", dataType = "string", paramType = "form", value = "信息状态：填报状态，缺/ok[lack/ok]；默认为lack"),
            @ApiImplicitParam(name = "ofd", dataType = "string", paramType = "form", format = "date", value = "过保时间"),
            @ApiImplicitParam(name = "dofd", dataType = "int", paramType = "form", value = "过保天数"),
            @ApiImplicitParam(name = "cabinetId", dataType = "long", paramType = "form", value = "所属机柜的id"),
            @ApiImplicitParam(name = "capacity", dataType = "string", paramType = "form", value = "硬盘容量（以G为单位）"),
            @ApiImplicitParam(name = "internMemory", dataType = "string", paramType = "form", value = "内存容量（以G为单位）"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 201, message = "已请求，未响应"),
            @ApiResponse(code = 401, message = "未授权的请求"),
            @ApiResponse(code = 403, message = "请求被禁止"),
            @ApiResponse(code = 404, message = "未找到请求资源")
    })
    Object edit(EditDeviceVo device);

    /**
     * 获取单个设备信息
     *
     * @return
     */
    @ApiOperation(value = "获取单个设备信息", notes = "获取单个设备资产信息"/*,tags = "资产，添加"*/, httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 201, message = "已请求，未响应"),
            @ApiResponse(code = 401, message = "未授权的请求"),
            @ApiResponse(code = 403, message = "请求被禁止"),
            @ApiResponse(code = 404, message = "未找到请求资源")
    })
    Object findById(@ApiParam(name = "id", type = "int64") Long id);

    /**
     * 删除设备信息
     *
     * @return
     */
    @ApiOperation(value = "删除设备信息", notes = "删除设备资产信息"/*,tags = "资产，添加"*/, httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 201, message = "已请求，未响应"),
            @ApiResponse(code = 401, message = "未授权的请求"),
            @ApiResponse(code = 403, message = "请求被禁止"),
            @ApiResponse(code = 404, message = "未找到请求资源")
    })
    Object delete(@ApiParam(name = "id", type = "int64") Long deviceId);

    /**
     * 按照id串批量删除设备信息
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "按照id串批量删除设备信息",notes = "批量删除设备信息",httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 201, message = "已请求，未响应"),
            @ApiResponse(code = 401, message = "未授权的请求"),
            @ApiResponse(code = 403, message = "请求被禁止"),
            @ApiResponse(code = 404, message = "未找到请求资源")
    })
    Object batchDeleteByIds(@ApiParam(name="ids",type = "string",value = "id串") String ids);

    /**
     * 按照id数组批量删除设备信息
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "按照id数组批量删除设备信息",notes = "批量删除设备信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int64", paramType = "array", value = "设备id数组", required = false),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 201, message = "已请求，未响应"),
            @ApiResponse(code = 401, message = "未授权的请求"),
            @ApiResponse(code = 403, message = "请求被禁止"),
            @ApiResponse(code = 404, message = "未找到请求资源")
    })
    Object batchDeleteByIdA(Long... ids);
}
