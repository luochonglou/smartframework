/**
 * Created by Tiro on 2020/07/30.
 */
package org.smartframework.boot.web.advice;

import org.smartframework.common.FeignFlag;
import org.smartframework.common.result.BaseResult;
import org.smartframework.common.result.SuccessResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 类描述： 全局ResponseBody处理<br>
 *
 * @Description:
 * @Author: Tiro
 * @Date: 2020/7/30 16:38
 */
@ControllerAdvice
public class GlobalResponseBodyHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (serverHttpRequest.getHeaders().containsKey(FeignFlag._HEADER) ||
                body instanceof BaseResult) {
            return body;
        }
        return SuccessResponse.newInstance(body);
    }
}
