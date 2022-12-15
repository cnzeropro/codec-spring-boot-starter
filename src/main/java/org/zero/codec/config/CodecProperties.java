package org.zero.codec.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Zero (cnzeropro@qq.com)
 * @date 2022/11/29
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "codec")
public class CodecProperties {
    private static final String DEFAULT_KEY = "0123456789abcdef";
    private String key = DEFAULT_KEY;
}