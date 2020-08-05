/**
 * Created by Tiro on 2020/07/24.
 */
package org.smartframework.common.exception;

import lombok.Getter;
import org.smartframework.common.exception.basic.BusinessExceptionCode;
import org.smartframework.common.exception.basic.BusinessExceptionWrapper;
import org.smartframework.common.exception.basic.DefaultExceptionCode;

import java.io.Serializable;

/**
 * 类描述：已知业务异常 <br>
 *
 * @Description: 已知业务异常
 * @Author: Tiro
 * @Date: 2020/7/24 11:28
 */
@Getter
public class BusinessException extends RuntimeException implements Serializable, BusinessExceptionWrapper {

    /**
     * 异常编码
     */
    private int code = DefaultExceptionCode.BUSINESS_DEFAULT.getCode();

    /**
     * 异常消息
     */
    private String msg;


    /**
     * @param message 异常消息
     */
    public BusinessException(String message) {
        super(message);
        this.msg = message;
    }

    /**
     * @param message 异常消息
     * @param cause   原始异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }


    /**
     * @param exceptionCode 异常编码
     */
    public BusinessException(BusinessExceptionCode exceptionCode) {
        super(exceptionCode.getMsg());

        this.code = exceptionCode.getCode();
        this.msg = exceptionCode.getMsg();
    }

    /**
     * @param exceptionCode 异常编码
     * @param cause         原始异常
     */
    public BusinessException(BusinessExceptionCode exceptionCode, Throwable cause) {
        super(exceptionCode.getMsg(), cause);

        this.code = exceptionCode.getCode();
        this.msg = exceptionCode.getMsg();
    }

}
