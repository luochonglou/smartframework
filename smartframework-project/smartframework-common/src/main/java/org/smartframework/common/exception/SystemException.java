/**
 * Created by Tiro on 2020/07/24.
 */
package org.smartframework.common.exception;

import lombok.Getter;

import java.io.Serializable;

/**
 * 类描述：未知系统异常 <br>
 *
 * @Description: 未知系统异常
 * @Author: Tiro
 * @Date: 2020/7/24 11:53
 */
@Getter
public class SystemException extends RuntimeException implements Serializable {

    public SystemException() {
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
