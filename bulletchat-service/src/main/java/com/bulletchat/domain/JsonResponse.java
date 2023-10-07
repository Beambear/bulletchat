package com.bulletchat.domain;

import com.bulletchat.code.StatusCode;

public class JsonResponse<T> {

    private static final long serialVersionUID = 1L;
    /**
     * 状态码
     */
    private String code;
    /**
     * 相应信息，说明响应情况
     */
    private String msg;
    /**
     * 响应具体数据
     */
    private T data;

    /**
     * 无返回数据，但响应成功
     */
    public static JsonResponse<String> success(){
        return new JsonResponse<>(null);
    }


    /**
     * 带有返回数据的成功响应体
     * @param data
     */
    public JsonResponse(T data) {
        this(StatusCode.SUCCESS.getCode(),StatusCode.SUCCESS.getInfo(), data);
    }

    /**
     * 失败返回体
     */

    public JsonResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
