package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket save(Ticket ticket);

    Ticket findTicketByIdAndEnterpriseId(Long id, Long enterpriseId);
    Ticket findTicketBySerialAndEnterpriseId(String serial, Long enterpriseId);
    Ticket findTicketByIdAndEnabledAndEnterpriseId(Long id,boolean enabled, Long enterpriseId);

    Ticket findBySerialAndEnterpriseId(String name, Long enterpriseId);
    Ticket findBySerialAndEnabledAndEnterpriseId(String name, boolean enabled, Long enterpriseId);

    List<Ticket> findAllByEnterpriseId(long id);

    Page<Ticket> findAllByEnterpriseId(Pageable pageable, Long enterpriseId);
    Page<Ticket> findAllByEnabledAndEnterpriseId(Pageable pageable, boolean enabled, Long enterpriseId);

    @Query("SELECT max(t.sequence) FROM Ticket t where t.enterprise = :enterprise")
    int selectMaxSequence(@Param("enterprise")Enterprise enterprise);

    List<Ticket> findAllByWonTrueAndEnterpriseIdAndEmissionDateAndShiftId(Long enterpriseId, Date emissionDate, Long shiftId);

}
