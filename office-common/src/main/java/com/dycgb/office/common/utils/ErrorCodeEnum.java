package com.dycgb.office.common.utils;

/**
 * 错误码枚举类
 */
public enum ErrorCodeEnum {
    OK(10000, "API响应成功"),
    FAILED(-10000, "API响应失败"),

    ACCESS_TOKEN_FAILED_NO_PERMISSION(10001, "没有此权限"),
    ACCESS_TOKEN_FAILED_INVALID(10002, "Token无效"),
    ACCESS_TOKEN_FAILED_EXPIRED(10003, "Token过期"),

    LOGIN_FAILED_USERNAME_OR_PASSWORD_INCORRECT(10004, "登录失败-用户名或密码不正确"),
    LOGIN_FAILED_PARAMETER_ILLEGAL(10005, "登录失败-参数非法"),
    LOGIN_OK_ACCOUNT_INACTIVE(10006, "登录成功-账户未激活"),
    LOGIN_OK(10007, "登录成功"),
    LOGIN_FAILED_UN_LOGIN(10008, "登录失败-账户未登录"),
    REGISTER_FAILED_PARAMETER_ILLEGAL(10009, "注册失败-参数非法"),

    ACCOUNT_CREATE_OK(20001, "新增账户成功"),
    ACCOUNT_CREATE_FAILED_PARAMETER_ILLEGAL(20002, "新增账户失败-参数非法"),
    ACCOUNT_CREATE_FAILED_EXIST(20003, "新增账户失败-账户已存在"),
    ACCOUNT_CREATE_FAILED_PASSWORD_INCONSISTENT(20004, "新增账户失败-密码不一致"),

    ACCOUNT_UPDATE_PASSWORD_OK(20011, "更改账户密码成功"),
    ACCOUNT_UPDATE_PASSWORD_FAILED_PARAMETER_ILLEGAL(20012, "更改账户密码失败-参数非法"),
    ACCOUNT_UPDATE_PASSWORD_FAILED_MISMATCH(20013, "更改账户密码失败-密码不匹配"),
    ACCOUNT_UPDATE_PASSWORD_FAILED_NOT_EXIST(20014, "更改账户密码失败-账户不存在"),
    ACCOUNT_UPDATE_ACTIVE_OK(20015, "账户激活成功"),
    ACCOUNT_UPDATE_ACTIVE_FAILED_NOT_EXIST(20016, "账户激活失败-账户不存在"),
    ACCOUNT_UPDATE_ACTIVE_FAILED_ACTIVATED(20017, "账户激活失败-账户已激活"),

    ROLE_QUERY_OK(30001, "查询角色信息成功"),
    ROLE_CREATE_FAILED_PARAMETER_ILLEGAL(30002, "新增角色信息失败-参数非法"),
    ROLE_CREATE_FAILED_EXIST(30003, "新增角色信息失败-角色已存在"),
    ROLE_CREATE_OK(30004, "新增角色信息成功"),
    ROLE_UPDATE_FAILED(30005, "更改角色信息失败-无此角色"),
    ROLE_UPDATE_OK(30006, "更改角色信息成功"),
    ROLE_DELETE_FAILED(30007, "删除角色信息失败-无此角色"),
    ROLE_DELETE_OK(30008, "删除角色信息成功"),

    USER_QUERY_OK(40001, "查询用户信息成功"),
    USER_QUERY_FAILED_NOT_FOUND(40002, "查询用户信息失败-无此用户"),
    USER_CREATE_FAILED_PARAMETER_ILLEGAL(40003, "创建用户信息失败-参数非法"),
    USER_CREATE_FAILED_EXIST(40004, "创建用户信息失败-用户已存在"),
    USER_CREATE_OK(40005, "创建用户信息成功"),
    USER_CREATE_OK_AND_ACCOUNT_ACTIVATE_FAILED(40006, "创建用户信息成功-但账户激活失败"),

    CATEGORY_QUERY_OK(50001, "查询类别信息成功"),
    CATEGORY_QUERY_FAILED_NOT_FOUND(50002, "查询类别信息失败-类别不存在"),
    CATEGORY_CREATE_OK(50003, "创建类别信息成功"),
    CATEGORY_CREATE_FAILED_ALREADY_EXIST(50004, "创建类别信息失败-类别已存在"),
    CATEGORY_DELETE_OK(50005, "删除类别信息成功"),
    CATEGORY_DELETE_FAILED_NOT_FOUND(50006, "删除类别信息失败-类别不存在"),
    CATEGORY_UPDATE_OK(50007, "更新类别信息成功"),
    CATEGORY_UPDATE_FAILED_ALREADY_EXIST(50008, "更新类别信息失败-类别已存在"),
    CATEGORY_UPDATE_FAILED_NOT_FOUND(50009, "更新类别信息失败-类别不存在"),
    CATEGORY_UPDATE_FAILED_ID_NULL(50010, "更新类别信息失败-ID为空"),


    TOTAL_GOODS_QUERY_BY_PAGE_OK(60001, "分页查询流水发货总账成功"),
    TOTAL_GOODS_CREATE_OK(60002, "创建流水发货记录成功"),
    TOTAL_GOODS_CREATE_FAILED_PARAMETER_ILLEGAL(60003, "创建流水发货记录失败-参数非法"),
    TOTAL_GOODS_CREATE_FAILED_CATEGORY_EXIST(60004, "创建流水发货记录失败-类别不存在"),
    TOTAL_GOODS_CREATE_FAILED_USER_EXIST(60005, "创建流水发货记录失败-用户不存在"),
    TOTAL_GOODS_EXCEL_UPLOAD_OK(60006, "上传账户流水明细Excel文件成功"),
    TOTAL_GOODS_EXCEL_UPLOAD_FAILED_FILE_NOT_FOUND(60007, "上传账户流水发货Excel文件失败-文件不存在"),
    TOTAL_GOODS_EXCEL_UPLOAD_FAILED_EXCEL_NOT_FOUND(60008, "上传账户流水发货Excel文件失败-文件非.xls或.xlsx"),
    TOTAL_GOODS_EXCEL_UPLOAD_FAILED_IO_EXCEPTION(60009, "上传账户流水发货Excel文件失败-IO异常"),

    ACCOUNT_DETAILS_QUERY_OK(70001, "查询账户明细成功"),
    ACCOUNT_DETAILS_QUERY_FAILED_NOT_FOUND(70002, "查询账户明细失败-记录不存在"),
    ACCOUNT_DETAILS_CREATE_OK(70003, "创建账户明细成功"),
    ACCOUNT_DETAILS_CREATE_FAILED_ALREADY_EXIST(70004, "创建账户明细失败-记录已存在"),
    ACCOUNT_DETAILS_UPDATE_OK(70005, "更新账户明细成功"),
    ACCOUNT_DETAILS_DELETE_OK(70006, "删除账户明细成功"),
    ACCOUNT_DETAILS_DELETE_FAILED_NOT_FOUND(70007, "删除账户明细失败-记录不存在"),
    ACCOUNT_DETAILS_EXCEL_UPLOAD_OK(70008, "上传账户流水明细Excel文件成功"),
    ACCOUNT_DETAILS_EXCEL_UPLOAD_FAILED_FILE_NOT_FOUND(70009, "上传账户流水明细Excel文件失败-文件不存在"),
    ACCOUNT_DETAILS_EXCEL_UPLOAD_FAILED_EXCEL_NOT_FOUND(70010, "上传账户流水明细Excel文件失败-文件非.xls或.xlsx"),
    ACCOUNT_DETAILS_EXCEL_UPLOAD_FAILED_IO_EXCEPTION(70011, "上传账户流水明细Excel文件失败-IO异常"),

    PAYMENT_TYPE_QUERY_OK(80001, "查询付款类型信息成功"),
    PAYMENT_TYPE_QUERY_FAILED_NOT_FOUND(80002, "查询付款类型信息失败-无此类别"),
    PAYMENT_TYPE_CREATE_FAILED_ALREADY_EXIST(80003, "创建付款类型信息失败-类型已存在"),
    PAYMENT_TYPE_CREATE_OK(80004, "创建付款类型信息成功"),
    PAYMENT_TYPE_UPDATE_FAILED_ID_NULL(80005, "更新付款类型信息失败-ID为空"),
    PAYMENT_TYPE_UPDATE_FAILED_NOT_FOUND(80006, "更新付款类型信息失败-类型不存在"),
    PAYMENT_TYPE_UPDATE_FAILED_ALREADY_EXIST(80007, "更新付款类型信息失败-类型已存在"),
    PAYMENT_TYPE_UPDATE_OK(80008, "更新付款类型信息成功"),
    PAYMENT_TYPE_DELETE_FAILED(80009, "删除付款类型信息失败-类型不存在"),
    PAYMENT_TYPE_DELETE_OK(80010, "删除付款类型信息成功"),

    INVOICE_OUT_EXCEL_UPLOAD_FAILED_FILE_NOT_FOUND(90001, "对外开出发票Excel文件上传失败-文件不存在"),
    INVOICE_OUT_EXCEL_UPLOAD_FAILED_EXCEL_NOT_FOUND(90002, "对外开出发票Excel文件上传失败-文件非.xls或.xlsx"),
    INVOICE_OUT_EXCEL_UPLOAD_FAILED_IO_EXCEPTION(90003, "上传对外开出发票Excel文件失败-IO异常"),
    INVOICE_OUT_EXCEL_UPLOAD_OK(90004, "上传对外开出发票Excel文件成功"),

    MATERIAL_OVERVIEW_QUERY_OK(),
    MATERIAL_OVERVIEW_QUERY_FAILED_USER_NOT_FOUND(10001, "材料单预览查询失败-用户不存在"),
    MATERIAL_OVERVIEW_QUERY_FAILED_MATERIAL_TYPE_NOT_FOUND(10001, "材料单预览查询失败-材料类别不存在"),
    MATERIAL_OVERVIEW_QUERY_FAILED_FILED_NULL(10002, "材料单预览查询失败-参数为空"),
    MATERIAL_OVERVIEW_CREATE_OK(10002, "材料单预览创建成功"),
    MATERIAL_OVERVIEW_CREATE_FAILED_FILED_ILLEGAL(10002, "材料单预览创建失败-参数非法"),

    MATERIAL_TYPE_QUERY_OK(11001, "材料类别查询成功"),
    MATERIAL_TYPE_QUERY_FAILED(11001, "材料商类别查询失败-类别不存在"),
    MATERIAL_TYPE_CREATE_OK(11001, "材料类别创建成功"),

    ;

    private long code;
    private String message;

    ErrorCodeEnum() {
    }

    ErrorCodeEnum(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
