package com.dycgb.office.common.repository;

import com.dycgb.office.common.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
    Optional<PaymentType> findPaymentTypeById(@NonNull Long Id);

    List<PaymentType> findPaymentTypeByNameLike(String name);

    List<PaymentType> findPaymentTypeByName(String name);

    @Modifying
    @Transactional
    Integer deletePaymentTypeById(@NonNull Long id);
}
