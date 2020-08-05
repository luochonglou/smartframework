/**
 * Created by Tiro on 2020/07/30.
 */
package org.smartframework.boot.web.advice;

import org.smartframework.common.result.BaseResult;
import org.smartframework.common.result.SuccessResponse;
import org.smartframework.common.FeignFlag;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 类描述： 全局ResponseBody处理<br>
 *
 * @Description:
 * @Author: Tiro
 * @Date: 2020/7/30 16:38
 */
@ControllerAdvice
public class GlobalResponseBodyHandler implements ResponseBodyAdvice<Object> {
    @Resource
    private HttpServletRequest request;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return Objects.isNull(request.getHeader(FeignFlag._HEADER));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body instanceof BaseResult) {
            return body;
        }
        return SuccessResponse.newInstance(body);
    }
}
