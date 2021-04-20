package com.dycgb.office.common.exception;

import com.dycgb.office.common.utils.ErrorCodeEnum;

/**
 * @Description 资源不存在异常
 * @Author myhe
 * @Date 2021/4/2 下午5:36
 */
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(Long code, String message) {
        super(code, message);
    }

    public ResourceNotFoundException(ErrorCodeEnum ecm) {
        super(ecm.getCode(), ecm.getMessage());
    }
}
