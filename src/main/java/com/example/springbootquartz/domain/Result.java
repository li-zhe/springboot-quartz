package com.example.springbootquartz.domain;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 统一返回结果
 *
 * @author liyuzhe
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    public static <T> Result<T> success() {
        return restResult(null, HttpStatus.OK.value(), null);
    }

    public static <T> Result<T> success(T data) {
        return restResult(data, HttpStatus.OK.value(), null);
    }

    public static <T> Result<T> success(T data, String msg) {
        return restResult(data, HttpStatus.OK.value(), msg);
    }

    public static <T> Result<T> error(String msg) {
        return restResult(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static <T> Result<T> error(T data) {
        return restResult(data, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
    }

    public static <T> Result<T> error(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> Result<T> error(T data, int code, String msg) {
        return restResult(data, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    private static <T> Result<T> restResult(T data, int code, String msg) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
