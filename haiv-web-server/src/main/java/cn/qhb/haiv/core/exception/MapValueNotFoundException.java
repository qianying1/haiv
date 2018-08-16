package cn.qhb.haiv.core.exception;

/**
 * 无法找到sql操作映射值错误异常
 */
public class MapValueNotFoundException extends Exception{

    public MapValueNotFoundException(String msg){
        super(msg);
    }
}
