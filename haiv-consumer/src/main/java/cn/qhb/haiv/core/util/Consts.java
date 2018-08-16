package cn.qhb.haiv.core.util;


/**
 * @author migenwei
 *
 */
public class Consts {
    
	 public static final String FLOW_URL = "http://192.168.1.71:8080";
    // public static final String FLOW_URL = "http://183.60.189.51:8080";
	  public static final String FLOW_URL_START = "/flows/saveProcessActivitys.do";// 开启流程
	/** 状态：0不可用，1正常*/
    public static final int STATUS_YES = 1;
    public static final int STATUS_NO = 0;
    
    /** 成功标志：-1失败，10000成功，10001没登录，10002账号密码错误，10003验证码错误，10004账号不存在，10099系统错误*/
    public static final String RESULT_CODE_FAIL = "-1";
    public static final String RESULT_CODE_SUCCESS = "10000";
    public static final String RESULT_CODE_NOT_LOGIN = "10001";
    public static final String RESULT_CODE_LOGIN_ERROR = "10002";
    public static final String RESULT_CODE_SMSCODE_ERROR = "10003";
    public static final String RESULT_CODE_ACCOUNT_ERROR = "10004";
    public static final String RESULT_CODE_SYSTEM_ERROR = "10099";
    
    /** 账号状态：-1异常退出，0正常退出，1登录态*/
    public static final int ACCOUNT_ERROR = -1;
    public static final int ACCOUNT_LOGOUT = 0;
    public static final int ACCOUNT_LOGIN = 1;
    
    /** 邮件夹类型：1=收信箱，2=发信箱，3=草稿箱，4=删除箱，10=自定义 **/
    public static final int MAIL_FOLDER_TYPE_RECEIVE = 1;
    public static final int MAIL_FOLDER_TYPE_SEND = 2;
    public static final int MAIL_FOLDER_TYPE_DRAFT = 3;
    public static final int MAIL_FOLDER_TYPE_DELETE = 4;
    public static final int MAIL_FOLDER_TYPE_CUSTOM = 10;
    
    /** 邮件标志：1 删除标志，2 已读标志，3 回复标志 **/
    public static final int MAIL_BOX_FLAG_DELETE = 1;
    public static final int MAIL_BOX_FLAG_READ = 2;
    public static final int MAIL_BOX_FLAG_REPLY = 3;
    
    /** 邮件状态：0 草稿箱，1 正式邮件 **/
    public static final int MAIL_STATUS_DRAFT = 0;
    public static final int MAIL_STATUS_NORMAL = 1;
    
    /** 邮件重要标志：1 一般，2 重要，3 非常重要 **/
    public static final int MAIL_IMPORTANT_FLAG_NORMAL = 1;
    public static final int MAIL_IMPORTANT_FLAG_IMPORTANT = 2;
    public static final int MAIL_IMPORTANT_FLAG_VERY = 3;
    
    /** 用户类型：-1 系统，1 超级管理员 **/
    public static final Long USER_TYPE_SYSTEM = new Long(-1);
    public static final Long USER_TYPE_SUPPER = new Long(1);
    
    /** 短消息类型：1 个人信息，2 日程安排，3 计划任务，4.流程消息 5.系统消息 6.共享通讯录消息 **/
    public static final int MSG_TYPE_PERSONAL = 1;
    public static final int MSG_TYPE_CALENDAR = 2;
    public static final int MSG_TYPE_PLAN = 3;
    public static final int MSG_TYPE_TASK = 4;
    public static final int MSG_TYPE_SYSTEM = 5;
    public static final int MSG_TYPE_PHONE = 6;
    
    /** 会议状态：0待开始，1进行中，2结束，3取消 **/
    public static final String MEETING_STATUS_STAY = "0";
    public static final String MEETING_STATUS_START = "1";
    public static final String MEETING_STATUS_END = "2";
    public static final String MEETING_STATUS_CANCEL = "3";
    
    /** 会议用户类型：1主持人，2参会人 **/
    public static final int MEETING_USER_EMCEE = 1;
    public static final int MEETING_USER_PERSON = 2;
    
    /** 用户会议行为类型：1进入会议室，2离开会议室，3用户举手，4用户发言，5用户结束发言，6结束会议 **/
    public static final String MEETING_ACTION_JOIN = "1";
    public static final String MEETING_ACTION_LEAVE = "2";
    public static final String MEETING_ACTION_HAND = "3";
    public static final String MEETING_ACTION_START_SPEECH = "4";
    public static final String MEETING_ACTION_END_SPEECH = "5";
    public static final String MEETING_ACTION_END = "6";
    
    /** 任务状态：1进行中，2已暂停，3已完成，4已超期，5已停止 **/
    public static final String TASK_STATUS_START = "1";
    public static final String TASK_STATUS_SUSPEND = "2";
    public static final String TASK_STATUS_COMPLETE = "3";
    public static final String TASK_STATUS_TIMEOUT = "4";
    public static final String TASK_STATUS_STOP = "5";
    
    // 车辆申请状态
    public static final short CAR_STATUS_REFUSE= 0; //审核不通过
    public static final short CAR_STATUS_START = 1; //待审批
    public static final short CAR_STATUS_END = 2;//已审批
    public static final short CAR_STATUS_TAKE = 3;// 归还中
    public static final short CAR_STATUS_HAVE = 4;//已归还
    
    /*系统消息中，的消息文字*/
    public static final String MAIL_SEND_TIPS = "给您发送了一封邮件，请注意查收！"; // 发送邮件的消息提示
    public static final String FLOW_SEND_TIPS = "给您发送了流程，请注意查收！"; // 发送流程的消息提示
    public static final String SHARE_SEND_TIPS = "共享了一条通讯录给您！"; // 共享通讯录的消息提示
    public static final String TASK_ADD_TIPS = "给您发布了一条任务，请注意查收！";// 发布任务时的消息提示
    public static final String MEETING_ADD_TIPS = "邀请您参与视频会议！";//视频会议
    public static final String NEWS_ADD_TIPS = "发布了新公告，请注意查看";//发布公告
    public static final String REPORT_SEND_TIPS = "给您发送了一封工作报告，请注意查收！";//发送工作报告消息提示

    /*资产申请*/
    public static final String ASSETS_STATUS_REFUSE = "0"; // 审核不通过
    public static final String ASSETS_STATUS_START = "1"; // 资产申请
    public static final String ASSETS_STATUS_APPRO = "2"; // 资产已审批
    public static final String ASSET_STATUS_OUT = "3"; // 资产出库
    public static final String ASSET_STATUS_PREIN = "4";// 资产归还申请
    public static final String ASSET_STATUS_IN = "5";// 资产已归还
    
    /*流程关联配置 1表示用车申请的关联  2 表示固定资产申请的关联*/
    public static final long FLOW_CONF_CAR_APPLY = 1;
    public static final long FLOW_CONF_ASSETS = 2;
  
    /*已读、未读*/
    public static final long READED = 1;
    public static final long UNREAD = 0;
    
    
}
