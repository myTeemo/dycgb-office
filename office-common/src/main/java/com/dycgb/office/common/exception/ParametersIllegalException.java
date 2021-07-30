package com.dycgb.office.common.exception;

import com.dycgb.office.common.utils.ErrorCodeEnum;

/**
 * @Description 参数非法异常
 * @Author myhe
 * @Date 2021/4/28 下午10:56
 */
public class ParametersIllegalException extends BaseException {
    public ParametersIllegalException(Long code, String message) {
        super(code, message);
    }

    public ParametersIllegalException(ErrorCodeEnum ece) {
        super(ece.getCode(), ece.getMessage());
    }
}
