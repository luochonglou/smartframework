/**
 * Created by Tiro on 2020/08/13.
 */
package org.smartframework.boot.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.smartframework.common.exception.basic.DefaultHttpExceptionCode;
import org.smartframework.common.result.ExceptionResponse;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类描述：默认Http异常处理器 <br>
 *
 * @Description:
 * @Author: Tiro
 * @Date: 2020/8/13 20:44
 */
@Slf4j
public abstract class DefaultHttpExceptionHandler {

    /**
     * 方法不支持
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    public Object httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.METHOD_NOT_SUPPORTED);
    }

    /**
     * 媒体类型不支持
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Object HttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.MEDIA_TYPE_NOT_SUPPORTED);
    }

    /**
     * 媒体类型类型不被接受
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMediaTypeNotAcceptableException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    public Object HttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.MEDIA_TYPE_NOT_ACCEPTABLE);
    }

    /**
     * 路径参数缺失
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MissingPathVariableException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object MissingPathVariableException(MissingPathVariableException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.MISSING_PATH_VARIABLE);
    }

    /**
     * Servlet请求参数缺失
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object MissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.MISSING_SERVLET_REQUEST_PARAMETER);
    }

    /**
     * Servlet请求处理过程中的绑定异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServletRequestBindingException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object ServletRequestBindingException(ServletRequestBindingException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.SERVLET_REQUEST_BINDING);
    }

    /**
     * 不支持该类型转换
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConversionNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object ConversionNotSupportedException(ConversionNotSupportedException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.CONVERSION_NOT_SUPPORTED);
    }

    /**
     * 类型不匹配
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = TypeMismatchException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object TypeMismatchException(TypeMismatchException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.TYPE_MISMATCH);
    }

    /**
     * HTTP消息不可读
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.MESSAGE_NOT_READABLE);
    }

    /**
     * HTTP消息不可写
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotWritableException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object HttpMessageNotWritableException(HttpMessageNotWritableException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.MESSAGE_NOT_WRITABLE);
    }

    /**
     * 方法参数值验证失败
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.METHOD_ARGUMENT_NOT_VALID.getCode(), getMsg(e.getBindingResult(), DefaultHttpExceptionCode.METHOD_ARGUMENT_NOT_VALID.getMsg()));
    }

    /**
     * multipart/form-data请求时
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestPartException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object MissingServletRequestPartException(MissingServletRequestPartException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.MISSING_SERVLET_REQUEST_PART);
    }

    /**
     * Spring验证框架中的绑定异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object BindException(BindException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.BIND.getCode(), getMsg(e.getBindingResult(), DefaultHttpExceptionCode.BIND.getMsg()));
    }

    /**
     * 没有找到能处理该请求的handler
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Object NoHandlerFoundException(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.NO_HANDLER_FOUND);
    }

    /**
     * 异步请求超时
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = AsyncRequestTimeoutException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    public Object AsyncRequestTimeoutException(AsyncRequestTimeoutException e) {
        log.error(e.getMessage(), e);
        return ExceptionResponse.newInstance(DefaultHttpExceptionCode.ASYNC_REQUEST_TIMEOUT);
    }


    /**
     * 获取绑定参数异常信息
     *
     * @param bindingResult
     * @param defaultMsg
     * @return
     */
    private String getMsg(BindingResult bindingResult, String defaultMsg) {
        return Optional.ofNullable(bindingResult)
                .map(r -> r.getFieldErrors())
                .map(fe -> fe.stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(",")))
                .orElse(defaultMsg);
    }
}
