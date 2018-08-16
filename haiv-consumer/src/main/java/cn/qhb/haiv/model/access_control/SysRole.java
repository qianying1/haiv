package cn.qhb.haiv.model.access_control;

import cn.qhb.haiv.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 角色用户关系
 */
public class SysRole extends BaseModel implements Serializable {
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 角色类型
     */
    private int roleType;
    /**
     * 业务编号
     */
    private String businessNum;
    /**
     * 顺序号
     */
    private int orderNum;
    /**
     * 当前角色权限列表
     */
    List<RolePermission> permissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public String getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(String businessNum) {
        this.businessNum = businessNum;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public List<RolePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolePermission> permissions) {
        this.permissions = permissions;
    }
}
