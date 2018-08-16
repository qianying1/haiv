package cn.qhb.haiv.controller.api;

import cn.qhb.haiv.core.util.PoiUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件操作接口api
 */
@Api(value = "FileManagerController", description = "文件管理", tags = "FileManager")
public interface FileManagerApi {

    /**
     * 文件上传
     *
     * @return
     */
    @ApiOperation(value = "文件上传", notes = "文件上传", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 201, message = "已请求，未响应"),
            @ApiResponse(code = 401, message = "未授权的请求"),
            @ApiResponse(code = 403, message = "请求被禁止"),
            @ApiResponse(code = 404, message = "未找到请求资源")
    })
    Object upload(@ApiParam(name = "file", allowMultiple = true) MultipartFile file);

    @ApiOperation(value = "设备批量导出", notes = "设备批量导出", httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 201, message = "已请求，未响应"),
            @ApiResponse(code = 401, message = "未授权的请求"),
            @ApiResponse(code = 403, message = "请求被禁止"),
            @ApiResponse(code = 404, message = "未找到请求资源")
    })
    Object batchExport(@ApiParam(name = "ids", type = "string", value = "设备id串") String ids, @ApiParam(name = "fileName", type = "string", value = "文件名称", required = false) String fileName,
                       @ApiParam(name = "version", type = "string", value = "导出的版本", required = false, defaultValue = "old") PoiUtils.ExcelVersion version,
                       final HttpServletResponse response);

    /**
     * 下载2003版模板
     *
     * @return
     */
    @ApiOperation(value = "下载模板", notes = "下载模板", httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 201, message = "已请求，未响应"),
            @ApiResponse(code = 401, message = "未授权的请求"),
            @ApiResponse(code = 403, message = "请求被禁止"),
            @ApiResponse(code = 404, message = "未找到请求资源")
    })
    void downloadTemplate(@ApiParam(name = "fileName", type = "string", value = "自定义文件名称") String fileName,
                          @ApiParam(name = "version", value = "excel版本", defaultValue = "old", type = "string") PoiUtils.ExcelVersion version, final HttpServletResponse response);
}
