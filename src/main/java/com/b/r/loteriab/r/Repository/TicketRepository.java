package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket save(Ticket ticket);

    Ticket findTicketById(Long id);
    Ticket findTicketByIdAndEnabled(Long id,boolean enabled);

    Ticket findBySerial(String name);
    Ticket findBySerialAndEnabled(String name, boolean enabled);

    List<Ticket> findAllByEnterpriseId(long id);

    Page<Ticket> findAll(Pageable pageable);
    Page<Ticket> findAllByEnabled(Pageable pageable, boolean enabled);

    @Query("SELECT MAX(e.sequence) FROM Ticket t")
    int selectMaxSequence();

}
