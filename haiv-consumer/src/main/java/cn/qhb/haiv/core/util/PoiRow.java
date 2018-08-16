package cn.qhb.haiv.core.util;

import java.util.List;

/**
 * 表格数据节点
 */
public class PoiRow {

    /**
     * 行数类型
     */
    private RowType type;
    /**
     * 行数据
     */
    private List<?> rowData;

    /**
     * 行数据类型
     */
    public enum RowType {
        /**
         * 标题
         */
        title,
        /**
         * 备注
         */
        mark,
        /**
         * 数据
         */
        data_;
    }

    public PoiRow() {
    }

    public PoiRow(RowType type, List<?> rowData) {
        this.type = type;
        this.rowData = rowData;
    }

    public RowType getType() {
        return type;
    }

    public void setType(RowType type) {
        this.type = type;
    }

    public List<?> getRowData() {
        return rowData;
    }

    public void setRowData(List<?> rowData) {
        this.rowData = rowData;
    }

    @Override
    public String toString() {
        return "PoiRow{" +
                "type=" + type +
                ", rowData=" + rowData +
                '}';
    }
}
