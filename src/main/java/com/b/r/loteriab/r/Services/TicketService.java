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
}
