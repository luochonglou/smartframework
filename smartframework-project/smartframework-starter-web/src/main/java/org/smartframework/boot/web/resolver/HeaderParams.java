/**
 * Created by Tiro on 2020/07/27.
 */
package org.smartframework.boot.web.resolver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * 类描述：请求头参数 <br>
 *
 * @Description: 请求头参数
 * @Author: Tiro
 * @Date: 2020/7/27 16:12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeaderParams {

    private final static String _UID = "uid";

    private final static String _TOKEN = "token";

    private String uid;

    private String token;


    /**
     * @param nativeWebRequest
     * @return
     */
    public static HeaderParams build(NativeWebRequest nativeWebRequest) {
        if (null == nativeWebRequest) {
            throw new NullPointerException();
        }
        return new HeaderParams(nativeWebRequest.getHeader(_UID), nativeWebRequest.getHeader(_TOKEN));
    }
}
