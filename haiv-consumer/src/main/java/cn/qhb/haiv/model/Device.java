package cn.qhb.haiv.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 设备资产
 */
public class Device extends BaseModel implements Serializable {
    /**
     * 名称：用户自定义名称
     */
    private String name;
    /**
     * 设备型号
     */
    private String assetModel;
    /**
     * cpu型号
     */
    private String cpuModel;
    /**
     * 厂商
     */
    private String manufacturer;
    /**
     * 序列号
     */
    private String seriesNum;
    /**
     * 操作系统
     */
    private String operaSys;
    /**
     * 生产ip
     */
    private String proIp;
    /**
     * 带外版本
     */
    private String ofbVersion;
    /**
     * 带外ip
     */
    private String ofbIp;
    /**
     * 负责人
     */
    private String respMan;
    /**
     * 状态：在用/下线
     */
    private Status status = Status.offline;
    /**
     * 用途
     */
    private String usage;
    /**
     * 信息状态：填报状态，缺/ok
     */
    private DataStatus dataStatus = DataStatus.lack;
    /**
     * 过保日期：yyyy/mm/dd
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ofd;
    /**
     * 过保天数
     */
    private Integer dofd = 0;
    /**
     * 机柜id
     */
    private Long cabinetId;
    /**
     * 设备告警列表
     */
    private List<Warning> warnings;
    /**
     * 所属机柜
     */
    private Cabinet cabinet;
    /**
     * 硬盘容量（以G为单位）
     */
    private String capacity;
    /**
     * 内存容量（以G为单位）
     */
    private String internMemory;

    /**
     * 信息填报状态
     */
    public enum DataStatus {
        /**
         * 缺
         */
        lack,
        /**
         * 已填
         */
        ok
    }

    public enum Status {
        /**
         * 在线
         */
        online,
        /**
         * 离线
         */
        offline
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssetModel() {
        return assetModel;
    }

    public void setAssetModel(String assetModel) {
        this.assetModel = assetModel;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSeriesNum() {
        return seriesNum;
    }

    public void setSeriesNum(String seriesNum) {
        this.seriesNum = seriesNum;
    }

    public String getOperaSys() {
        return operaSys;
    }

    public void setOperaSys(String operaSys) {
        this.operaSys = operaSys;
    }

    public String getProIp() {
        return proIp;
    }

    public void setProIp(String proIp) {
        this.proIp = proIp;
    }

    public String getOfbVersion() {
        return ofbVersion;
    }

    public void setOfbVersion(String ofbVersion) {
        this.ofbVersion = ofbVersion;
    }

    public String getOfbIp() {
        return ofbIp;
    }

    public void setOfbIp(String ofbIp) {
        this.ofbIp = ofbIp;
    }

    public String getRespMan() {
        return respMan;
    }

    public void setRespMan(String respMan) {
        this.respMan = respMan;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public DataStatus getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(DataStatus dataStatus) {
        this.dataStatus = dataStatus;
    }

    public Date getOfd() {
        return ofd;
    }

    public void setOfd(Date ofd) {
        this.ofd = ofd;
    }

    public Integer getDofd() {
        return dofd;
    }

    public void setDofd(Integer dofd) {
        this.dofd = dofd;
    }

    public List<Warning> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Warning> warnings) {
        this.warnings = warnings;
    }

    public Cabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(Cabinet cabinet) {
        this.cabinet = cabinet;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getInternMemory() {
        return internMemory;
    }

    public void setInternMemory(String internMemory) {
        this.internMemory = internMemory;
    }

    public Long getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(Long cabinetId) {
        this.cabinetId = cabinetId;
    }

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", assetModel='" + assetModel + '\'' +
                ", cpuModel='" + cpuModel + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", seriesNum='" + seriesNum + '\'' +
                ", operaSys='" + operaSys + '\'' +
                ", proIp='" + proIp + '\'' +
                ", ofbVersion='" + ofbVersion + '\'' +
                ", ofbIp='" + ofbIp + '\'' +
                ", respMan='" + respMan + '\'' +
                ", status=" + status +
                ", usage='" + usage + '\'' +
                ", dataStatus=" + dataStatus +
                ", ofd=" + ofd +
                ", dofd=" + dofd +
                ", cabinetId=" + cabinetId +
                ", warnings=" + warnings +
                ", cabinet=" + cabinet +
                ", capacity='" + capacity + '\'' +
                ", internMemory='" + internMemory + '\'' +
                '}';
    }
}
