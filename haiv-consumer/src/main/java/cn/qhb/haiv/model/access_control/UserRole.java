package cn.qhb.haiv.model.access_control;

import cn.qhb.haiv.model.BaseModel;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 子用户
 */
public class UserRole extends BaseModel{
    /**
     * 角色编号
     */
    private Long roleId;
    /**
     * 用户编号
     */
    private Long userId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
