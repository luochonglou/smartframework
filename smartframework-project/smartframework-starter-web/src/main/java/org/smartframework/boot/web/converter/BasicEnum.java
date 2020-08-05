/**
 * Created by Tiro on 2020/07/27.
 */
package org.smartframework.boot.web.converter;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 枚举描述：基础枚举 <br>
 *
 * @Description: 基础枚举
 * @Author: Tiro
 * @Date: 2020/7/27 16:28
 */
public interface BasicEnum<T> {


    /**
     * @return
     */
    @JsonValue
    T getCode();

    /**
     * equals code
     *
     * @param code
     * @return
     */
    default boolean is(T code) {
        return this.getCode().equals(code);
    }

}
