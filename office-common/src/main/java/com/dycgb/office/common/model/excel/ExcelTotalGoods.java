package com.dycgb.office.common.model.excel;

import com.dycgb.office.common.model.Category;
import com.dycgb.office.common.model.Product;
import com.dycgb.office.common.model.TotalGoods;
import com.dycgb.office.common.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description 流水发货记录Excel文件行定义
 * @Author myhe
 * @Date 2021/4/12 下午3:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelTotalGoods {
    private String date;
    private String seq;
    private String number;
    private String userName;
    private String content;
    private String productName;
    private String unit;
    private String count;
    private String money;
    private String address;
    private String remark;

    public TotalGoods converter() {

        User user = User.builder()
                .name(userName)
                .category(Category.builder().name("客户").build())
                .build();
        Product product = Product.builder()
                .name(productName)
                .unit(unit)
                .build();

        return TotalGoods.builder()
                .documentSeq(seq)
                .documentNo(number)
                .documentDate(date)
                .user(user)
                .product(product)
                .address(address)
                .content(content)
                .count(count != null ? new BigDecimal(count) : null)
                .money(money != null ? new BigDecimal(money) : null)
                .remark(remark)
                .build();
    }
}
