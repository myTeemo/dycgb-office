package com.dycgb.office.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description 基础异常
 * @Author myhe
 * @Date 2021/4/2 下午5:45
 */
@Getter
@Setter
public class BaseException extends RuntimeException {
    private Long code;
    private String message;

    public BaseException(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}
