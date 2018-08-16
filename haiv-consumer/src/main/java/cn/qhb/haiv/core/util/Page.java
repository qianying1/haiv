package cn.qhb.haiv.core.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据分页
 *
 * @author qianhaibin
 */
@ApiModel(value = "page", description = "数据分页")
public class Page<T> {

    /**
     * 数据页页码
     */
    @ApiModelProperty(name = "pageNum", notes = "数据页页码")
    private int pageNum;
    /**
     * 数据总条数
     */
    @ApiModelProperty(name = "total", notes = "数据总条数")
    private int total;
    /**
     * 数据页页面大小
     */
    @ApiModelProperty(name = "pageSize", notes = "数据页页面大小")
    private int pageSize;

    /**
     * 数据内容
     */
    @ApiModelProperty(name = "list", notes = "数据内容")
    List<T> list = new ArrayList<>();

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 判断当前页码是否已经溢出
     *
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param total 数据总数
     * @return
     */
    public static boolean isPageNumOverFlowPage(int pageNum,int pageSize,int total){
        return (pageNum>(total/pageSize+(total%pageSize==0?0:1)));
    }
}
