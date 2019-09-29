package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.*;
import com.b.r.loteriab.r.Model.Enums.SaleSatus;
import com.b.r.loteriab.r.Model.ViewModel.Helper;
import com.b.r.loteriab.r.Model.ViewModel.SaleDetailViewModel;
import com.b.r.loteriab.r.Model.ViewModel.SaleViewModel;
import com.b.r.loteriab.r.Repository.CombinationRepository;
import com.b.r.loteriab.r.Repository.EnterpriseRepository;
import com.b.r.loteriab.r.Repository.PosRepository;
import com.b.r.loteriab.r.Repository.SellerRepository;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class ApiService {

    @Autowired
    private CombinationRepository combinationRepository;
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private PosRepository posRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private Helper helper;

    public Sale mapSale(SaleViewModel vm, Shift shift) {
        Sale sale = new Sale();
        sale.setSaleDetails(new ArrayList<>());
        sale.setEnabled(false);
        sale.setShift(shift);
        sale.setDate(new Date());
        sale.setSaleStatus(SaleSatus.SAVING.ordinal());
        Enterprise enterprise = enterpriseRepository.findEnterpriseById(vm.getEnterprise().getId());
        sale.setTotalAmount(vm.getTotalAmount());
        for (SaleDetailViewModel saleDetailVM : vm.getSaleDetails()) {
            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setEnabled(true);
            Combination combination = combinationRepository.findByResultCombinationAndCombinationTypeIdAndEnterpriseId(saleDetailVM.getCombination(), saleDetailVM.getCombinationTypeId(), enterprise.getId());
            saleDetail.setCombination(combination);
            saleDetail.setEnterprise(enterprise);
            saleDetail.setPrice(saleDetailVM.getPrice());
            saleDetail.setWon(false);
            sale.getSaleDetails().add(saleDetail);
        }
        sale.setPos(posRepository.findPosByIdAndEnterpriseIdAndDeletedFalse(vm.getPos().getId(), enterprise.getId()));
        sale.setEnterprise(enterprise);
        sale.setSeller(sellerRepository.findSellerByIdAndEnterpriseIdAndDeletedFalse(vm.getSeller().getId(), enterprise.getId()));
        Ticket ticket = new Ticket();
        ticket.setEnabled(true);
        ticket.setEmissionDate(new Date());
        ticket.setEnterprise(enterprise);
        ticket.setCompleted(false);
        ticket.setWon(false);
        ticket.setShift(shift);
        ticket.setAmountWon(0);
        Pair<String, Long> ticketSerial = helper.createNewTicketSerial(enterprise);
        ticket.setSerial(ticketSerial.getValue0());
        ticket.setSequence(ticketSerial.getValue1());
        ticket.setShortSerial(helper.generateString(true, 10));
        sale.setTicket(ticket);
        return sale;
    }
}
