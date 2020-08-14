/**
 * Created by Tiro on 2020/07/24.
 */
package org.smartframework.boot.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.smartframework.common.exception.BusinessException;
import org.smartframework.common.exception.SystemException;
import org.smartframework.common.exception.basic.BusinessExceptionWrapper;
import org.smartframework.common.exception.basic.DefaultExceptionCode;
import org.smartframework.common.result.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.util.Optional;

/**
 * 类描述：全局异常处理 <br>
 *
 * @Description: 全局异常处理
 * @Author: Tiro
 * @Date: 2020/7/24 18:08
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends DefaultHttpExceptionHandler {

    /**
     * BusinessException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(e.getCode(), e.getMsg());
    }


    /**
     * ValidationException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ValidationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultExceptionCode.PARAMETER_MISMATCH.getCode(),
                Optional.ofNullable(e.getMessage()).orElse(DefaultExceptionCode.PARAMETER_MISMATCH.getMsg()));
    }

    /**
     * SystemException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = SystemException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleSystemException(SystemException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultExceptionCode.SYSTEM.getCode(), Optional.ofNullable(e.getMessage()).orElse(DefaultExceptionCode.SYSTEM.getMsg()));
    }


    /**
     * Other exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(HttpServletResponse response, Exception e) {
        if (e instanceof BusinessExceptionWrapper) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            log.error(e.getMessage(), e);
            return ExceptionResponse.newInstance(((BusinessExceptionWrapper) e).getCode(), ((BusinessExceptionWrapper) e).getMsg());
        }
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultExceptionCode.SYSTEM.getCode(), DefaultExceptionCode.SYSTEM.getMsg());
    }

}
