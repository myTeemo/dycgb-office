package com.dycgb.office.common.service.impl;

import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.MaterialDetails;
import com.dycgb.office.common.model.MaterialOverview;
import com.dycgb.office.common.model.MaterialType;
import com.dycgb.office.common.model.User;
import com.dycgb.office.common.repository.MaterialOverviewRepository;
import com.dycgb.office.common.repository.MaterialTypeRepository;
import com.dycgb.office.common.service.MaterialDetailsService;
import com.dycgb.office.common.service.MaterialOverviewService;
import com.dycgb.office.common.service.MaterialTypeService;
import com.dycgb.office.common.service.UserService;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description 材料记录预览实现类
 * @Author myhe
 * @Date 2021/4/13 上午10:40
 */
@Service
public class MaterialOverviewServiceImpl implements MaterialOverviewService {
    @Resource
    private MaterialOverviewRepository materialOverviewRepository;
    @Resource
    private MaterialDetailsService materialDetailsService;
    @Resource
    private MaterialTypeService materialTypeService;
    @Resource
    private UserService userService;

    /**
     * 创建一条材料单
     *
     * @param materialOverview 材料单
     */
    @Override
    public MaterialOverview createMaterial(MaterialOverview materialOverview) {
        if (materialOverview.getUser() != null && materialOverview.getUser().getId() != null &&
                materialOverview.getMaterialType() != null && materialOverview.getMaterialType().getId() != null) {
            try {
                userService.finUserById(materialOverview.getUser().getId());
            } catch (ResourceNotFoundException re) {
                throw new ResourceNotFoundException(ErrorCodeEnum.MATERIAL_OVERVIEW_QUERY_FAILED_USER_NOT_FOUND);
            }
            try {
                materialTypeService.findMaterialTypeById(materialOverview.getMaterialType().getId());
            } catch (ResourceNotFoundException re) {
                throw new ResourceNotFoundException(ErrorCodeEnum.MATERIAL_OVERVIEW_QUERY_FAILED_MATERIAL_TYPE_NOT_FOUND);
            }
            List<MaterialDetails> materialDetailsList = materialOverview.getMaterialDetailsList();
            BigDecimal sumMoney = new BigDecimal("0.00");

            for (MaterialDetails materialDetails : materialDetailsList) {
                if (materialDetails.getId() == null) {
                    BigDecimal price = materialDetails.getPrice();
                    BigDecimal count = materialDetails.getCount();
                    BigDecimal money = price.multiply(count);

                    if (materialDetails.getMoney() == null) {
                        materialDetails.setMoney(money);
                    } else {
                        materialDetails.setMoney(money.min(materialDetails.getMoney()));
                    }
                    sumMoney = sumMoney.add(materialDetails.getMoney());
                } else {
                    throw new ResourceNotFoundException(ErrorCodeEnum.MATERIAL_OVERVIEW_CREATE_FAILED_FILED_ILLEGAL);
                }
            }
            materialOverview.setMoney(sumMoney);
            return materialOverviewRepository.save(materialOverview);
        }
        throw new ResourceNotFoundException(ErrorCodeEnum.MATERIAL_OVERVIEW_QUERY_FAILED_FILED_NULL);
    }
}
