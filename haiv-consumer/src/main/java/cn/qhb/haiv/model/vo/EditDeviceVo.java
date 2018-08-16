package cn.qhb.haiv.model.vo;

import cn.qhb.haiv.model.Device;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备资产
 */
//@ApiModel(value = "编辑设备")
public class EditDeviceVo implements Serializable {

    //    @ApiModelProperty(name = "id", notes = "唯一标识", dataType = "formData")
    private Long id;
    /**
     * 名称：用户自定义名称
     */
//    @ApiModelProperty(name = "name", notes = "名称：用户自定义名称", dataType = "formData")
    private String name;
    /**
     * 设备型号
     */
//    @ApiModelProperty(name = "assetModelodel", notes = "设备型号", dataType = "formData")
    private String assetModel;
    /**
     * cpu型号
     */
//    @ApiModelProperty(name = "cpuModel", notes = "cpu型号", dataType = "formData")
    private String cpuModel;
    /**
     * 厂商
     */
//    @ApiModelProperty(name = "manufacturer", notes = "厂商", dataType = "formData")
    private String manufacturer;
    /**
     * 序列号
     */
//    @ApiModelProperty(name = "seriesNum", notes = "序列号", dataType = "formData")
    private String seriesNum;
    /**
     * 操作系统
     */
//    @ApiModelProperty(name = "operaSys", notes = "操作系统", dataType = "formData")
    private String operaSys;
    /**
     * 生产ip
     */
//    @ApiModelProperty(name = "proIp", notes = "生产ip", dataType = "formData")
    private String proIp;
    /**
     * 带外版本
     */
//    @ApiModelProperty(name = "ofbVersion", notes = "带外版本", dataType = "formData")
    private String ofbVersion;
    /**
     * 带外ip
     */
//    @ApiModelProperty(name = "ofbIp", notes = "带外ip", dataType = "formData")
    private String ofbIp;
    /**
     * 负责人
     */
//    @ApiModelProperty(name = "respMan", notes = "负责人", dataType = "formData")
    private String respMan;
    /**
     * 状态：在用/下线
     */
//    @ApiModelProperty(name = "status", notes = "状态：在用/下线[online/offline]，默认为offline", dataType = "string")
    private Device.Status status = Device.Status.offline;
    /**
     * 用途
     */
//    @ApiModelProperty(name = "usage", notes = "用途", dataType = "formData")
    private String usage;
    /**
     * 信息状态：填报状态，缺/ok
     */
//    @ApiModelProperty(name = "dataStatus", notes = "信息状态：填报状态，缺/ok[lack/ok]；默认为lack", dataType = "formData")
    private Device.DataStatus dataStatus = Device.DataStatus.lack;
    /**
     * 过保日期：yyyy/mm/dd
     */
//    @ApiModelProperty(name = "ofd", dataType = "datetime", notes = "过保日期，格式为：yyyy/mm/dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ofd;
    /**
     * 过保天数
     */
//    @ApiModelProperty(name = "过保天数", dataType = "formData", notes = "过保天数：默认为0")
    private Integer dofd = 0;
    /**
     * 机柜id
     */
//    @ApiModelProperty(name = "cahinetId", dataType = "formData", notes = "所属机柜的id")
    private Long cabinetId;
    /**
     * 硬盘容量（以G为单位）
     */
//    @ApiModelProperty(name = "capacity", dataType = "formData", notes = "硬盘容量（以G为单位）")
    private String capacity;
    /**
     * 内存容量（以G为单位）
     */
//    @ApiModelProperty(name = "internMemory", dataType = "formData", notes = "内存容量（以G为单位）")
    private String internMemory;

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

    public Device.Status getStatus() {
        return status;
    }

    public void setStatus(Device.Status status) {
        this.status = status;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public Device.DataStatus getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Device.DataStatus dataStatus) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
