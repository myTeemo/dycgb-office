package com.dycgb.office.common.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description 文件路径配置
 * @Author myhe
 * @Date 2021/4/22 下午3:32
 */
@Component
@Data
public class FileConstants {

    @Value("${dycgb.office.file.path.base}")
    private String basePath;

    @Value("${dycgb.office.file.path.account-details}")
    private String accountDetailsPath;

    @Value("${dycgb.office.file.path.goods}")
    private String goodsPath;

    @Value("${dycgb.office.file.path.invoices}")
    private String invoicesPath;
}
