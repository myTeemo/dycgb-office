package com.dycgb.office.common.repository;

import com.dycgb.office.common.model.AccountDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 流水总入账数据库访问类
 */
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long>, JpaSpecificationExecutor<AccountDetails> {

    Optional<AccountDetails> findAccountDetailsById(Long id);

    List<AccountDetails> findAccountDetailsByDocumentSeqAndDocumentNoAndDocumentDate(String documentSeq,
                                                                                     String documentNo, String documentDate);
    Page<AccountDetails> findAll(Pageable pageable);

    @Modifying
    @Transactional
    Integer deleteAccountDetailsById(Long id);
}
