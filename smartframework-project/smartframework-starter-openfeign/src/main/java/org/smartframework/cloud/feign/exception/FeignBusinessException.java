/**
 * Created by Tiro on 2020/07/24.
 */
package org.smartframework.cloud.feign.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.Getter;
import org.smartframework.common.exception.basic.BusinessExceptionWrapper;

/**
 * 类描述： 服务调用业务异常<br>
 *
 * @Description: 服务调用业务异常
 * @Author: Tiro
 * @Date: 2020/7/24 12:00
 */
@Getter
public class FeignBusinessException extends HystrixBadRequestException implements BusinessExceptionWrapper {

    /**
     * 异常编码
     */
    private int code;

    /**
     * 异常消息
     */
    private String msg;


    /**
     * @param message
     * @param code
     */
    public FeignBusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }
}
