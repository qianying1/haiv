package cn.qhb.haiv.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Date;

/**
 * 告警设备
 */
public class Warning extends BaseModel implements Serializable {
    /**
     * 告警发生的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date actionTime;
    /**
     * 告警信息
     */
    private String msg;
    /**
     * 告警状态
     */
    private WarningStatus status = WarningStatus.handled;
    /**
     * 告警级别
     */
    private WarningLevel warningLevel = WarningLevel.info;
    /**
     * 所属设备id
     */
    private Long deviceId;
    /**
     * 设备
     */
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
        error;

        /**
         * 转换为中文显示
         *
         * @return
         */
        public static String valueCnStr(WarningLevel level) {
            if (level.equals(WarningLevel.error))
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
                return WarningLevel.error;
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
}
