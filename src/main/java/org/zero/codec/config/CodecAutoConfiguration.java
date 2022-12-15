package org.zero.codec.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zero (cnzeropro@qq.com)
 * @date 2022/11/29
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan("org.zero.codec")
public class CodecAutoConfiguration {
}
