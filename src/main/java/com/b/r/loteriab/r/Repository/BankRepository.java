package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Bank;
import com.b.r.loteriab.r.Model.Pos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    Bank findBankByIdAndEnterpriseId(Long id, Long enterpriseId);
    Bank findBankByIdAndEnabledAndEnterpriseId(Long id, boolean enabled, Long enterpriseId);

    Page<Bank> findAllByEnabledAndEnterpriseId(Pageable pageable, boolean state, Long enterpriseId);
    Page<Bank> findAllByEnterpriseId(Pageable pageable, Long enterpriseId);
    List <Bank> findAllByEnterpriseId(Long enterpriseId);
    List<Bank> findAllByEnabledAndEnterpriseId(Boolean enabled, Long enterpriseId);

    void deleteByIdAndEnterpriseId(Long id, Long enterpriseId);

    Bank save(Bank bank);

    Bank findBankBySerialAndEnterpriseId(String serial, Long enterpriseId);
    Bank findBySerialAndEnabledAndEnterpriseId(String serial, boolean enabled, Long enterpriseId);
    Bank findBankByDescriptionAndEnterpriseId(String description, Long enterpriseId);

    Bank findTopByEnterpriseIdOrderByEnterpriseIdDesc(Long enterpriseId);
}
