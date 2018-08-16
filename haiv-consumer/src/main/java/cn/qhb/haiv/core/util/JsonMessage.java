package cn.qhb.haiv.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json响应信息
 * <p>
 * Created by qianhaibin on 2018/3/8.
 */
public class JsonMessage {

    /**
     * 返回的数据名称
     */
    private static final String CONTENT_NAME = "data";
    /**
     * 返回的信息名称
     */
    private static final String MSG_NAME = "msg";
    /**
     * 返回状态的名称
     */
    private static final String STATUS_NAME = "success";

    private static Logger logger = LoggerFactory.getLogger(JsonMessage.class);
    /**
     * 返回的json映射
     */
    private static Map<String, Object> jsonResult = new HashMap<>();

    /**
     * 重置result返回映射
     */
    public static void resetJsonResult() {
        jsonResult.clear();
        jsonResult = new HashMap<>();
    }
    /**
     * 设置是否成功状态
     *
     * @param isSuccess
     * @return
     */
    /*public JsonMessage setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
        return this;
    }*/

    /**
     * 成功响应信息
     * 返回格式为
     * {
     * data: {obj(0):obj(1),obj(2):obj(3),obj(4):obj(5),.......},
     * msg:"xxxxx",
     * success:true
     * }
     *
     * @param msg 返回信息
     * @param obj 返回数据
     * @return
     */
    public static String successArray(String msg, Object... obj) {
        resetJsonResult();
        jsonResult.put(STATUS_NAME, true);
        jsonResult.put(MSG_NAME, msg);
        if (obj == null || obj.length <= 0) {
            jsonResult.put(CONTENT_NAME, new HashMap<>());
            return JSON.toJSONString(jsonResult, SerializerFeature.DisableCircularReferenceDetect);
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(CONTENT_NAME, dataMap);
        for (int i = 0; i < obj.length; i += 2) {
            try {
                dataMap.put(obj[i].toString(), obj[i + 1]);
            } catch (Exception e) {
                logger.error("json数据转化错误!", e);
                jsonResult.put(MSG_NAME, "json数据转化错误!" + logger.getName());
            }
        }
        return JSON.toJSONString(jsonResult, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 按照固定的格式返回数据
     * <p>
     * 格式为{data:{xxxx},msg:"xxxx",success:true}
     *
     * @param msg
     * @param data
     * @return
     */
    public static String successList(String msg, List data) {
        resetJsonResult();
        jsonResult.put(STATUS_NAME, true);
        jsonResult.put(MSG_NAME, msg);
        if (data == null || data.size() <= 0)
            jsonResult.put(CONTENT_NAME, new ArrayList());
        else
            jsonResult.put(CONTENT_NAME, data);
        return JSON.toJSONString(jsonResult, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 返回单个对象数据信息
     * <p>
     * 格式为{data:obj,msg:"xxxxxx",success:true}
     *
     * @param msg
     * @param obj
     * @return
     */
    public static String successObj(String msg, Object obj) {
        resetJsonResult();
        jsonResult.put(STATUS_NAME, true);
        jsonResult.put(MSG_NAME, msg);
        if (obj == null)
            jsonResult.put(CONTENT_NAME, null);
        else
            jsonResult.put(CONTENT_NAME, obj);
        return JSON.toJSONString(jsonResult, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 返回成功信息
     *
     * @param msg
     * @return
     */
    public static String successMsg(String msg) {
        resetJsonResult();
        jsonResult.put(STATUS_NAME, true);
        jsonResult.put(MSG_NAME, msg);
        return JSON.toJSONString(jsonResult, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 错误响应信息
     * <p>
     * 返回格式为
     * {
     * data:null,
     * msg:"xxxxxxxx",
     * success:false
     * }
     *
     * @param msg
     * @return
     */
    public static String error(String msg) {
        resetJsonResult();
        jsonResult.put(MSG_NAME, msg);
        jsonResult.put(STATUS_NAME, false);
        jsonResult.put(CONTENT_NAME, null);
        return JSON.toJSONString(jsonResult, SerializerFeature.DisableCircularReferenceDetect);
    }
}
