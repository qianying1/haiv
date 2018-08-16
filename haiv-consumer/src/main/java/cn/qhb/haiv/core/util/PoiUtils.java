package cn.qhb.haiv.core.util;

import cn.qhb.haiv.core.exceptions.CellLensValNullException;
import cn.qhb.haiv.core.exceptions.ExcelDataOrFilePathException;
import cn.qhb.haiv.model.Device;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.*;

/**
 * poi解释工具
 */
public class PoiUtils {
    private static Logger logger = LoggerFactory.getLogger(PoiUtils.class);

    /**
     * 使用excel的版本
     */
    public enum ExcelVersion {
        latest,
        old,
    }

    /**
     * 单表数据导入（开始的行标识为-1则该类型行不存在）
     *
     * @param targetClass     目标解释类型
     * @param filePath        excel文件所在路径
     * @param dataRowStart    数据开始的行
     * @param dataColumnStart 数据开始的列
     * @param version         所需要进行数据解释的excel版本
     * @param fields          需要解释的字段
     * @return
     */
    public static List<?> importSingleData(@NotNull Class<?> targetClass, @NotNull String filePath, int dataRowStart, int dataColumnStart, @NotNull ExcelVersion version, String... fields) throws IOException {
        if (filePath.trim().equals("") || fields == null || fields.length <= 0) {
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        InputStream is = null;
        List<Object> result = null;
        try {
            is = new FileInputStream(file);
            Map<String, Class<?>[]> setterParameterTypesMap = ModelUtils.forClassMethodsParameterTypes(targetClass);
            result = new ArrayList<>();
            if (version == ExcelVersion.old) {
                dataImport2003(result, targetClass, setterParameterTypesMap, is, dataRowStart, dataColumnStart, fields);
            } else {
                dataImport2007(result, targetClass, setterParameterTypesMap, is, dataRowStart, dataColumnStart, fields);
            }
        } catch (Exception e) {
            logger.error("excel数据导入出错！", e);
            return null;
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return result;
    }

    /**
     * 2003版excel数据导入
     *
     * @param objects
     * @param clazz
     * @param setterParameterTypesMap
     * @param is
     * @param dataRowStart
     * @param dataColumnStart
     * @param fields
     * @throws Exception
     */
    private static void dataImport2003(List<Object> objects, Class<?> clazz, Map<String, Class<?>[]> setterParameterTypesMap, InputStream is, int dataRowStart, int dataColumnStart, String... fields) throws Exception {
        if (objects == null || fields == null || fields.length <= 0 || clazz == null || setterParameterTypesMap.keySet().size() <= 0) {
            return;
        }
        Object entity;
        HSSFWorkbook workbook = new HSSFWorkbook(is);
        Iterator iterator = workbook.sheetIterator();
        int columnCount = fields.length;
        while (iterator.hasNext()) {
            HSSFSheet sheet = (HSSFSheet) iterator.next();
            Iterator rowI = sheet.rowIterator();
            int rowIndex = 0;
            int rowNullCount = 0;
            while (rowI.hasNext()) {
                if (rowNullCount >= 50) {
                    break;
                }
                HSSFRow row = (HSSFRow) rowI.next();
                rowIndex++;
                if (rowIndex < dataRowStart) {
                    continue;
                }
                entity = newInstance(clazz);
                if (entity == null) {
                    return;
                }
                Iterator cellI = row.cellIterator();
                int columnIndex = dataColumnStart, fieldIndex = 0;
                while (cellI.hasNext()) {
                    HSSFCell cell = (HSSFCell) cellI.next();
                    if (columnIndex > columnCount) {
                        break;
                    }
                    columnIndex++;
                    Object cellVal = cell.toString();
                    Class<?> paramType = setterParameterTypesMap.get(fields[fieldIndex].toLowerCase())[0];
                    logger.info("正在解释excel数据： cell value: " + cellVal + " paramType: " + paramType);
                    Object val = ModelUtils.forValueNotDataFormat(paramType, cell, cellVal);
                    ModelUtils.getSetterMethod(clazz, fields[fieldIndex++], paramType).invoke(entity, val);
                }
                if (columnIndex >= columnCount) {
                    objects.add(entity);
                } else {
                    rowNullCount++;
                }
            }
        }
    }

    /**
     * 2007版excel数据导入
     *
     * @param objects
     * @param clazz
     * @param setterParameterTypesMap
     * @param is
     * @param dataRowStart
     * @param dataColumnStart
     * @param fields
     * @throws Exception
     */
    private static void dataImport2007(List<Object> objects, Class<?> clazz, Map<String, Class<?>[]> setterParameterTypesMap, InputStream is, int dataRowStart, int dataColumnStart, String... fields) throws Exception {
        if (objects == null || fields == null || fields.length <= 0 || clazz == null || setterParameterTypesMap.keySet().size() <= 0) {
            return;
        }
        Object entity;
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        Iterator iterator = workbook.sheetIterator();
        int columnCount = fields.length;
        while (iterator.hasNext()) {
            XSSFSheet sheet = (XSSFSheet) iterator.next();
            Iterator rowI = sheet.rowIterator();
            int rowIndex = 0;
            int rowNullCount = 0;
            while (rowI.hasNext()) {
                if (rowNullCount >= 50) {
                    break;
                }
                XSSFRow row = (XSSFRow) rowI.next();
                rowIndex++;
                if (rowIndex < dataRowStart) {
                    continue;
                }
                entity = newInstance(clazz);
                if (entity == null) {
                    return;
                }
                Iterator cellI = row.cellIterator();
                int columnIndex = dataColumnStart, fieldIndex = 0;
                while (cellI.hasNext()) {
                    XSSFCell cell = (XSSFCell) cellI.next();
                    if (columnIndex > columnCount) {
                        break;
                    }
                    columnIndex++;
                    Object cellVal = cell.toString();
                    Class<?> paramType = setterParameterTypesMap.get(fields[fieldIndex].toLowerCase())[0];
                    logger.info("正在解释excel数据： cell value: " + cellVal + " paramType: " + paramType);
                    Object val = ModelUtils.forValueNotDataFormat(paramType, cell, cellVal);
                    ModelUtils.getSetterMethod(clazz, fields[fieldIndex++], paramType).invoke(entity, val);
                }
                if (columnIndex >= columnCount) {
                    objects.add(entity);
                } else {
                    rowNullCount++;
                }
            }
        }
    }

    /**
     * 导出excel表格文件
     *
     * @param excelData   string=> 表格名称，list=>表格内容（rowType=>行类型，list=>行数据）
     * @param cellLengths string=> 表格名称，list=>对应表格的单元格长度
     * @param filePath    保存导出文件的路径
     * @return
     */
    public static String exportExcelWithCenterStyle(@NotNull Map<String, List<PoiRow>> excelData, Map<String, List<Integer>> cellLengths, String filePath, @NotNull ExcelVersion version) throws Exception {
        if (excelData.isEmpty() || filePath == null || filePath.trim().equals("")) {
            throw new ExcelDataOrFilePathException("表格具体数据或者保存路径不能为空！");
        }
        if (version == ExcelVersion.old) {
            return export2003ExcelWithCenterStyle(excelData, cellLengths, filePath);
        } else {
            return export2007ExcelWithCenterStyle(excelData, cellLengths, filePath);
        }
    }

    /**
     * 导出旧版本的excel
     *
     * @param excelData
     * @param cellLengths
     * @param filePath
     * @return
     * @throws Exception
     */
    private static String export2003ExcelWithCenterStyle(@NotNull Map<String, List<PoiRow>> excelData, @NotNull Map<String, List<Integer>> cellLengths, String filePath) throws Exception {
        int rowIndex = 0;
        // 创建表以及设置样式
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook();
            Set<String> sheetNames = excelData.keySet();
            for (String sheetName : sheetNames) {
                HSSFSheet sheet = workbook.createSheet(sheetName);
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                List<Integer> cellLength = cellLengths.get(sheetName);
                if (cellLength == null) {
                    throw new CellLensValNullException("不存在对应表格名称为【" + sheetName + "】的单元格长度列表");
                }
                initCellLens(sheet, cellLength, ExcelVersion.old);
                List<PoiRow> sheetData = excelData.get(sheetName);
                for (PoiRow row : sheetData) {
                    if (row.getRowData() == null || row.getRowData().size() <= 0) {
                        continue;
                    }
                    PoiRow.RowType rowType = row.getType();
                    List<?> data = row.getRowData();
                    System.out.println(data);
                    HSSFRow cellRow = sheet.createRow(rowIndex++);
                    // 创建表头
                    if (rowType == PoiRow.RowType.title) {
                        for (int i = 0; i < data.size(); i++) {
                            HSSFFont font = workbook.createFont();
                            font.setFontHeightInPoints((short) 15);
                            font.setFontName("IMPACT");
                            font.setColor((short) 255);
                            cellStyle.setFont(font);
                            cellStyle.setBorderBottom(BorderStyle.DOUBLE);
                            cellStyle.setBorderTop(BorderStyle.DOUBLE);
                            cellStyle.setBorderLeft(BorderStyle.DOUBLE);
                            cellStyle.setBorderRight(BorderStyle.DOUBLE);
                            cellStyle.setBottomBorderColor((short) 255);
                            cellStyle.setTopBorderColor((short) 255);
                            cellStyle.setLeftBorderColor((short) 255);
                            cellStyle.setRightBorderColor((short) 255);
                            Object cellObj = data.get(i);
                            String cellVal = null;
                            if (cellObj instanceof ColumnNode) {
                                ColumnNode node = (ColumnNode) cellObj;
                                switch (node.getCellType()) {
                                    case string_:
                                        break;
                                    case list_:
                                        PoiDropDownListUtil.dropDownList2003(workbook, sheet, node.getListData(), rowIndex, node.getListRowLen(), i, i, "hidden_list" + i, 1);
                                        break;
                                    case date_:
                                        break;
                                    case number_:
                                        break;
                                    default:
                                        break;
                                }
                                cellVal = (String) node.getCellVal();
                            } else {
                                cellVal = (String) cellObj;
                            }
                            logger.info("导出单元格字段值为： " + cellVal);
                            writeXlsCell(cellRow, cellVal, i, cellStyle);
                        }
                    }
                    // 向表中添加数据
                    else if (rowType == PoiRow.RowType.data_) {
                        cellStyle = workbook.createCellStyle();
                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                        HSSFDataFormat format = workbook.createDataFormat();
                        cellStyle.setDataFormat(format.getFormat("@"));
                        for (int j = 0; j < data.size(); j++) {
                            logger.info("正在导出数据单元格，值为：" + data.get(j) + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                            writeXlsCell(cellRow, data.get(j).toString(), j, cellStyle);
                            // 写序号
                            //writeXlsCell(row, String.valueOf(j + 1), 0, cellStyle);
                        }
                    }
                }
            }
            String basePath = FileUtils.getBasePath(filePath);
            File file = new File(basePath);
            if (!file.isDirectory()) {
                file.mkdirs();
            }
            file = new File(filePath);
            if (file.exists()) {
                filePath = basePath + File.separator + FileUtils.generateRandomUUID() + "." + FileUtils.getFileExt(filePath);
                file = new File(filePath);
            }
            file.createNewFile();
            workbook.write(file);
            return filePath;
        } catch (Exception e) {
            logger.error("导出excel文件出错！", e);
            return "";
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    /**
     * 导出旧版本的excel
     *
     * @param excelData
     * @param cellLengths
     * @param filePath
     * @return
     * @throws Exception
     */
    private static String export2007ExcelWithCenterStyle(@NotNull Map<String, List<PoiRow>> excelData, @NotNull Map<String, List<Integer>> cellLengths, String filePath) throws Exception {
        int rowIndex = 0;
        // 创建表以及设置样式
        XSSFWorkbook workbook = null;
        OutputStream os = null;
        try {
            workbook = new XSSFWorkbook();
            Set<String> sheetNames = excelData.keySet();
            for (String sheetName : sheetNames) {
                XSSFSheet sheet = workbook.createSheet(sheetName);
                XSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                List<Integer> cellLength = cellLengths.get(sheetName);
                if (cellLength == null) {
                    throw new CellLensValNullException("不存在对应表格名称为【" + sheetName + "】的单元格长度列表");
                }
                initCellLens(sheet, cellLength, ExcelVersion.latest);
                List<PoiRow> sheetData = excelData.get(sheetName);
                for (PoiRow row : sheetData) {
                    if (row.getRowData() == null || row.getRowData().size() <= 0) {
                        continue;
                    }
                    PoiRow.RowType rowType = row.getType();
                    List<?> data = row.getRowData();
                    XSSFRow cellRow = sheet.createRow(rowIndex++);
                    // 创建表头
                    if (rowType == PoiRow.RowType.title) {
                        for (int i = 0; i < data.size(); i++) {
                            XSSFFont font = workbook.createFont();
                            font.setFontHeightInPoints((short) 15);
                            font.setFontName("IMPACT");
                            Color color = new XSSFColor();
                            ((XSSFColor) color).setTheme(1);
                            font.setColor(XSSFColor.toXSSFColor(color));
                            cellStyle.setFont(font);
                            cellStyle.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, (XSSFColor) color);
                            cellStyle.setBorderColor(XSSFCellBorder.BorderSide.TOP, (XSSFColor) color);
                            cellStyle.setBorderColor(XSSFCellBorder.BorderSide.LEFT, (XSSFColor) color);
                            cellStyle.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, (XSSFColor) color);
                            Object cellObj = data.get(i);
                            String cellVal = null;
                            if (cellObj instanceof ColumnNode) {
                                ColumnNode node = (ColumnNode) cellObj;
                                switch (node.getCellType()) {
                                    case string_:
                                        break;
                                    case list_:
                                        PoiDropDownListUtil.dropDownList2007(workbook, sheet, node.getListData(), rowIndex, node.getListRowLen(), i, i, "hidden_list" + i, 1);
                                        break;
                                    case date_:
                                        break;
                                    case number_:
                                        break;
                                    default:
                                        break;
                                }
                                cellVal = (String) node.getCellVal();
                            } else {
                                cellVal = (String) cellObj;
                            }
                            writeXlsxCell(cellRow, cellVal, i, cellStyle);
                        }
                    }
                    // 向表中添加数据
                    else if (rowType == PoiRow.RowType.data_) {
                        cellStyle = workbook.createCellStyle();
                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                        XSSFDataFormat format = workbook.createDataFormat();
                        cellStyle.setDataFormat(format.getFormat("@"));
                        for (int j = 0; j < data.size(); j++) {
                            writeXlsxCell(cellRow, data.get(j).toString(), j, cellStyle);
                        }
                    }
                }
            }
            String basePath = FileUtils.getBasePath(filePath);
            File file = new File(basePath);
            if (!file.isDirectory()) {
                file.mkdirs();
            }
            file = new File(filePath);
            if (file.exists()) {
                filePath = basePath + File.separator + FileUtils.generateRandomUUID() + "." + FileUtils.getFileExt(filePath);
                file = new File(filePath);
            }
            file.createNewFile();
            os = new FileOutputStream(file);
            workbook.write(os);
            return filePath;
        } catch (Exception e) {
            logger.error("导出excel文件出错！", e);
            return "";
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * 导出2003版excel表格工作簿
     *
     * @param title      表名
     * @param headers    数据表头标题
     * @param contents   表格数据，每一个String数组为一行
     * @param cellLength 每一列的宽度,宽度以字符为单位
     * @return
     */
    public static HSSFWorkbook exportXlsWithCenterStyle(String title, List<String> headers, List<ArrayList<String>> contents, List<Integer> cellLength) {

        int rowIndex = 0;
        // 创建表以及设置样式
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        initCellLens(sheet, cellLength, ExcelVersion.old);

        // 创建表头
        HSSFRow header = sheet.createRow(rowIndex++);
        for (int i = 0; i < headers.size(); i++) {
            writeXlsCell(header, headers.get(i), i, cellStyle);
        }

        // 向表中添加数据
        for (int j = 0; j < contents.size(); j++) {
            List<String> rows = contents.get(j);
            HSSFRow row = sheet.createRow(rowIndex++);
            // 写序号
//			writeXlsCell(row, String.valueOf(j + 1), 0, cellStyle);
            // 写内容
            for (int i = 0; i < rows.size(); i++) {
                writeXlsCell(row, rows.get(i), i, cellStyle);
            }
        }
        return workbook;
    }

    /**
     * 导出2007版excel表格工作簿
     *
     * @param title      表名
     * @param headers    数据表头标题
     * @param contents   表格数据，每一个String数组为一行
     * @param cellLength 每一列的宽度,宽度以字符为单位
     * @return
     */
    public static XSSFWorkbook exportXlsxWithCenterStyle(String title, List<String> headers, List<ArrayList<String>> contents, List<Integer> cellLength) {

        int rowIndex = 0;
        // 创建表以及设置样式
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(title);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        initCellLens(sheet, cellLength, ExcelVersion.latest);

        // 创建表头
        XSSFRow header = sheet.createRow(rowIndex++);
        for (int i = 0; i < headers.size(); i++) {
            writeXlsxCell(header, headers.get(i), i, cellStyle);
        }

        // 向表中添加数据
        for (int j = 0; j < contents.size(); j++) {
            List<String> rows = contents.get(j);
            XSSFRow row = sheet.createRow(rowIndex++);
            // 写序号
//			writeXlsCell(row, String.valueOf(j + 1), 0, cellStyle);
            // 写内容
            for (int i = 0; i < rows.size(); i++) {
                writeXlsxCell(row, rows.get(i), i, cellStyle);
            }
        }
        return workbook;
    }

    /**
     * 数据批量导出
     *
     * @param dataList  需要导出的数据
     * @param clazz     导出数据的解释实体类型
     * @param sheetName 需要进行导出到的excel页名称
     * @param version   需要导出到excel的版本
     * @param titles    导出数据的excel文件的标题名称列表
     * @param filePath  期望存放文件的地方
     * @param fields    需要导出的字段
     * @return string=>    最终导出文件后存放的地方
     */
    public static String dataBatchExport(List<?> dataList, Class<?> clazz, String sheetName, ExcelVersion version, String[] titles, List<Integer> cellLens, String filePath, String... fields) throws Exception {
        Map<String, List<PoiRow>> excelData = new HashMap<>();
        List<PoiRow> sheetData = new ArrayList<>();
        excelData.put(sheetName, sheetData);
        //标题
        PoiRow header = new PoiRow();
        header.setType(PoiRow.RowType.title);
        header.setRowData(Utils.arrayConvertToList(titles));
        sheetData.add(header);

        //列距
        Map<String, List<Integer>> cellLMap = new HashMap<>();
        cellLMap.put(sheetName, cellLens);

        //数据
        for (Object obj : dataList) {
            PoiRow row = new PoiRow();
            row.setType(PoiRow.RowType.data_);
            row.setRowData(getColumnNodelListData(clazz, obj, fields));   //ModelUtils.getModelValues(clazz, obj, fields)
            sheetData.add(row);
        }
        return exportExcelWithCenterStyle(excelData, cellLMap, filePath, version);
    }

    /**
     * 将数据按照poi单元格节点列表的方式返回
     *
     * @param clazz
     * @param entity
     * @param fields
     * @return
     */
    public static List<?> getColumnNodelListData(Class<?> clazz, Object entity, String... fields) throws Exception {
        List<Object> result = new ArrayList<>();
        for (String field : fields) {
            Object obj = ModelUtils.getTypeOfValue(clazz, field, entity);
            result.add(obj);
        }
        return result;
    }

    /**
     * 向2003版表格单元格中写入数据
     *
     * @param row
     * @param content
     * @param index
     * @param style
     */
    private static void writeXlsCell(HSSFRow row, String content, Integer index, HSSFCellStyle style) {
        HSSFCell cell = row.createCell(index);
        cell.setCellValue(content);
        cell.setCellStyle(style);
    }

    /**
     * 向2007版表格单元格中写入数据
     *
     * @param row
     * @param content
     * @param index
     * @param style
     */
    private static void writeXlsxCell(XSSFRow row, String content, Integer index, XSSFCellStyle style) {
        XSSFCell cell = row.createCell(index);
        cell.setCellValue(content);
        cell.setCellStyle(style);
    }

    /**
     * 目标对象创建
     *
     * @param clazz
     * @return
     */
    private static Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException iae) {
            logger.error("没有默认的构造函数！无法创建对象！", iae);
        } catch (InstantiationException ie) {
            logger.error("创建对象时出现错误！", ie);
        }
        return null;
    }

    /**
     * 初始化单元格长度
     *
     * @param sheet0
     * @param cellLength
     * @param version
     */
    private static void initCellLens(Sheet sheet0, List<Integer> cellLength, ExcelVersion version) {
        if (version == ExcelVersion.old) {
            HSSFSheet sheet = (HSSFSheet) sheet0;
            // 设置每列的宽度
            for (int i = 0; i < cellLength.size(); i++) {
                sheet.setColumnWidth(i, cellLength.get(i) * 256);
            }
        } else {
            XSSFSheet sheet = (XSSFSheet) sheet0;
            // 设置每列的宽度
            for (int i = 0; i < cellLength.size(); i++) {
                sheet.setColumnWidth(i, cellLength.get(i) * 256);
            }
        }
    }

    //test
    public static void main(String... args) throws Exception {
//        List<Device> devices = (List<Device>) importSingleData(Device.class, "f:\\download\\hello.xls", 2, 1, ExcelVersion.old, Constants.DEVICE_OUTPUT_TEMPLATE_FIELDS_EN);
//        System.out.println(devices);
        exportExcelTest();
    }

    /**
     * 导出模板测试
     *
     * @throws Exception
     */
    private static void exportExcelTest() throws Exception {
        Map<String, List<PoiRow>> excelData = new HashMap<>();
        String sheetName = "设备信息模板";
        List<PoiRow> sheetData = new ArrayList<>();
        excelData.put(sheetName, sheetData);
        //标题
        PoiRow header = new PoiRow();
        List<ColumnNode> titles = new ArrayList<>();
        //name,assetModel,cpuModel,manufacturer,seriesNum,operaSys,proIp,ofbVersion,ofbIp,respMan,status,usage,dataStatus,ofd,dofd,cahinetId,warnings,cabinet,capacity,internMemory,alterTime,insertTime
        titles.add(new ColumnNode("名称：用户自定义名称"));
        titles.add(new ColumnNode("设备型号"));
        titles.add(new ColumnNode("cpu型号"));
        titles.add(new ColumnNode("厂商"));
        titles.add(new ColumnNode("序列号"));
        titles.add(new ColumnNode("操作系统"));
        titles.add(new ColumnNode("生产ip"));
        titles.add(new ColumnNode("带外版本"));
        titles.add(new ColumnNode("带外ip"));
        titles.add(new ColumnNode("负责人"));
        titles.add(new ColumnNode(1000, "状态：在用/下线", Device.Status.online.toString(), Device.Status.offline.toString()));
        titles.add(new ColumnNode("用途"));
        titles.add(new ColumnNode(1000, "信息状态：填报状态，缺/ok", Device.DataStatus.lack.toString(), Device.DataStatus.ok.toString()));
        titles.add(new ColumnNode("过保日期：yyyy/mm/dd", 1000));
        titles.add(new ColumnNode(1000, "过保天数"));
        titles.add(new ColumnNode("机柜id"));
        titles.add(new ColumnNode("硬盘容量（以G为单位）"));
        titles.add(new ColumnNode("内存容量（以G为单位）"));
        header.setType(PoiRow.RowType.title);
        header.setRowData(titles);
        sheetData.add(header);

        //列距
        List<Integer> cellLens = new ArrayList<>();
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        cellLens.add(25);
        Map<String, List<Integer>> cellLMap = new HashMap<>();
        cellLMap.put(sheetName, cellLens);

        //数据
        /*PoiRow dataMap = new PoiRow();
        List<String> datas = new ArrayList<>();
        //name,assetModel,cpuModel,manufacturer,seriesNum,operaSys,proIp,ofbVersion,ofbIp,respMan,status,usage,dataStatus,ofd,dofd,cahinetId,warnings,cabinet,capacity,internMemory,alterTime,insertTime
        datas.add("网络云端服务器");
        datas.add("16546456464145613135153112513211321032");
        datas.add("654641561053103103213123102312313131");
        datas.add("广州申迪计算机有限公司");
        datas.add("41556415153131031023102313213213131");
        datas.add("windows 7");
        datas.add("127.2.3.5");
        datas.add("v1.0.0.1");
        datas.add("125.36.5.3");
        datas.add("李四");
        datas.add("online");
        datas.add("部署服务器");
        datas.add("lack");
        datas.add("2018-05-06 12:23:12");
        datas.add("30");
        datas.add("3");
        datas.add("12");
        datas.add("25");
        dataMap.setType(PoiRow.RowType.data_);
        dataMap.setRowData(datas);*/
//        sheetData.add(dataMap);

        exportExcelWithCenterStyle(excelData, cellLMap, "f:\\download\\hello.xls", ExcelVersion.old);
    }
}
