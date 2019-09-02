package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Interaces.IReportViewModel;
import com.b.r.loteriab.r.Model.Sale;
import com.b.r.loteriab.r.Model.SaleDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {
    void deleteByIdAndEnterpriseId(Long id, Long enterpriseId);
}
