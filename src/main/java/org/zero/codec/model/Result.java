package org.zero.codec.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.zero.codec.constant.SysError;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Zero (cnzeropro@qq.com)
 * @date 2022/11/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private static final long serialVersionUID = 7893804841950761019L;

    public static final String OK_MSG = "操作成功";
    public static final String ERROR_MSG = "操作失败";

    /**
     * HTTP 状态码
     */
    private Integer code;
    /**
     * 用户提示信息
     */
    private String msg;
    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 成功标志
     */
    private Boolean success;
    /**
     * 时间
     */
    private LocalDateTime time = LocalDateTime.now();

    /**
     * 数据对象
     */
    private T data;

    /**
     * 请求成功
     */
    public static <T> Result<T> ok() {
        return ok(null);
    }

    /**
     * 请求成功
     */
    public static <T> Result<T> ok(T data) {
        return ok(OK_MSG, data);
    }

    /**
     * 请求成功
     */
    public static <T> Result<T> ok(String msg, T data) {
        return of(HttpStatus.OK, msg, SysError.OK, data);
    }

    /**
     * 请求成功但不是预期响应
     */
    public static <T> Result<T> error(String msg, SysError sysError) {
        return of(HttpStatus.OK, msg, sysError);
    }

    /**
     * 请求成功但不是预期响应
     */
    public static <T> Result<T> error(String msg, String errorCode, String errorMsg) {
        return of(HttpStatus.OK, msg, errorCode, errorMsg);
    }

    /**
     * 请求成功但不是预期响应
     */
    public static <T> Result<T> error(String msg, SysError sysError, T data) {
        return of(HttpStatus.OK, msg, sysError, data);
    }

    /**
     * 请求成功但不是预期响应
     */
    public static <T> Result<T> error(String msg, String errorCode, String errorMsg, T data) {
        return of(HttpStatus.OK, msg, errorCode, errorMsg, data);
    }

    /**
     * 请求失败
     */
    public static <T> Result<T> fail() {
        return fail(null);
    }

    /**
     * 请求失败
     */
    public static <T> Result<T> fail(T data) {
        return fail(ERROR_MSG, data);
    }

    /**
     * 请求失败
     */
    public static <T> Result<T> fail(String msg) {
        return fail(msg, null);
    }

    /**
     * 请求失败
     */
    public static <T> Result<T> fail(String msg, T data) {
        return of(HttpStatus.INTERNAL_SERVER_ERROR, msg, null, data);
    }

    /**
     * 请求失败
     */
    public static <T> Result<T> fail(int code, String msg) {
        return fail(code, msg, null);
    }

    /**
     * 请求失败
     */
    public static <T> Result<T> fail(HttpStatus code, String msg) {
        return fail(code, msg, null);
    }

    /**
     * 请求失败
     */
    public static <T> Result<T> fail(int code, String msg, SysError sysError) {
        return of(code, msg, sysError);
    }

    /**
     * 请求失败
     */
    public static <T> Result<T> fail(HttpStatus code, String msg, SysError sysError) {
        return of(code, msg, sysError);
    }

    public static <T> Result<T> of(HttpStatus httpStatus, String msg, SysError sysError) {
        return of(httpStatus, msg, sysError, null);
    }

    public static <T> Result<T> of(HttpStatus httpStatus, String msg, SysError sysError, T data) {
        return of(httpStatus.value(), msg, sysError, data);
    }

    public static <T> Result<T> of(int code, String msg, SysError sysError) {
        return of(code, msg, sysError, null);
    }

    public static <T> Result<T> of(int code, String msg, SysError sysError, T data) {
        Optional<SysError> sysErrorOpt = Optional.ofNullable(sysError);
        return of(code, msg, sysErrorOpt.map(SysError::getCode).orElse(null), sysErrorOpt.map(SysError::getMsg).orElse(null), data);
    }

    public static <T> Result<T> of(HttpStatus httpStatus, String msg, String errorCode, String errorMsg) {
        return of(httpStatus, msg, errorCode, errorMsg, null);
    }

    public static <T> Result<T> of(int code, String msg, String errorCode, String errorMsg) {
        return of(code, msg, errorCode, errorMsg, null);
    }

    public static <T> Result<T> of(HttpStatus httpStatus, String msg, String errorCode, String errorMsg, T data) {
        return of(httpStatus.value(), msg, errorCode, errorMsg, LocalDateTime.now(), data);
    }

    public static <T> Result<T> of(int code, String msg, String errorCode, String errorMsg, T data) {
        return of(code, msg, errorCode, errorMsg, LocalDateTime.now(), data);
    }

    public static <T> Result<T> of(int code, String msg, String errorCode, String errorMsg, LocalDateTime time, T data) {
        if (Objects.isNull(errorCode)) {
            return of(code, msg, null, errorMsg, HttpStatus.OK.value() == code, time, data);
        }

        return of(code, msg, errorCode, errorMsg, HttpStatus.OK.value() == code && SysError.OK.getCode().equals(errorCode), time, data);
    }

    public static <T> Result<T> of(int code, String msg, String errorCode, String errorMsg, boolean success, LocalDateTime time, T data) {
        return Result.<T>builder()
                .code(code)
                .msg(msg)
                .errorCode(errorCode)
                .errorMsg(errorMsg)
                .success(success)
                .time(time)
                .data(data)
                .build();
    }
}
