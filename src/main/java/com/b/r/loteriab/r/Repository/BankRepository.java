package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Transactional
@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    String q = "SELECT * FROM bank b WHERE lower(b.description) IN\n" +
            "(select lower(ba.description) from bank ba where ba.id != ?1 and lower(ba.description) IN (lower(?2)))\n" +
            "and b.enterprise_id =?3 and b.enabled= true limit 1";

    Bank findBankByIdAndEnterpriseId(Long id, Long enterpriseId);

    Bank findBankByDescriptionIgnoreCaseAndEnterpriseId(String name, Long enterpriseId);

    Bank findBankByPosIdAndEnterpriseId(Long id, Long enterpriseId);

    Bank findBankBySellerIdAndEnterpriseId(Long sellerId, Long enterpriseId);

    Page<Bank> findAllByEnabledAndEnterpriseIdOrderByIdDesc(boolean state, Long enterpriseId, Pageable pageable);

    Page<Bank> findAllByEnterpriseIdOrderByIdDesc(Long enterpriseId, Pageable pageable);

    List<Bank> findAllByEnterpriseIdOrderByIdDesc(Long enterpriseId);

    List<Bank> findAllByEnabledAndEnterpriseId(Boolean enabled, Long enterpriseId);

    Bank save(Bank bank);

    Bank findTopByEnterpriseIdOrderByIdDesc(Long enterpriseId);

    @Query(value = q, nativeQuery = true)
    List<Bank> selectbankIfExist(Long bankId, String name, Long enterpriseId);


}
