package cn.qhb.haiv.core.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;

public class PoiDropDownListUtil {

    public static void main(String[] args) throws Exception {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("导入模板");

        // 第一行
        Row row = sheet.createRow(0);
        /*CellStyle style = CatalogExcelUtil.getHeadStyle(wb);

        CatalogExcelUtil.initCell(row.createCell(0), style, "第1列列头");
        CatalogExcelUtil.initCell(row.createCell(1), style, "第2列列头");
        CatalogExcelUtil.initCell(row.createCell(2), style, "部门");
        CatalogExcelUtil.initCell(row.createCell(3), style, "层级");
        CatalogExcelUtil.initCell(row.createCell(4), style, "第5列列头");
        CatalogExcelUtil.initCell(row.createCell(5), style, "第6列列头");*/

        // 设置部门
        String[] departSelectList = new String[]{"刘德华", "张学友", "黎明", "郭富城", "金城武", "梁朝伟"};
        // 第3列的第1行到第21行单元格部门下拉 ，可替换为从数据库的部门表数据，
        // hidden_depart 为隐藏的sheet的别名，1为这个sheet的索引 ，考虑到有多个列绑定下拉列表
        dropDownList2007(wb, sheet, departSelectList, 1, 20, 2, 2, "hidden_depart", 1);

        // 设置层级
        String[] levelSelectList = new String[1000];//{"科比", "詹姆斯", "库里", "麦迪", "艾弗森"};
        for (int i = 0; i < levelSelectList.length; i++) {
            levelSelectList[i] = "levelSelectList " + i;
        }
        dropDownList2007(wb, sheet, levelSelectList, 1, 1000, 3, 3, "hidden_level", 2);
        FileOutputStream stream = new FileOutputStream("d:\\success9.xlsx");
        wb.write(stream);
        stream.close();
    }

    /**
     * 2003版excel下拉列表框
     *
     * @param wb               HSSFWorkbook对象
     * @param realSheet        需要操作的sheet对象
     * @param datas            下拉的列表数据
     * @param startRow         开始行
     * @param endRow           结束行
     * @param startCol         开始列
     * @param endCol           结束列
     * @param hiddenSheetName  隐藏的sheet名
     * @param hiddenSheetIndex 隐藏的sheet索引
     * @return
     * @throws Exception
     */
    public static void dropDownList2003(Workbook wb, Sheet realSheet, String[] datas, int startRow, int endRow,
                                        int startCol, int endCol, String hiddenSheetName, int hiddenSheetIndex)
            throws Exception {

        HSSFWorkbook workbook = (HSSFWorkbook) wb;
        // 创建一个数据源sheet
        HSSFSheet hidden = workbook.createSheet(hiddenSheetName);
        // 数据源sheet页不显示
        workbook.setSheetHidden(hiddenSheetIndex, true);
        // 将下拉列表的数据放在数据源sheet上
        HSSFRow row = null;
        HSSFCell cell = null;
        for (int i = 0, length = datas.length; i < length; i++) {
            row = hidden.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(datas[i]);
        }
        // 指定下拉数据时，给定目标数据范围 hiddenSheetName!$A$1:$A5   隐藏sheet的A1到A5格的数据
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(hiddenSheetName + "!$A$1:$A" + datas.length);
        CellRangeAddressList addressList = null;
        HSSFDataValidation validation = null;
        // 单元格样式
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 循环指定单元格下拉数据
        for (int i = startRow; i <= endRow; i++) {
            row = (HSSFRow) realSheet.createRow(i);
            cell = row.createCell(startCol);
            cell.setCellStyle(style);
            addressList = new CellRangeAddressList(i, i, startCol, endCol);
            validation = new HSSFDataValidation(addressList, constraint);
            realSheet.addValidationData(validation);
        }
    }

    /**
     * @param wb               XSSFWorkbook对象
     * @param realSheet        需要操作的sheet对象
     * @param datas            下拉的列表数据
     * @param startRow         开始行
     * @param endRow           结束行
     * @param startCol         开始列
     * @param endCol           结束列
     * @param hiddenSheetName  隐藏的sheet名
     * @param hiddenSheetIndex 隐藏的sheet索引
     * @return
     * @throws Exception
     */
    public static void dropDownList2007(Workbook wb, Sheet realSheet, String[] datas, int startRow, int endRow,
                                        int startCol, int endCol, String hiddenSheetName, int hiddenSheetIndex)
            throws Exception {
        if (wb == null) {
            return;
        }
        XSSFWorkbook workbook = (XSSFWorkbook) wb;
        // 创建一个数据源sheet
        XSSFSheet hidden = workbook.createSheet(hiddenSheetName);
        // 数据源sheet页不显示
        workbook.setSheetHidden(hiddenSheetIndex, true);
        // 将下拉列表的数据放在数据源sheet上
        XSSFRow row;
        XSSFCell cell;
        for (int i = 0, length = datas.length; i < length; i++) {
            row = hidden.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(datas[i]);
        }
        // A1 到 Adatas.length 表示第一列的第一行到datas.length行，需要与前一步生成的隐藏的数据源sheet数据位置对应
        CellRangeAddressList addressList = null;
        DataValidation validation = null;
        DataValidationConstraint dvConstraint = null;
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(hidden);
        dvConstraint = dvHelper.createFormulaListConstraint(hiddenSheetName + "!$A$1:$A" + datas.length);
        // 单元格样式
        CellStyle style = workbook.createCellStyle();
        XSSFDataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("@"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 循环指定单元格下拉数据
        for (int i = startRow; i <= endRow; i++) {
            row = (XSSFRow) realSheet.createRow(i);
            cell = row.createCell(startCol);
            cell.setCellStyle(style);
            addressList = new CellRangeAddressList(i, i, startCol, endCol);
            validation = dvHelper.createValidation(
                    dvConstraint, addressList);
            realSheet.addValidationData(validation);
        }
    }
}