package com.dycgb.office.common.service.impl;

import com.alibaba.excel.EasyExcel;
import com.dycgb.office.common.exception.ParametersIllegalException;
import com.dycgb.office.common.exception.ResourceAlreadyExistException;
import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.AccountDetails;
import com.dycgb.office.common.model.Category;
import com.dycgb.office.common.model.PaymentType;
import com.dycgb.office.common.model.User;
import com.dycgb.office.common.model.excel.ExcelAccountDetails;
import com.dycgb.office.common.model.vo.AccountDetailsVo;
import com.dycgb.office.common.repository.AccountDetailsRepository;
import com.dycgb.office.common.service.AccountDetailsService;
import com.dycgb.office.common.service.CategoryService;
import com.dycgb.office.common.service.PaymentTypeService;
import com.dycgb.office.common.service.UserService;
import com.dycgb.office.common.utils.CustomResponse;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import com.dycgb.office.common.utils.FileConstants;
import com.dycgb.office.common.utils.Pager;
import com.dycgb.office.common.utils.excel.AccountDetailsExcelListener;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Description 流水入账业务处理实现类
 * @Author myhe
 * @Date 2021/3/28 下午3:26
 */
@Service
public class AccountDetailsServiceImpl implements AccountDetailsService {
    @Resource
    private AccountDetailsRepository accountDetailsRepository;
    @Resource
    private CategoryService categoryService;
    @Resource
    private PaymentTypeService paymentTypeService;
    @Resource
    private UserService userService;

    @Resource
    private FileConstants fileConstants;

    private Map<String, Category> categoriesMap = new HashMap<>();
    private Map<String, PaymentType> paymentTypesMap = new HashMap<>();
    private Map<String, User> usersMap = new HashMap<>();


    /**
     * 根据ID查询账户流水明细
     *
     * @param id 入账记录ID
     * @return 流水入账结果
     */
    @Override
    public AccountDetails findAccountDetailsById(@NonNull Long id) {
        Optional<AccountDetails> accountDetails = accountDetailsRepository.findAccountDetailsById(id);
        if (accountDetails.isPresent()) {
            return accountDetails.get();
        }
        throw new ResourceNotFoundException(ErrorCodeEnum.ACCOUNT_DETAILS_QUERY_FAILED_NOT_FOUND);
    }

    /**
     * 分页查询账户流水明细
     *
     * @param page             第 page 页
     * @param pageSize         每页大小
     * @param accountDetailsVo 查询条件
     * @return 分页列表
     */
    @Override
    public Pager<AccountDetails> findAccountDetailsByPage(Integer page, Integer pageSize, AccountDetailsVo accountDetailsVo) {
        page = page > 0 ? page - 1 : 0;
        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "documentDate"));
        Page<AccountDetails> p;
        if (accountDetailsVo == null) {
            p = accountDetailsRepository.findAll(pageable);
        } else {
            Specification<AccountDetails> specification = new Specification() {
                @Override
                public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                    Predicate predicate = cb.conjunction();
                    if (accountDetailsVo.getSd() != null && !accountDetailsVo.getSd().trim().equals("")) {
                        predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("documentDate").as(String.class), accountDetailsVo.getSd()));
                    }

                    if (accountDetailsVo.getEd() != null && !accountDetailsVo.getEd().trim().equals("")) {
                        predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("documentDate").as(String.class), accountDetailsVo.getEd()));
                    }

                    if (accountDetailsVo.getCid() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("category"), accountDetailsVo.getCid()));
                    }

                    if (accountDetailsVo.getUid() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("user"), accountDetailsVo.getUid()));
                    }

                    if (accountDetailsVo.getPayId() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("paymentType"), accountDetailsVo.getPayId()));
                    }

                    if (accountDetailsVo.getDno() != null && !accountDetailsVo.getDno().trim().equals("")) {
                        predicate.getExpressions().add(cb.like(root.get("documentNo"), "%" + accountDetailsVo.getDno() + "%"));
                    }

                    if (accountDetailsVo.getKeys() != null && !accountDetailsVo.getKeys().trim().equals("")) {
                        predicate.getExpressions().add(cb.like(root.get("content"), "%" + accountDetailsVo.getKeys() + "%"));
                    }

                    if (accountDetailsVo.getInfo() != null && !accountDetailsVo.getInfo().trim().equals("")) {
                        predicate.getExpressions().add(cb.like(root.get("counterpartyInformation"), "%" + accountDetailsVo.getInfo() + "%"));
                    }

                    return predicate;
                }
            };
            p = accountDetailsRepository.findAll(specification, pageable);
        }
        return new Pager<>(page == 0 ? 1 : page + 1, p.getSize(), pageable.getOffset(), p.getTotalPages(), p.hasNext(), p.getContent(), p.getTotalElements());
    }

    /**
     * 创建一个账户明细信息
     *
     * @param accountDetails 账户明细
     * @return
     */
    @Override
    public AccountDetails createAccountDetails(AccountDetails accountDetails) {
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAccountDetailsByDocumentSeqAndDocumentNoAndDocumentDate(accountDetails.getDocumentSeq(), accountDetails.getDocumentNo(), accountDetails.getDocumentDate());
        if (accountDetailsList.isEmpty()) {
            return accountDetailsRepository.save(accountDetails);
        }
        throw new ResourceAlreadyExistException(ErrorCodeEnum.ACCOUNT_DETAILS_CREATE_FAILED_ALREADY_EXIST);
    }

    /**
     * 批量创建账户明细
     *
     * @param excelAccountDetailsList 待创建账户明细列表
     */
    @Override
    public List<AccountDetails> createAccountDetails(List<ExcelAccountDetails> excelAccountDetailsList) {

        if (paymentTypesMap.size() == 0) {
            List<PaymentType> allPaymentTypes = paymentTypeService.findAllPaymentTypes();
            for (PaymentType paymentType : allPaymentTypes) {
                paymentTypesMap.put(paymentType.getName(), paymentType);
            }
        }

        if (categoriesMap.size() == 0) {
            List<Category> allCategories = categoryService.findAllCategories();
            for (Category category : allCategories) {
                categoriesMap.put(category.getName(), category);
            }
        }

        if (usersMap.size() == 0) {
            List<User> allUsers = userService.findAllUsers();
            for (User user : allUsers) {
                usersMap.put(user.getName(), user);
            }
        }

        List<AccountDetails> accountDetailsList = new ArrayList<>();

        for (ExcelAccountDetails excelAccountDetails : excelAccountDetailsList) {
            AccountDetails accountDetails = excelAccountDetails.converter();
            String userName = accountDetails.getUser().getName();
            String categoryName = accountDetails.getCategory().getName();
            String paymentTypeName = accountDetails.getPaymentType().getName();

            // 检测数据库中是否包含类别
            if (categoriesMap.containsKey(categoryName)) {
                accountDetails.getCategory().setId(categoriesMap.get(categoryName).getId());
            } else {
                Category category = categoryService.createCategory(Category.builder().name(categoryName).build());
                categoriesMap.put(category.getName(), category);
                accountDetails.getCategory().setId(category.getId());
            }

            // 检测是否包含付款方式
            if (paymentTypesMap.containsKey(paymentTypeName)) {
                accountDetails.getPaymentType().setId(paymentTypesMap.get(paymentTypeName).getId());
            } else {
                PaymentType paymentType = paymentTypeService.createPaymentType(PaymentType.builder().name(paymentTypeName).build());
                paymentTypesMap.put(paymentType.getName(), paymentType);
                accountDetails.getPaymentType().setId(paymentType.getId());
            }

            // 检测数据库中是否含有用户
            if (usersMap.containsKey(userName)) {
                accountDetails.getUser().setId(usersMap.get(userName).getId());
            } else {
                User user = userService.createUser(User.builder().name(userName).category(categoriesMap.get(categoryName)).build());
                usersMap.put(user.getName(), user);
                accountDetails.getUser().setId(user.getId());
            }
            accountDetailsList.add(accountDetails);
        }
        usersMap.clear();
        paymentTypesMap.clear();
        categoriesMap.clear();
        return accountDetailsRepository.saveAll(accountDetailsList);
    }

    /**
     * 更新账户明细记录
     *
     * @param accountDetails 待更新的账户明细
     */
    @Override
    public AccountDetails updateAccountDetails(AccountDetails accountDetails) {
        findAccountDetailsById(accountDetails.getId());
        return accountDetailsRepository.save(accountDetails);
    }

    @Override
    public boolean deleteAccountDetailsById(Long id) {
        if (accountDetailsRepository.deleteAccountDetailsById(id) > 0) {
            return true;
        }
        throw new ResourceNotFoundException(ErrorCodeEnum.ACCOUNT_DETAILS_DELETE_FAILED_NOT_FOUND);
    }

    /**
     * 读取流水明细 Excel 处理方法
     *
     * @param file Excel
     */
    @Override
    public void readAccountDetailsExcel(MultipartFile file) throws IOException {
        if (file == null)
            throw new ResourceNotFoundException(ErrorCodeEnum.ACCOUNT_DETAILS_EXCEL_UPLOAD_FAILED_FILE_NOT_FOUND);
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            throw new ResourceNotFoundException(ErrorCodeEnum.ACCOUNT_DETAILS_EXCEL_UPLOAD_FAILED_EXCEL_NOT_FOUND);
        }
        EasyExcel.read(file.getInputStream(), ExcelAccountDetails.class, new AccountDetailsExcelListener(this)).sheet().doRead();
    }

    @Override
    public AccountDetails imageUpload(MultipartFile img, Long id, String documentNo) throws IOException {
        AccountDetails oAccountDetails = findAccountDetailsById(id);
        if (oAccountDetails.getDocumentNo().equals(documentNo)) {
            String formatDocumentDate = oAccountDetails.getDocumentDate().replace("/", "");

            String fileName = String.format("%s-%s-%s-%s-%s-%s", oAccountDetails.getDocumentSeq(), oAccountDetails.getDocumentNo(), formatDocumentDate, oAccountDetails.getUser().getName(), oAccountDetails.getContent(), oAccountDetails.getIncome().compareTo(BigDecimal.ZERO) == 0 ? oAccountDetails.getExpense() : oAccountDetails.getIncome());
            String suffix = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
            fileName += suffix;
            File nImg = new File(fileConstants.getAccountDetailsPath() + fileName);

            oAccountDetails.setFileName(fileName);
            AccountDetails nAccountDetails = updateAccountDetails(oAccountDetails);
            img.transferTo(nImg);
            return nAccountDetails;
        }
        throw new ParametersIllegalException(ErrorCodeEnum.ACCOUNT_DETAILS_IMG_UPLOAD_FAILED_PARAMETERS_ILLEGAL);
    }
}
