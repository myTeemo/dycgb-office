package com.dycgb.office.common.model.excel;

import com.dycgb.office.common.model.Category;
import com.dycgb.office.common.model.Company;
import com.dycgb.office.common.model.Invoice;
import com.dycgb.office.common.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description 发票开出Excel 每行定义
 * @Author myhe
 * @Date 2021/4/11 上午11:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelInvoice {
    private String userName; //发票持有人
    private String number; //发票号码
    private String buyerName;  // 购方名称
    private String buyerTaxCode; // 购方税号
    private String buyerAddress; // 购方地址
    private String buyerTel; // 购方电话
    private String buyerBankName; // 购方银行名称
    private String buyerBankAccount; // 购方银行账户
    private String date; //发票日期
    private String status; // 发票状态
    private String yearAndMonth; // 发票所属年月
    private String moneyWithOutTax; // 除税金额
    private String taxRate; // 税率
    private String taxMoney; // 税额
    private String product; // 产品名称
    private String projectName; // 工程项目名称
    private String projectAddress; // 工程项目地址
    private String remark; // 备注

    /**
     * Excel 单元格转换为实体类
     */
    public Invoice converter() {
        User user = User.builder()
                .name(userName)
                .category(Category.builder().name("客户").build())
                .build();

        Company buyer = Company.builder()
                .name(buyerName)
                .taxCode(buyerTaxCode)
                .address(buyerAddress)
                .tel(buyerTel)
                .bankName(buyerBankName)
                .bankAccount(buyerBankAccount)
                .build();

        Company seller = Company.builder()
                .name("苏州市登月彩钢板活动房有限公司")
                .taxCode("91320509MA1MK3YW4P")
                .address("苏州市吴江区震泽镇兴华村13组")
                .tel("13776158177")
                .bankName("苏州农村商业银行震泽支行")
                .bankAccount("0706678211120404140441")
                .build();

        return Invoice.builder()
                .number(number)
                .buyer(buyer)
                .date(date)
                .status(status)
                .yearAndMonth(yearAndMonth)
                .moneyWithoutTax(moneyWithOutTax)
                .taxRate(taxRate)
                .taxMoney(taxMoney)
                .productName(product)
                .projectName(projectName)
                .projectAddress(projectAddress)
                .remark(remark)
                .seller(seller)
                .moneyWithRate(new BigDecimal(Double.toString(Double.parseDouble(moneyWithOutTax) + Double.parseDouble(taxMoney))))
                .mTaxRate(new BigDecimal("0.11"))
                .mTaxMoney(new BigDecimal(Double.toString(Double.parseDouble(moneyWithOutTax) + Double.parseDouble(taxMoney))).multiply(new BigDecimal("0.11")))
                .user(user)
                .build();
    }
}
