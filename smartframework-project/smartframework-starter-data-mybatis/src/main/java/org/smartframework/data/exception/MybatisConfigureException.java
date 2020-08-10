/**
 * Created by Tiro on 2020/08/07.
 */
package org.smartframework.data.exception;

/**
 * 类描述： mybatis 配置异常<br>
 *
 * @Description:
 * @Author: Tiro
 * @Date: 2020/8/7 18:03
 */
public class MybatisConfigureException extends RuntimeException {

    public MybatisConfigureException() {
    }

    public MybatisConfigureException(String message) {
        super(message);
    }

    public MybatisConfigureException(String message, Throwable cause) {
        super(message, cause);
    }

    public MybatisConfigureException(Throwable cause) {
        super(cause);
    }

    public MybatisConfigureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
