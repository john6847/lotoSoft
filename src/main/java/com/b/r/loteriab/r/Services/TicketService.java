package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Ticket;
import com.b.r.loteriab.r.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket save(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    private Ticket findById(Long id, Long enterpriseId){return ticketRepository.findTicketByIdAndEnterpriseId(id, enterpriseId);}
    private Ticket findByIdAndEnabled(Long id,boolean enabled, Long enterpriseId){
        return ticketRepository.findTicketByIdAndEnabledAndEnterpriseId(id,enabled, enterpriseId);
    }

    private Ticket findBySerial(String serial, Long enterpriseId){
        return ticketRepository.findBySerialAndEnterpriseId(serial, enterpriseId);
    }

    private Ticket findBySerialAndEnabled(String serial,boolean enabled, Long enterpriseId){
        return ticketRepository.findBySerialAndEnabledAndEnterpriseId(serial,enabled, enterpriseId);
    }



}
