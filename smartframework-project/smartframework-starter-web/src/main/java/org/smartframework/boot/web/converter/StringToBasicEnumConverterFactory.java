/**
 * Created by Tiro on 2020/07/27.
 */
package org.smartframework.boot.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 类描述：String converter BasicEnum<br>
 *
 * @Description:
 * @Author: Tiro
 * @Date: 2020/7/27 16:35
 */
public class StringToBasicEnumConverterFactory implements ConverterFactory<String, BasicEnum> {

    /**
     * 缓存
     */
    private static final Map<Class, Converter> CONVERTER_MAP = new ConcurrentHashMap<>();


    @Override
    public <T extends BasicEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return CONVERTER_MAP.computeIfAbsent(targetType, StringToEnumConverter::new);
    }

    /**
     * @param <T>
     */
    class StringToEnumConverter<T extends BasicEnum> implements Converter<String, T> {

        protected final Class<T> enumType;
        /**
         * 枚举缓存
         */
        private Map<String, T> enumMap;

        /**
         * @param enumType
         */
        StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
            enumMap = Arrays.stream(enumType.getEnumConstants()).collect(Collectors.toMap(t -> t.getCode().toString(), Function.identity()));
        }

        /**
         * @param source
         * @return
         */
        @Override
        public T convert(String source) {
            T t = enumMap.get(source);
            if (null == t) {
                throw new IllegalArgumentException(enumType.getName() + "参数非法");
            }
            return t;
        }
    }
}
