/**
 * Created by Tiro on 2020/08/13.
 */
package org.smartframework.common.exception.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类描述：Http请求基础异常编码解释 <br>
 *
 * @Description: Http请求基础异常编码解释
 * @Author: Tiro
 * @Date: 2020/8/13 21:02
 */
@AllArgsConstructor
@Getter
public enum DefaultHttpExceptionCode implements BusinessExceptionCode {
    METHOD_NOT_SUPPORTED(405, "HTTP方法不支持"),
    MEDIA_TYPE_NOT_SUPPORTED(415, "MIME类型不支持"),
    MEDIA_TYPE_NOT_ACCEPTABLE(406, "MIME类型不被接受"),
    MISSING_PATH_VARIABLE(500, "路径参数缺失"),
    MISSING_SERVLET_REQUEST_PARAMETER(400, "Servlet请求参数缺失"),
    SERVLET_REQUEST_BINDING(400, "Servlet请求绑定异常"),
    CONVERSION_NOT_SUPPORTED(500, "不支持该类型转换"),
    TYPE_MISMATCH(400, "类型不匹配"),
    MESSAGE_NOT_READABLE(400, "HTTP消息不可读"),
    MESSAGE_NOT_WRITABLE(500, "HTTP消息不可写"),
    METHOD_ARGUMENT_NOT_VALID(400, "方法参数值验证失败"),
    MISSING_SERVLET_REQUEST_PART(400, "multipart/form-data请求部分缺失"),
    BIND(400, "参数绑定异常"),
    NO_HANDLER_FOUND(404, "资源路径不存在"),
    ASYNC_REQUEST_TIMEOUT(503, "异步请求超时"),
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
