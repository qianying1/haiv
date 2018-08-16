package cn.qhb.haiv.model.access_control;

import cn.qhb.haiv.model.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 权限角色用户
 */
public class SysUser extends BaseModel implements Serializable {

    /**
     * 当前用户管理的角色
     */
    private List<RolePermission> rolePermissions;
    /**
     * 当前用户的授权用户
     */
    private List<UserRole> userRoles;
    /**
     * 用户类型
     */
    private UserType userType;

    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户email
     */
    private String email;
    /**
     * 最后一次登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
    /**
     * 当前状态
     */
    private int status;

    /**
     * 用户的类型
     */
    public enum UserType {
        /**
         * 管理员
         */
        admin,
        /**
         * 普通用户
         */
        user,
    }

    public List<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
