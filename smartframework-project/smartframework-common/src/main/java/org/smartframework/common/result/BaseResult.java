/**
 * Created by Tiro on 2020/07/30.
 */
package org.smartframework.common.result;

import java.io.Serializable;

/**
 * 类描述：基础响应 <br>
 *
 * @Description: 基础响应
 * @Author: Tiro
 * @Date: 2020/7/30 18:28
 */
public interface BaseResult extends Serializable {
    /**
     * 获取响应状态
     *
     * @return
     */
    int getCode();

    /**
     * 获取响应消息
     *
     * @return
     */
    String getMsg();
}
