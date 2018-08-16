package cn.qhb.haiv.core.exception;

/**
 * 无法找到列表值错误异常
 */
public class ListValueNotFoundException extends Exception{

    public ListValueNotFoundException(String msg){
        super(msg);
    }
}
