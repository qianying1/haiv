package cn.qhb.haiv.model.access_control;

import cn.qhb.haiv.model.BaseModel;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * 权限控制系统功能
 */
public class SysFunction extends BaseModel implements Serializable {

    /**
     * 功能名称
     * varchar(100)
     */
    private String name;
    /**
     * 功能访问地址
     * varchar(200)
     */
    private String visitUrl;
    /**
     * 功能类型
     */
    private int type;
    /**
     * 上级菜单编号
     */
    private BigInteger parentId;
    /**
     * 顺序号
     */
    private int sn;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否预留
     */
    private IsPrep isPrep;
    /**
     * 大图片路径
     */
    private String bigImgPath;
    /**
     * 中图片路径
     */
    private String middleImgPath;
    /**
     * 小图片路径
     */
    private String smImgPath;
    /**
     * 所属角色
     */
    private List<RolePermission> permissions;
    /**
     * 是否预留
     */
    public enum IsPrep{
        /**
         * 是
         */
        yes,
        /**
         * 否
         */
        no;
        /**
         * 转换为中文表示
         *
         * @param isPrep
         * @return
         */
        public static String valueCnStr(IsPrep isPrep){
            if (isPrep.equals(IsPrep.yes))
                return "是";
            return "否";
        }

        /**
         * 中文串转换为枚举值
         *
         * @param cnStr
         * @return
         */
        public static IsPrep valueEnum(String cnStr){
            if (cnStr.trim().equals("是"))
                return IsPrep.yes;
            return IsPrep.no;
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisitUrl() {
        return visitUrl;
    }

    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public IsPrep getIsPrep() {
        return isPrep;
    }

    public void setIsPrep(IsPrep isPrep) {
        this.isPrep = isPrep;
    }

    public String getBigImgPath() {
        return bigImgPath;
    }

    public void setBigImgPath(String bigImgPath) {
        this.bigImgPath = bigImgPath;
    }

    public String getMiddleImgPath() {
        return middleImgPath;
    }

    public void setMiddleImgPath(String middleImgPath) {
        this.middleImgPath = middleImgPath;
    }

    public String getSmImgPath() {
        return smImgPath;
    }

    public void setSmImgPath(String smImgPath) {
        this.smImgPath = smImgPath;
    }

    public List<RolePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolePermission> permissions) {
        this.permissions = permissions;
    }
}
