package cn.qhb.haiv.model;

import java.io.Serializable;
import java.util.List;

/**
 * 机柜/机架
 */
//@Entity
//@Table(name = "cahinet")
public class Cabinet extends BaseModel implements Serializable {
    /**
     * 机柜中的设备
     */
//    @OneToMany(targetEntity = Device.class,fetch = FetchType.LAZY,mappedBy = "cabinet")
    private List<Device> devices;
    /**
     * 所属机房
     */
//    @ManyToOne(targetEntity = MachineRoom.class)
//    @JoinColumn(name = "room_id")
    private MachineRoom machineRoom;
    /**
     * 所属机房id
     */
    private Long roomId;
    /**
     * 机柜名称
     */
//    @Column(name = "name")
    private String name;
    /**
     * 机柜用途
     */
//    @Column(name = "usage")
    private String usage;
    /**
     * 机柜位置
     */
//    @Column(name = "location")
    private String location;

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public MachineRoom getMachineRoom() {
        return machineRoom;
    }

    public void setMachineRoom(MachineRoom machineRoom) {
        this.machineRoom = machineRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Cabinet{" +
                "devices=" + devices +
                ", machineRoom=" + machineRoom +
                ", name='" + name + '\'' +
                ", usage='" + usage + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
