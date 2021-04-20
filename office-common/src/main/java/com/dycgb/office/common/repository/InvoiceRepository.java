package com.dycgb.office.common.repository;

import com.dycgb.office.common.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
