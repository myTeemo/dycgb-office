package com.dycgb.office.common.service.impl;

import com.dycgb.office.common.exception.ResourceAlreadyExistException;
import com.dycgb.office.common.exception.ResourceNotFoundException;
import com.dycgb.office.common.model.PaymentType;
import com.dycgb.office.common.repository.PaymentTypeRepository;
import com.dycgb.office.common.service.PaymentTypeService;
import com.dycgb.office.common.utils.ErrorCodeEnum;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Description 付款方式实现类
 * @Author myhe
 * @Date 2021/3/30 下午1:55
 */
@Service
public class PaymentTypeServiceImpl implements PaymentTypeService {
    @Resource
    private PaymentTypeRepository paymentTypeRepository;

    /**
     * 根据ID查询付款方式
     *
     * @param id 付款方式ID
     * @return 付款方式信息
     */
    @Override
    public PaymentType findPaymentTypeById(@NonNull Long id) {
        Optional<PaymentType> paymentTypeById = paymentTypeRepository.findPaymentTypeById(id);
        if (paymentTypeById.isPresent()) {
            return paymentTypeById.get();
        }
        throw new ResourceNotFoundException(ErrorCodeEnum.PAYMENT_TYPE_QUERY_FAILED_NOT_FOUND);
    }

    /**
     * 查询所有付款方式信息
     *
     * @return 付款方式信息列表
     */
    @Override
    public List<PaymentType> findAllPaymentTypes() {
        return paymentTypeRepository.findAll();
    }

    /**
     * 创建一个付款方式
     *
     * @param paymentType 付款方式信息
     * @return 创建结果
     */
    @Override
    public PaymentType createPaymentType(PaymentType paymentType) {

        List<PaymentType> paymentTypes = paymentTypeRepository.findPaymentTypeByNameLike("%" + paymentType.getName() + "%");
        if (paymentTypes.isEmpty()) {
            return paymentTypeRepository.save(paymentType);
        }
        throw new ResourceAlreadyExistException(ErrorCodeEnum.PAYMENT_TYPE_CREATE_FAILED_ALREADY_EXIST);
    }

    /**
     * 更新付款方式信息
     *
     * @param paymentType 付款类型
     * @return 更新结果
     */
    @Override
    public PaymentType updatePaymentType(PaymentType paymentType) {
        if (paymentType.getId() == null) {
            throw new NullPointerException(ErrorCodeEnum.PAYMENT_TYPE_UPDATE_FAILED_ID_NULL.getMessage());
        }

        Optional<PaymentType> oldPaymentType = paymentTypeRepository.findPaymentTypeById(paymentType.getId());
        if (oldPaymentType.isPresent()) {
            List<PaymentType> paymentTypes = paymentTypeRepository.findPaymentTypeByNameLike(paymentType.getName());
            if (paymentTypes.isEmpty()) {
                return paymentTypeRepository.save(paymentType);
            }
            throw new ResourceAlreadyExistException(ErrorCodeEnum.PAYMENT_TYPE_UPDATE_FAILED_ALREADY_EXIST);
        }
        throw new ResourceNotFoundException(ErrorCodeEnum.PAYMENT_TYPE_UPDATE_FAILED_NOT_FOUND);
    }

    /**
     * 删除一个付款方式信息
     *
     * @param id 付款方式ID
     * @return 删除结果
     */
    @Override
    public boolean deletePaymentTypeById(Long id) {
        if (paymentTypeRepository.deletePaymentTypeById(id) > 0) {
            return true;
        }
        throw new ResourceNotFoundException(ErrorCodeEnum.PAYMENT_TYPE_DELETE_FAILED);
    }
}
