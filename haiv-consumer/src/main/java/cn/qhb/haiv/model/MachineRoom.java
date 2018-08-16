package cn.qhb.haiv.model;

import java.io.Serializable;
import java.util.List;

/**
 * 机房
 */
public class MachineRoom extends BaseModel implements Serializable {
    /**
     * 机柜列表
     */
    private List<Cabinet> cabinets;
    /**
     * 机房名称
     */
    private String name;
    /**
     * 机房所在地
     */
    private String location;
    /**
     * 备注
     */
    private String remark;

    public List<Cabinet> getCabinets() {
        return cabinets;
    }

    public void setCabinets(List<Cabinet> cabinets) {
        this.cabinets = cabinets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
