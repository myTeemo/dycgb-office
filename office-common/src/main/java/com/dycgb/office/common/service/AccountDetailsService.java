package com.dycgb.office.common.service;


import com.dycgb.office.common.model.AccountDetails;
import com.dycgb.office.common.model.excel.ExcelAccountDetails;
import com.dycgb.office.common.model.vo.AccountDetailsVo;
import com.dycgb.office.common.utils.Pager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * @Description 流水入账业务处理接口类
 * @Author myhe
 * @Date 2021/3/28 下午3:26
 */
public interface AccountDetailsService {

    AccountDetails findAccountDetailsById(Long id);

    AccountDetails createAccountDetails(AccountDetails accountDetails);

    List<AccountDetails> createAccountDetails(List<ExcelAccountDetails> excelAccountDetailsList);

    Pager<AccountDetails> findAccountDetailsByPage(Integer page, Integer pageSize, AccountDetailsVo accountDetailsVo);

    AccountDetails updateAccountDetails(AccountDetails accountDetails);

    boolean deleteAccountDetailsById(Long id);

    void readAccountDetailsExcel(MultipartFile file) throws IOException;

    AccountDetails imageUpload(MultipartFile img, Long id, String documentNo) throws IOException;
}
