package com.jeeno.springsecurity.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * 统一的请求返回类
 * @author 杜家浩
 * @version 2.1.0
 * @date 2019/11/21 15:27
 */
@Data
@Builder
@ToString
public class ReturnDTO<T> {

    /**
     * status
     */
    private StatusEnum status;
    /**
     * message
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public enum StatusEnum{
        /**
         * 成功
         */
        SUCCESS,
        /**
         * 失败
         */
        FAILURE;
    }
}
