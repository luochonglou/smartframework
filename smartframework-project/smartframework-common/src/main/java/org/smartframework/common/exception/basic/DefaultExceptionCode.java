/**
 * Created by Tiro on 2020/07/25.
 */
package org.smartframework.common.exception.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 枚举描述：默认异常编码 <br>
 *
 * @Description: 默认异常编码
 * @Author: Tiro
 * @Date: 2020/7/25 15:09
 */
@AllArgsConstructor
@Getter
public enum DefaultExceptionCode implements BusinessExceptionCode {
    /**
     * 服务发生未知异常
     */
    SYSTEM(-1, "系统异常"),

    /**
     * 微服务调用发生未知异常
     */
    SYSTEM_MICRO_SERVICES(-1, "服务调用失败"),

    /**
     * 默认业务异常
     */
    BUSINESS_DEFAULT(0, "操作失败"),


    /**
     * 资源路径不存在
     */
    NO_HANDLER(404, "资源路径不存在"),

    /**
     * 请求方式不允许
     */
    METHOD_NOT_ALLOWED(405, "请求方式不允许"),


    /**
     * 默认参数异常
     */
    PARAMETER_MISMATCH(10003, "参数不合法"),
    ;

    /**
     * 异常编码
     */
    private int code;
    /**
     * 异常消息
     */
    private String msg;
}
