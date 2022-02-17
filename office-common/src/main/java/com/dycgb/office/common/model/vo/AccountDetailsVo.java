package com.dycgb.office.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsVo {
    private String sd;
    private String ed;
    private Long cid;
    private Long uid;
    private String dno;
    private String keys;
    private String info;
    private Long payId;
}
