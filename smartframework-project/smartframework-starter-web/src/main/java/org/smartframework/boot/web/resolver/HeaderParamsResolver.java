/**
 * Created by Tiro on 2020/07/27.
 */
package org.smartframework.boot.web.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 类描述：请求头参数解析器 <br>
 *
 * @Description: 请求头参数解析器
 * @Author: Tiro
 * @Date: 2020/7/27 16:15
 */
public class HeaderParamsResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(HeaderParams.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return HeaderParams.build(nativeWebRequest);
    }
}
