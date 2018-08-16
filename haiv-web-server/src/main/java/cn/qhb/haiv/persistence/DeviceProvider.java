package cn.qhb.haiv.persistence;

import cn.qhb.haiv.core.util.SqlFilter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备sql提供者
 */
public class DeviceProvider {

    /**
     * 查询数据量
     *
     * @param filters
     * @return
     */
    public String selectCount(@Param("filters") List<SqlFilter> filters) {
        StringBuffer sql = new StringBuffer("select count(id) from device where 1=1");
        if (filters != null && filters.size() >= 1) {
            sql.append(" and");
            for (SqlFilter filter : filters) {
                if (filter == null) {
                    continue;
                }
                if (filter.isPreBracket()) {
                    sql.append(" (");
                }
                if (filter.getKey().trim() != null && !filter.getKey().trim().equals("") && filter.getVal() != null && filter.getOperatorStr() != null && !filter.getOperatorStr().trim().equals("")) {
                    sql.append(filter.isPreBracket()?"":" ").append(filter.getKey()).append(" ").append(filter.getOperatorStr()).append(" ").append("#{filter.val}");
                }
                if (filter.isAfterBracket()) {
                    sql.append(")");
                }
                if (filter.isNext()) {
                    sql.append(" ").append(filter.getLinkStr());
                }
            }
        }
        System.out.println(sql.toString());
        return sql.toString();
    }

    public String selectPage(@Param(value = "start") int start, @Param(value = "offset") int offset, @Param("filters") List<SqlFilter> filters) {
        StringBuffer sql = new StringBuffer("select * from device where 1=1");
        if (filters != null && filters.size() >= 1) {
            sql.append(" and");
            for (SqlFilter filter : filters) {
                if (filter == null) {
                    continue;
                }
                if (filter.isPreBracket()) {
                    sql.append(" (");
                }
                if (filter.getKey().trim() != null && !filter.getKey().trim().equals("") && filter.getVal() != null && filter.getOperatorStr() != null && !filter.getOperatorStr().trim().equals("")) {
                    sql.append(" ").append(filter.getKey()).append(" ").append(filter.getOperatorStr()).append(" ").append(filter.getVal());
                }
                if (filter.isAfterBracket()) {
                    sql.append(")");
                }
                if (filter.isNext()) {
                    sql.append(" ").append(filter.getLinkStr());
                }
            }
        }
        sql.append(" limit ").append("#{start},#{offset}");
        return sql.toString();
    }
}
