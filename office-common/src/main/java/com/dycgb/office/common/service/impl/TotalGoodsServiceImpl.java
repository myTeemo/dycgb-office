package com.dycgb.office.common.service.impl;

import com.alibaba.excel.EasyExcel;
import com.dycgb.office.common.exception.ParametersIllegalException;
import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.Category;
import com.dycgb.office.common.model.Product;
import com.dycgb.office.common.model.TotalGoods;
import com.dycgb.office.common.model.User;
import com.dycgb.office.common.model.excel.ExcelTotalGoods;
import com.dycgb.office.common.model.vo.TotalGoodsVo;
import com.dycgb.office.common.repository.TotalGoodsRepository;
import com.dycgb.office.common.service.CategoryService;
import com.dycgb.office.common.service.ProductService;
import com.dycgb.office.common.service.TotalGoodsService;
import com.dycgb.office.common.service.UserService;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import com.dycgb.office.common.utils.FileConstants;
import com.dycgb.office.common.utils.Pager;
import com.dycgb.office.common.utils.excel.TotalGoodsExcelListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Description 流水发货业务实现类
 * @Author myhe
 * @Date 2021/4/12 下午3:34
 */

@Service
public class TotalGoodsServiceImpl implements TotalGoodsService {
    @Resource
    private TotalGoodsRepository totalGoodsRepository;

    @Resource
    private UserService userService;
    @Resource
    private ProductService productService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private FileConstants fileConstants;

    private final Map<String, User> usersMap = new HashMap<>();
    private final Map<String, Product> productsMap = new HashMap<>();
    private final Map<String, Category> categoriesMap = new HashMap<>();

    /**
     * 上传流水发货Excel文件
     *
     * @param file EXCEL
     */
    @Override
    public void excelUpload(MultipartFile file) throws IOException {
        if (file == null)
            throw new ResourceNotFoundException(ErrorCodeEnum.TOTAL_GOODS_EXCEL_UPLOAD_FAILED_FILE_NOT_FOUND);
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            throw new ResourceNotFoundException(ErrorCodeEnum.TOTAL_GOODS_EXCEL_UPLOAD_FAILED_EXCEL_NOT_FOUND);
        }
        EasyExcel.read(file.getInputStream(), ExcelTotalGoods.class, new TotalGoodsExcelListener(this)).sheet().doRead();
    }

    /**
     * 根据流水发货Excel文件批量创建发货记录
     *
     * @param excelTotalGoodsList Excel解析出来的发货记录列表
     */
    @Override
    public List<TotalGoods> createTotalGoodsByExcel(List<ExcelTotalGoods> excelTotalGoodsList) {
        List<User> users = userService.findAllUsers();
        List<Product> products = productService.findAllProducts();
        List<Category> categories = categoryService.findAllCategories();

        // TODO 用户可能存在于多个类别中需要考虑解决
        for (User user : users) {
            usersMap.put(user.getName(), user);
        }

        for (Product product : products) {
            productsMap.put(product.getName(), product);
        }

        for (Category category : categories) {
            categoriesMap.put(category.getName(), category);
        }

        List<TotalGoods> totalGoodsList = new ArrayList<>();

        for (ExcelTotalGoods excelTotalGoods : excelTotalGoodsList) {
            TotalGoods totalGoods = excelTotalGoods.converter();
            String userName = totalGoods.getUser().getName();
            String productName = totalGoods.getProduct().getName();

            if (usersMap.containsKey(userName)) {
                totalGoods.setUser(usersMap.get(userName));
            } else {
                User user = totalGoods.getUser();
                if (!categoriesMap.containsKey(user.getCategory().getName())) {
                    Category createdCategory = categoryService.createCategory(user.getCategory());
                    categoriesMap.put(createdCategory.getName(), createdCategory);
                }
                user.setCategory(categoriesMap.get(user.getCategory().getName()));
                User createdUser = userService.createUser(user);
                usersMap.put(user.getName(), createdUser);
            }

            if (productsMap.containsKey(productName)) {
                totalGoods.setProduct(productsMap.get(productName));
            } else {
                Product product = productService.createProduct(totalGoods.getProduct());
                productsMap.put(product.getName(), product);
            }

            totalGoodsList.add(totalGoods);
        }
        usersMap.clear();
        productsMap.clear();
        categoriesMap.clear();

        return totalGoodsRepository.saveAll(totalGoodsList);
    }

    /**
     * 分页查询流水发货明细
     *
     * @param page         第 page 页
     * @param pageSize     页大小
     * @param totalGoodsVo 传入查询条件
     */
    @Override
    public Pager<TotalGoods> findTotalGoodsByPage(Integer page, Integer pageSize, TotalGoodsVo totalGoodsVo) {
        page = page > 0 ? page - 1 : 0;
        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "documentDate"));
        Page<TotalGoods> p;
        if (totalGoodsVo == null) {
            p = totalGoodsRepository.findAll(pageable);
        } else {
            Specification<TotalGoods> specification = new Specification<TotalGoods>() {
                @Override
                public Predicate toPredicate(Root<TotalGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate predicate = cb.conjunction();
                    if (totalGoodsVo.getSd() != null && !totalGoodsVo.getSd().trim().equals("")) {
                        predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("documentDate"), totalGoodsVo.getSd()));
                    }

                    if (totalGoodsVo.getEd() != null && !totalGoodsVo.getEd().trim().equals("")) {
                        predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("documentDate"), totalGoodsVo.getEd()));
                    }

                    if (totalGoodsVo.getUid() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("user"), totalGoodsVo.getUid()));
                    }

                    if (totalGoodsVo.getDno() != null && !totalGoodsVo.getDno().trim().equals("")) {
                        predicate.getExpressions().add(cb.like(root.get("documentNo"), "%" + totalGoodsVo.getDno() + "%"));
                    }

                    if (totalGoodsVo.getPid() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("product"), totalGoodsVo.getPid()));
                    }

                    if (totalGoodsVo.getAddress() != null && !totalGoodsVo.getAddress().trim().equals("")) {
                        predicate.getExpressions().add(cb.like(root.get("address"), "%" + totalGoodsVo.getAddress() + "%"));
                    }
                    return predicate;
                }
            };
            p = totalGoodsRepository.findAll(specification, pageable);
        }
        return new Pager<>(page == 0 ? 1 : page + 1, p.getSize(), pageable.getOffset(), p.getTotalPages(), p.hasNext(), p.getContent(), p.getTotalElements());
    }

    /**
     * 根据ID查询发货记录
     *
     * @param id 发货ID
     * @return 查询结果
     */
    @Override
    public TotalGoods findTotalGoodsById(@NonNull Long id) {
        Optional<TotalGoods> optionalTotalGoods = totalGoodsRepository.findById(id);
        if (optionalTotalGoods.isPresent()) {
            return optionalTotalGoods.get();
        }
        throw new ResourceNotFoundException(ErrorCodeEnum.TOTAL_GOODS_QUERY_FAILED_NOT_FOUND);
    }

    /**
     * 更新发货单
     *
     * @param totalGoods 发货单
     * @return 更新后的发货单
     */
    @Override
    public TotalGoods updateGoods(TotalGoods totalGoods) {
        findTotalGoodsById(totalGoods.getId());
        return totalGoodsRepository.save(totalGoods);
    }


    /**
     * 上传图片
     *
     * @param img        图片
     * @param id         发货单ID
     * @param documentNo 发货单序号
     * @return 更新后发货单
     * @throws IOException
     */
    @Override
    public TotalGoods imageUpload(MultipartFile img, Long id, String documentNo) throws IOException {
        TotalGoods oTotalGoods = findTotalGoodsById(id);
        if (oTotalGoods.getDocumentNo().equals(documentNo)) {
            String formatDocumentDate = oTotalGoods.getDocumentDate().replace("/", "");
            String fileName = String.format("%s-%s-%s-%s-%s-%s", oTotalGoods.getDocumentSeq(), oTotalGoods.getDocumentNo(), formatDocumentDate, oTotalGoods.getUser().getName(), String.format("%s%s%s%s", oTotalGoods.getAddress(), oTotalGoods.getCount(), oTotalGoods.getProduct().getUnit(), oTotalGoods.getProduct().getName()), oTotalGoods.getMoney());
            String suffix = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
            fileName += suffix;
            File nImg = new File(fileConstants.getGoodsPath() + fileName);
            oTotalGoods.setFileName(fileName);
            TotalGoods nTotalGoods = updateGoods(oTotalGoods);
            img.transferTo(nImg);
            return nTotalGoods;
        }
        throw new ParametersIllegalException(ErrorCodeEnum.TOTAL_GOODS_IMG_UPLOAD_FAILED_PARAMETERS_ILLEGAL);
    }

    @Override
    public boolean deleteTotalGoodsById(Long id) {
        if (totalGoodsRepository.deleteTotalGoodsById(id) > 0) {
            return true;
        }
        throw new ResourceNotFoundException(ErrorCodeEnum.TOTAL_GOODS_DELETE_FAILED_NOT_FOUND);
    }
}
