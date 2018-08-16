package cn.qhb.haiv.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 告警设备
 */
//@Entity
//@Table(name = "warning")
public class Warning extends BaseModel implements Serializable {
    /**
     * 告警发生的时间
     */
//    @Column(name = "action_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date actionTime;
    /**
     * 告警信息
     */
//    @Column(name = "msg")
    private String msg;
    /**
     * 告警状态
     */
//    @Column(name = "status")
    private WarningStatus status;
    /**
     * 告警级别
     */
//    @Column(name = "warning_level")
    private WarningLevel warningLevel;
    /**
     * 所属设备id
     */
    private Long deviceId;

    /**
     * 对应的设备
     */
//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
//    @JoinColumn(name = "device_id")
    private Device device;

    /**
     * 状态
     */
    public enum WarningStatus {
        /**
         * 已处理
         */
        handled,
        /**
         * 未处理
         */
        untreated;

        /**
         * 转换为中文
         */
        public static String valueCnStr(WarningStatus warningStatus) {
            if (warningStatus.toString().trim().equals(WarningStatus.handled)) {
                return "已处理";
            }
            return "未处理";
        }

        /**
         * 转换为状态
         *
         * @param str
         * @return
         */
        public static WarningStatus valueStat(String str) {
            if (str.trim().equals("已处理"))
                return WarningStatus.handled;
            return WarningStatus.untreated;
        }
    }

    /**
     * 级别
     */
    public enum WarningLevel {
        /**
         * 信息
         */
        info,
        /**
         * 警告
         */
        warning,
        /**
         * 严重
         */
        serious;

        /**
         * 转换为中文显示
         *
         * @return
         */
        public static String valueCnStr(WarningLevel level) {
            if (level.equals(WarningLevel.serious))
                return "严重";
            else if (level.equals(WarningLevel.warning))
                return "警告";
            else
                return "信息";
        }

        /**
         * 转换为告警级别
         *
         * @param str
         * @return
         */
        public static WarningLevel valueLevel(String str) {
            if (str.trim().equals("严重"))
                return WarningLevel.serious;
            else if (str.trim().equals("警告"))
                return WarningLevel.warning;
            else
                return WarningLevel.info;
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public WarningStatus getStatus() {
        return status;
    }

    public void setStatus(WarningStatus status) {
        this.status = status;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public WarningLevel getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(WarningLevel warningLevel) {
        this.warningLevel = warningLevel;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "Warning{" +
                "actionTime=" + actionTime +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                ", warningLevel=" + warningLevel +
                ", device=" + device +
                '}';
    }
}
