package com.dycgb.office.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalGoodsVo {
    private String sd;
    private String ed;
    private Long uid;
    private String dno;
    private Long pid;
    private String address;
}
