/**
 * Created by Tiro on 2020/07/30.
 */
package org.smartframework.common.exception.basic;

import java.io.Serializable;

/**
 * 类描述：基础异常编码 <br>
 *
 * @Description: 基础异常编码
 * @Author: Tiro
 * @Date: 2020/7/30 17:34
 */
public interface BusinessExceptionCode extends Serializable {
    /**
     * 获取异常编码，编码约定为正数
     *
     * @return
     */
    int getCode();

    /**
     * 获取异常消息
     *
     * @return
     */
    String getMsg();
}
