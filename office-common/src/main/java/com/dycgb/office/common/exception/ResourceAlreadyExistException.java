package com.dycgb.office.common.exception;

import com.dycgb.office.common.utils.ErrorCodeEnum;

/**
 * @Description 资源已存在异常
 * @Author myhe
 * @Date 2021/4/2 下午5:33
 */

public class ResourceAlreadyExistException extends BaseException {
    public ResourceAlreadyExistException(Long code, String message) {
        super(code, message);
    }

    public ResourceAlreadyExistException(ErrorCodeEnum ecm) {
        super(ecm.getCode(), ecm.getMessage());
    }
}
