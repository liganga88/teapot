package com.teapot.bean;

/**
 * Created by Administrator on 2017-12-8.
 */
public class JsonResult {
    //成功
    public static final int SUCCESS = 0;
    //错误
    public static final int ERROR = 1000;

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String message;

    // 响应中的数据
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JsonResult() {
    }

    public JsonResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static JsonResult ok(){
        return new JsonResult(SUCCESS, "OK", null);
    }

    public static JsonResult ok(Object data){
        return new JsonResult(SUCCESS, "OK", data);
    }

    public static JsonResult error(String message){
        return new JsonResult(ERROR, message, null);
    }

    public static JsonResult error(int code, String message){
        return new JsonResult(code, message, null);
    }
}
