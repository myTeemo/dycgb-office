package com.dycgb.office.common.service;

import com.dycgb.office.common.model.PaymentType;

import java.util.List;

/**
 * @Description TODO
 * @Author myhe
 * @Date 2021/3/30 下午1:54
 */
public interface PaymentTypeService {
    PaymentType findPaymentTypeById(Long id);

    List<PaymentType> findAllPaymentTypes();

    PaymentType createPaymentType(PaymentType paymentType);

    PaymentType updatePaymentType(PaymentType paymentType);

    boolean deletePaymentTypeById(Long id);
}
