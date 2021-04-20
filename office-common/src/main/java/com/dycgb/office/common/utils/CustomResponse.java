package com.dycgb.office.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description 自定义响应类
 * @Author myhe
 * @Date 2021/3/30 下午2:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse implements Serializable {

    private Long code;
    private String message;
    private Object data;

    public static CustomResponse OK = new CustomResponse(ErrorCodeEnum.OK.getCode(), ErrorCodeEnum.OK.getMessage());
    public static CustomResponse FAILED = new CustomResponse(ErrorCodeEnum.FAILED.getCode(), ErrorCodeEnum.FAILED.getMessage());

    public CustomResponse(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CustomResponse OK(Long code, String message, Object data) {
        return new CustomResponse(code, message, data);
    }

    public static CustomResponse OK(Long code, String message) {
        return new CustomResponse(code, message);
    }

    public static CustomResponse OK(ErrorCodeEnum ecm) {
        return OK(ecm.getCode(), ecm.getMessage());
    }

    public static CustomResponse OK(ErrorCodeEnum ecm, Object data) {
        return OK(ecm.getCode(), ecm.getMessage(), data);
    }

    public static CustomResponse FAILED(Long code, String message, Object data) {
        return new CustomResponse(code, message, data);
    }

    public static CustomResponse FAILED(Long code, String message) {
        return new CustomResponse(code, message);
    }

    public static CustomResponse FAILED(String message) {
        OK.setMessage(message);
        return OK;
    }

    public static CustomResponse FAILED(ErrorCodeEnum ecm) {
        return FAILED(ecm.getCode(), ecm.getMessage());
    }

    public static CustomResponse FAILED(ErrorCodeEnum ecm, Object data) {
        return FAILED(ecm.getCode(), ecm.getMessage(), data);
    }
}
