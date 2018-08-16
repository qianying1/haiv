package cn.qhb.haiv.model.access_control;

import cn.qhb.haiv.model.BaseModel;

/**
 * 权限
 */
public class Permission extends BaseModel {

    /**
     * 权限地址
     */
    private String url;
    /**
     * 权限名称
     */
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
