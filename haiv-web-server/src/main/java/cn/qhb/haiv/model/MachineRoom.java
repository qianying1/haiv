package cn.qhb.haiv.model;

import java.io.Serializable;
import java.util.List;

/**
 * 机房
 */
//@Entity
//@Table(name = "machine_room")
public class MachineRoom extends BaseModel implements Serializable {
    /**
     * 机柜列表
     */
//    @OneToMany(targetEntity = Cabinet.class,fetch = FetchType.LAZY,mappedBy = "machineRoom")
    private List<Cabinet> cabinets;
    /**
     * 机房名称
     */
//    @Column(name = "name")
    private String name;
    /**
     * 机房所在地
     */
//    @Column(name = "location")
    private String location;
    /**
     * 备注
     */
//    @Column(name = "remark")
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

    @Override
    public String toString() {
        return "MachineRoom{" +
                "cabinets=" + cabinets +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
