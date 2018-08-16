package cn.qhb.haiv.core.util;

import java.util.Arrays;

/**
 * 单元格节点
 */
public class ColumnNode {

    /**
     * 单元格类型
     */
    private CellType cellType;
    /**
     * 单元格数据
     */
    private Object cellVal;
    /**
     * 列表结束行
     */
    private int listRowLen;
    /**
     * 日期类型数据结束行
     */
    private int dateRowLen;
    /**
     * 数字类型数据结束行
     */
    private int numberRowLen;
    /**
     * 下拉列表数据
     */
    private String[] listData;

    public enum CellType {
        /**
         * 字符串
         */
        string_,
        /**
         * 枚举
         */
        list_,
        /**
         * 数字
         */
        number_,
        /**
         * 时间
         */
        date_
    }

    /**
     * 为所有类型的列
     *
     * @param cellVal
     */
    public ColumnNode(Object cellVal) {
        this.cellType = CellType.string_;
        this.cellVal = cellVal;
    }

    /**
     * 当当前列全部为下拉列表时使用
     *
     * @param listRowLen
     * @param cellVal
     * @param listData
     */
    public ColumnNode(int listRowLen, Object cellVal, String... listData) {
        this.cellType = CellType.list_;
        this.listRowLen = listRowLen;
        this.cellVal = cellVal;
        this.listData = listData;
    }

    /**
     * 当当前列全部为时间类型时使用
     *
     * @param cellVal
     * @param dateRowLen
     */
    public ColumnNode(Object cellVal, int dateRowLen) {
        this.cellType = CellType.date_;
        this.dateRowLen = dateRowLen;
        this.cellVal = cellVal;
    }

    /**
     * 当当前列全部为数字类型时使用
     *
     * @param cellVal
     * @param numberRowLen
     */
    public ColumnNode(int numberRowLen, Object cellVal) {
        this.cellType = CellType.number_;
        this.numberRowLen = numberRowLen;
        this.cellVal = cellVal;
    }

    public CellType getCellType() {
        return cellType;
    }

    public Object getCellVal() {
        return cellVal;
    }

    public int getListRowLen() {
        return listRowLen;
    }

    public int getDateRowLen() {
        return dateRowLen;
    }

    public int getNumberRowLen() {
        return numberRowLen;
    }

    public String[] getListData() {
        return listData;
    }

    @Override
    public String toString() {
        return "ColumnNode{" +
                "cellType=" + cellType +
                ", cellVal=" + cellVal +
                ", listRowLen=" + listRowLen +
                ", dateRowLen=" + dateRowLen +
                ", numberRowLen=" + numberRowLen +
                ", listData=" + Arrays.toString(listData) +
                '}';
    }
}
