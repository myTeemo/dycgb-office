package com.dycgb.office.common.model.excel;

import com.dycgb.office.common.model.AccountDetails;
import com.dycgb.office.common.model.Category;
import com.dycgb.office.common.model.PaymentType;
import com.dycgb.office.common.model.User;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 流水总账导出EXCEL的行数据定义
 * @Author myhe
 * @Date 2021/4/8 下午4:07
 */

@Data
public class ExcelAccountDetails {
    /**
     * 交易日期
     */
    private String documentDate;
    /**
     * 票号所在本数
     */
    private String documentSeq;
    /**
     * 票据编号
     */
    private String documentNo;
    /**
     * 类别
     */
    private String category;
    /**
     * 票据收付款人
     */
    private String user;
    /**
     * 事由
     */
    private String content;
    /**
     * 收入
     */
    private String income;
    /**
     * 支出
     */
    private String expense;

    /**
     * 付款方式
     */
    private String paymentType;
    /**
     * 对方信息
     */
    private String counterPartyInformation;

    /**
     * 备注
     */
    private String remark;

    public AccountDetails converter() {
        return AccountDetails.builder()
                .documentSeq(documentSeq)
                .documentDate(documentDate)
                .documentNo(documentNo)
                .category(Category.builder().name(category).build())
                .user(User.builder().name(user).build())
                .content(content)
                .income(new BigDecimal(income != null ? income : "0"))
                .expense(new BigDecimal(expense != null ? expense : "0"))
                .paymentType(PaymentType.builder().name(paymentType).build())
                .counterpartyInformation(counterPartyInformation)
                .remark(remark)
                .build();

    }
}
