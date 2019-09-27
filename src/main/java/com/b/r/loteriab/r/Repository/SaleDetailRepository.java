package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Dany on 04/05/2019.
 */
@Transactional
@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {
}
