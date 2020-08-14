/**
 * Created by Tiro on 2020/07/24.
 */
package org.smartframework.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.smartframework.common.exception.basic.BusinessExceptionCode;

/**
 * 类描述： 异常响应<br>
 *
 * @Description:异常响应
 * @Author: Tiro
 * @Date: 2020/7/24 15:28
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse implements BaseResult {
    /**
     * 异常编码
     */
    private int code;

    /**
     * 异常消息
     */
    private String msg;


    /**
     * @param code
     * @param msg
     * @return
     */
    public static ExceptionResponse newInstance(int code, String msg) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    /**
     * @param exceptionCode
     * @return
     */
    public static ExceptionResponse newInstance(BusinessExceptionCode exceptionCode) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(exceptionCode.getCode());
        response.setMsg(exceptionCode.getMsg());
        return response;
    }
}
