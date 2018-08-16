package cn.qhb.haiv.controller.common;

import cn.qhb.haiv.controller.api.FileManagerApi;
import cn.qhb.haiv.core.exceptions.ArrayFieldsNotEqualException;
import cn.qhb.haiv.core.util.*;
import cn.qhb.haiv.model.Device;
import cn.qhb.haiv.model.File;
import cn.qhb.haiv.service.assm.AssmService;
import cn.qhb.haiv.service.common.FileManagerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件操作
 */
@RestController(value = "fileManagerController")
@RequestMapping(value = "/file", produces = {"application/json;charset=UTF-8", "application/x-www-form-urlencoded"})
public class FileManagerController implements FileManagerApi {
    private static Logger logger = LoggerFactory.getLogger(FileManagerController.class);

    @Reference(version = "1.0.0")
    private FileManagerService fileManagerService;
    @Reference(version = "1.0.0")
    private AssmService assmService;

    /**
     * 表格数据
     */
    private Map<String, List<PoiRow>> excelData;
    /**
     * 表格中单元格长度
     */
    private Map<String, List<Integer>> cellLengths;
    /**
     * 导出表格所存放的路径
     */
    private String filePath;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", produces = "application/json;charset=UTF-8")
    @Override
    public @ResponseBody
    Object upload(MultipartFile file) {
        try {
            if (file == null || file.isEmpty() || file.getBytes() != null && file.getBytes().length <= 0) {
                return JsonMessage.error("请先选择上传的文件！");
            }
        } catch (IOException ioe) {
            logger.error("文件上传出错！", ioe);
            return JsonMessage.error("文件上传出错！");
        }
        //文件后缀验证
        String originName = file.getOriginalFilename();
        if (!FileUtils.fileAllowedWithExcelType(originName)) {
            return JsonMessage.error("不允许该文件类型！文件名为【" + originName + "】");
        }
        //文件大小验证
        Long size = file.getSize();
        if (!FileUtils.fileSizeAllowedWithExcelType(size)) {
            return JsonMessage.error("不允许的文件长度！长度为【" + (size.doubleValue() / (1024 * 1024)) + "MB】文件限制大小为【" + Constants.EXCEL_FILE_MAX_SIZE.doubleValue() / (1024 * 1024) + "MB】");
        }
        InputStream is;
        try {
            is = file.getInputStream();
        } catch (IOException ioe) {
            logger.error("获取文件输入流错误！", ioe);
            return JsonMessage.error("上传文件出错！");
        }
        /**
         * 文件名称为UUID+文件后缀
         */
        String fileSaveName = FileUtils.generateRandomUUID() + originName.substring(originName.lastIndexOf("."), originName.length());
        FileUtils fileUtils = new FileUtils();
        String path;
        String middlePath = "assm" + java.io.File.separator + DateUtils.currentDateMonthStr();
        boolean fileUploaded = false;
        try {
            path = fileUtils.uploadFile(middlePath, fileSaveName, is);
            if (path == null || path.trim().equals("")) {
                return JsonMessage.error("文件上传失败！");
            }
            fileUploaded = true;
        } catch (IOException ioe) {
            logger.error("上传文件出错！", ioe);
            return JsonMessage.error("上传文件出错！");
        }
        List<Device> devices = null;
        String msg;
        boolean fileDataSaved = false;
        try {
            devices = (List<Device>) PoiUtils.importSingleData(
                    Device.class,
                    Constants.FILE_BASE_BATH + java.io.File.separator + middlePath + java.io.File.separator + fileSaveName,
                    Constants.INPUT_DATA_ROW_START, Constants.INPUT_DATA_COLUMN_START,
                    originName.substring(originName.lastIndexOf(".") + 1, originName.length()).equals("xls") ? PoiUtils.ExcelVersion.old : PoiUtils.ExcelVersion.latest,
                    Constants.DEVICE_OUTPUT_TEMPLATE_FIELDS_EN);
            if (devices != null && devices.size() > 0) {
                int dataInert = assmService.addDevices(devices);
                fileDataSaved = fileManagerService.upload(path, originName, size, fileSaveName, File.FileType.excel);
                msg = fileDataSaved ? "文件上传成功！" : ("文件上传失败！" +
                        (dataInert == devices.size() ? "全部数据导入成功！" :
                                dataInert > 0 ? "文件上传成功，但是有【" + (devices.size() - dataInert) + "】条数据未导入成功！" : "数据导入失败！"));
            } else {
                msg = "导入的文件中没有设备数据！";
            }
        } catch (Exception e) {
            logger.error("导入设备数据信息失败！", e);
            return JsonMessage.error("导入设备数据信息失败！");
        } finally {
            try {
                if (fileUploaded) {
                    fileUtils.deleteFile(middlePath, fileSaveName);
                }
                if (fileDataSaved) {
                    fileManagerService.deleteByPathAndFileSaveName(path, fileSaveName);
                }
            } catch (Exception e) {
                logger.error("删除文件出错！", e);
            }
        }
        return JsonMessage.successMsg(msg);
    }

    /**
     * 批量设备导出
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/batchExportByIds/{ids}/{fileName}/{version}", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    @Override
    public @ResponseBody
    Object batchExport(@PathVariable(value = "ids") String ids, @PathVariable(value = "fileName", required = false) String fileName, @PathVariable(value = "version", required = false) PoiUtils.ExcelVersion version, final HttpServletResponse response) {
        System.out.println(ids);
        List<Device> devices = assmService.findListByIds(ids);
        System.out.println("devices size: " + devices.size());
        if (devices == null || devices.size() <= 0) {
            return JsonMessage.successMsg("【" + ids + "】中都不存在设备数据信息！");
        }
        List<Integer> cellLens = new ArrayList<>();
        for (int i = 0; i < Constants.DEVICE_OUTPUT_DATA_FIELDS_CN.length; i++) {
            cellLens.add(25);
        }
        String exportFilePath;
        boolean fileOutputted = false;
        try {
            exportFilePath = PoiUtils.dataBatchExport(devices, Device.class, fileName,
                    PoiUtils.ExcelVersion.old, Constants.DEVICE_OUTPUT_DATA_FIELDS_CN, cellLens,
                    Constants.EXCEL_EXPORT_FILES_PATH + java.io.File.separator + fileName + (version == PoiUtils.ExcelVersion.old ? ".xls" : ".xlsx"),
                    Constants.DEVICE_OUTPUT_DATA_FIELDS_EN);
            if (exportFilePath == null || exportFilePath.trim().equals("")) {
                return JsonMessage.error("设备数据批量导出失败！");
            }
            fileOutputted = FileUtils.outputFile(response, exportFilePath, fileName);
        } catch (Exception e) {
            logger.error("设备数据信息批量导出错误！", e);
            return JsonMessage.error("设备数据信息批量导出错误！");
        }
        if (fileOutputted) {
            FileUtils.deleteFile(exportFilePath);
            return JsonMessage.successMsg("设备数据批量导出成功！");
        }
        return JsonMessage.successMsg("设备数据批量导出失败！");
    }

    /**
     * 下载模板
     *
     * @param fileName
     * @return
     */
    @RequestMapping(value = "/downloadTemplate/{fileName}/{version}")
    public @ResponseBody
    void downloadTemplate(@PathVariable(name = "fileName") String fileName, @PathVariable(name = "version", required = false) PoiUtils.ExcelVersion version, final HttpServletResponse response) {
        try {
            exportTemplateExcel(version, response, fileName);
        } catch (Exception e) {
            logger.error("导出" + (version == PoiUtils.ExcelVersion.old ? "2003版" : "2007版") + "模板异常！", e);
        }
    }

    /**
     * 初始化需要导出表格的初始化参数
     *
     * @param version
     */
    private void exportTemplateExcel(PoiUtils.ExcelVersion version, final HttpServletResponse response, String fileName) throws Exception {
        this.excelData = new HashMap<>();
        this.cellLengths = new HashMap<>();
        this.filePath = Constants.EXCEL_EXPORT_FILES_PATH + java.io.File.separator + fileName + (version == PoiUtils.ExcelVersion.old ? ".xls" : ".xlsx");
        List<PoiRow> sheetData = new ArrayList<>();
        excelData.put(fileName, sheetData);
        List<Integer> lengths = new ArrayList<>();
        String[] exportFieldsCns = Constants.DEVICE_OUTPUT_TEMPLATE_FIELDS_CN;
        String[] exportFieldsEns = Constants.DEVICE_OUTPUT_TEMPLATE_FIELDS_EN;
        if (exportFieldsCns.length != exportFieldsEns.length) {
            throw new ArrayFieldsNotEqualException("需要导出的字段中文名称与英文名称不对称！");
        }
        PoiRow node = new PoiRow();
        List<ColumnNode> columnNodes = new ArrayList<>();
        node.setType(PoiRow.RowType.title);
        int i = 0;
        for (String exportFieldsEn : exportFieldsEns) {
            lengths.add(20);
            ColumnNode columnNode;
            if (ModelUtils.isEnum(Device.class, exportFieldsEn)) {
                String[] enumVals = ModelUtils.getEnumArray(Device.class, exportFieldsEn);
                columnNode = new ColumnNode(enumVals.length, exportFieldsCns[i++], enumVals);
            } else {
                columnNode = new ColumnNode(exportFieldsCns[i++]);
            }
            columnNodes.add(columnNode);
        }
        node.setRowData(columnNodes);
        sheetData.add(node);
        this.cellLengths.put(fileName, lengths);
        String exportFilePath = null;
        try {
            exportFilePath = PoiUtils.exportExcelWithCenterStyle(this.excelData, this.cellLengths, this.filePath, version);
            FileUtils.outputFile(response, exportFilePath, fileName);
        } catch (Exception e) {
            logger.error("导出" + (version == PoiUtils.ExcelVersion.old ? "旧" : "新") + "版excel模板文件失败！", e);
        } finally {
            if (exportFilePath != null && !exportFilePath.trim().equals("")) {
                FileUtils.deleteFile(exportFilePath);
            }
        }
    }

}
