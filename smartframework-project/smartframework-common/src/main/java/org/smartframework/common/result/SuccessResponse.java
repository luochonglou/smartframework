/**
 * Created by Tiro on 2020/07/30.
 */
package org.smartframework.common.result;

import lombok.Data;

/**
 * 类描述：成功响应 <br>
 *
 * @Description: 成功响应
 * @Author: Tiro
 * @Date: 2020/7/30 18:04
 */
@Data
public class SuccessResponse implements BaseResult {
    /**
     * 异常编码
     */
    private int code = 0;

    /**
     * 异常消息
     */
    private String msg = "成功";

    /**
     * 响应数据
     */
    private Object data;


    /**
     * @param data
     * @return
     */
    public static SuccessResponse newInstance(Object data) {
        SuccessResponse response = new SuccessResponse();
        response.setData(data);
        return response;
    }

}
