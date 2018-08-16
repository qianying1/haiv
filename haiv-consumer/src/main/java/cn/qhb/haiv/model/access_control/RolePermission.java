package cn.qhb.haiv.model.access_control;

import cn.qhb.haiv.model.BaseModel;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 角色权限
 */
public class RolePermission extends BaseModel implements Serializable {

    /**
     * 角色编号
     */
    private Long roleId;
    /**
     * 功能编号
     */
    private Long functionId;

    /**
     * 权限id
     */
    private Long pId;

    /**
     * 允许访问的位置列表
     */
    private SysFunction sysFunction;
    /**
     * 系统角色列表
     */
    private SysRole role;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public SysFunction getSysFunction() {
        return sysFunction;
    }

    public void setSysFunction(SysFunction sysFunction) {
        this.sysFunction = sysFunction;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }
}
