package cn.qhb.haiv.model;

import java.io.Serializable;
import java.util.List;

/**
 * 机柜/机架
 */
public class Cabinet extends BaseModel {
    /**
     * 机柜中的设备
     */
    private List<Device> devices;
    /**
     * 所属机房
     */
    private MachineRoom machineRoom;
    /**
     * 机柜名称
     */
    private String name;
    /**
     * 机柜用途
     */
    private String usage;
    /**
     * 机柜位置
     */
    private String location;
    /**
     * 所属机房id
     */
    private Long roomId;

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
}
