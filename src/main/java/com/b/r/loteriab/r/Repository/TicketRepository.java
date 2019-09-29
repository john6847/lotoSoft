package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Transactional
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket save(Ticket ticket);

    void deleteByIdAndEnterpriseId(Long id, Long enterpriseId);

    Ticket findTicketByIdAndEnterpriseId(Long id, Long enterpriseId);

    Ticket findTicketBySerialAndEnterpriseId(String serial, Long enterpriseId);

    Ticket findTicketByShortSerialAndEnterpriseId(String shortSerial, Long enterpriseId);

    List<Ticket> findAllByEnterpriseId(long id);

    Page<Ticket> findAllByEnterpriseId(Pageable pageable, Long enterpriseId);

    Page<Ticket> findAllByEnabledAndEnterpriseId(Pageable pageable, boolean enabled, Long enterpriseId);

    @Query("SELECT max(t.sequence) FROM Ticket t where t.enterprise = :enterprise")
    int selectMaxSequence(@Param("enterprise") Enterprise enterprise);
}
