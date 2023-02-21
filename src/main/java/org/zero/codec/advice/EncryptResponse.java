package org.zero.codec.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.zero.codec.annotation.Encrypt;
import org.zero.codec.config.CodecProperties;
import org.zero.codec.model.Result;
import org.zero.codec.util.AesUtil;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Zero (cnzeropro@qq.com)
 * @date 2022/11/29
 */
@Slf4j
@EnableConfigurationProperties(CodecProperties.class)
@ControllerAdvice
public class EncryptResponse implements ResponseBodyAdvice<Result<Object>> {
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private CodecProperties codecProperties;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public Result<Object> beforeBodyWrite(Result<Object> body, MethodParameter returnType, MediaType selectedContentType,
                                          Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                          ServerHttpRequest request, ServerHttpResponse response) {
        try {
            if (Objects.nonNull(body) && Objects.nonNull(body.getData())) {
                String key = Optional.ofNullable(returnType.getMethodAnnotation(Encrypt.class)).map(Encrypt::key).filter(StringUtils::hasText).orElse(codecProperties.getKey());
                body.setData(AesUtil.encrypt(objectMapper.writeValueAsString(body.getData()), key));
            }
            return body;
        } catch (Exception e) {
            log.warn(String.format("Result[%s] encrypt fail", body.toString()), e);
            return Result.error("data encrypt fail");
        }
    }
}