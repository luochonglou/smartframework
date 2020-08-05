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
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类描述：全局异常处理 <br>
 *
 * @Description: 全局异常处理
 * @Author: Tiro
 * @Date: 2020/7/24 18:08
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * Json Bean Validation
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Object handleValidException(HttpServletResponse response, MethodArgumentNotValidException e) {
        response.setStatus(400);
        log.error(e.getMessage(), e);
        return handleValidation(e.getBindingResult());
    }

    /**
     * Form Bean Validation
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Object handleValidException(HttpServletResponse response, BindException e) {
        response.setStatus(400);
        log.error(e.getMessage(), e);
        return handleValidation(e.getBindingResult());
    }

    /**
     * BusinessException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Object handleArmorBusinessException(HttpServletResponse response, BusinessException e) {
        response.setStatus(400);
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
    public Object handleArmorValidationException(HttpServletResponse response, ValidationException e) {
        response.setStatus(400);
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
    public Object handleArmorServiceException(HttpServletResponse response, SystemException e) {
        response.setStatus(500);
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultExceptionCode.SYSTEM.getCode(), Optional.ofNullable(e.getMessage()).orElse(DefaultExceptionCode.SYSTEM.getMsg()));
    }


    /**
     * Other exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public Object handleException(HttpServletResponse response, Exception e) {
        if (e instanceof BusinessExceptionWrapper) {
            response.setStatus(400);
            log.error(e.getMessage(), e);
            return ExceptionResponse.newInstance(((BusinessExceptionWrapper) e).getCode(), ((BusinessExceptionWrapper) e).getMsg());
        }
        response.setStatus(500);
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultExceptionCode.SYSTEM.getCode(), DefaultExceptionCode.SYSTEM.getMsg());
    }

    /**
     * 获取绑定参数异常信息
     *
     * @param bindingResult
     * @return
     */
    private Object handleValidation(BindingResult bindingResult) {
        String msg = Optional.ofNullable(bindingResult)
                .map(r -> r.getFieldErrors())
                .map(fe -> fe.stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(",")))
                .orElse(DefaultExceptionCode.PARAMETER_MISMATCH.getMsg());
        return ExceptionResponse.newInstance(DefaultExceptionCode.PARAMETER_MISMATCH.getCode(), msg);
    }
}
