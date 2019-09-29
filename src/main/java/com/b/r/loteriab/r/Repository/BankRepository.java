package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Transactional
@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    Bank findBankByIdAndEnterpriseId(Long id, Long enterpriseId);

    Bank findBankByPosIdAndEnterpriseId(Long id, Long enterpriseId);

    Bank findBankBySellerIdAndEnterpriseId(Long sellerId, Long enterpriseId);

    Page<Bank> findAllByEnabledAndEnterpriseIdOrderByIdDesc(boolean state, Long enterpriseId, Pageable pageable);

    Page<Bank> findAllByEnterpriseIdOrderByIdDesc(Long enterpriseId, Pageable pageable);

    List<Bank> findAllByEnterpriseIdOrderByIdDesc(Long enterpriseId);

    List<Bank> findAllByEnabledAndEnterpriseId(Boolean enabled, Long enterpriseId);

    Bank save(Bank bank);

    Bank findTopByEnterpriseIdOrderByEnterpriseIdDesc(Long enterpriseId);
}
