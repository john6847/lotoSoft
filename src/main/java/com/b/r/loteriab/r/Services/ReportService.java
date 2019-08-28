package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Enums.Shifts;
import com.b.r.loteriab.r.Model.Pos;
import com.b.r.loteriab.r.Model.Seller;
import com.b.r.loteriab.r.Model.Shift;
import com.b.r.loteriab.r.Model.ViewModel.SaleDetailViewModel;
import com.b.r.loteriab.r.Model.ViewModel.SaleViewModel;
import com.b.r.loteriab.r.Model.ViewModel.SalesReportViewModel;
import com.b.r.loteriab.r.Repository.PosRepository;
import com.b.r.loteriab.r.Repository.SaleRepository;
import com.b.r.loteriab.r.Repository.SellerRepository;
import com.b.r.loteriab.r.Repository.ShiftRepository;
import com.b.r.loteriab.r.Validation.Helper;
import com.b.r.loteriab.r.Validation.Result;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Dany on 04/05/2019.
 */
@Service
@Transactional
public class ReportService {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private SellerRepository sellerRepository;


    public List<Object> getSalesReport(SalesReportViewModel salesReportViewModel){
        if (salesReportViewModel.getSellerId()!= null){
           return proceedToFindSalesReport(salesReportViewModel, true);
        } else {
            if (salesReportViewModel.getGroupId()!= null){
                List<Object> salesReportViewModels = new ArrayList<>();
                List<Seller> sellers = sellerRepository.selectAllSellersInGroupsByEnterpriseId(salesReportViewModel.getGroupId(), true, salesReportViewModel.getEnterpriseId());
                for(Seller seller: sellers){
                    salesReportViewModel.setSellerId(seller.getId());
                    salesReportViewModels.addAll(proceedToFindSalesReport(salesReportViewModel, true));
                }
                return salesReportViewModels;
            }
            return  proceedToFindSalesReport(salesReportViewModel, false);
        }
    }

    private Pair<Date, Date> getStartAndEndDate(Shift shift, Date startDate, Date endDate, Long enterpriseId) {
        Shift other;
        Date start;
        Date end;
        if (shift.getName().equals(Shifts.Maten.name())){
            other = shiftRepository.findShiftByNameAndEnterpriseId(Shifts.New_York.name(), enterpriseId);
            start = Helper.getCloseDateTime(other.getCloseTime(), startDate);
            start = Helper.addDays(start, -1);
            if (endDate!=null){
                end = Helper.getCloseDateTime(shift.getCloseTime(), endDate);
            } else {
                end = Helper.getCloseDateTime(shift.getCloseTime(), startDate);
            }
        }else {
            other = shiftRepository.findShiftByNameAndEnterpriseId(Shifts.Maten.name(), enterpriseId);
            start = Helper.getCloseDateTime(other.getCloseTime(), startDate);
            if (endDate!=null){
                end = Helper.getCloseDateTime(shift.getCloseTime(), endDate);
            } else {
                end = Helper.getCloseDateTime(shift.getCloseTime(), startDate);
            }
        }

        return Pair.with(start, end);
    }

    private List<Object> proceedToFindSalesReport(SalesReportViewModel salesReportViewModel, boolean haveSeller) {
        if (salesReportViewModel.getShiftId()!= null){
            Shift shift = shiftRepository.findShiftByIdAndEnterpriseId(salesReportViewModel.getShiftId(), salesReportViewModel.getEnterpriseId());
            if (salesReportViewModel.getStartDate()!= null && salesReportViewModel.getEndDate() !=null){
                if (shift!=null){
                    Pair<Date, Date> startAndEnd = getStartAndEndDate(shift, Helper.convertStringToDate(salesReportViewModel.getStartDate(), "yyyy-MM-dd"),Helper.convertStringToDate(salesReportViewModel.getEndDate(), "yyyy-MM-dd") , salesReportViewModel.getEnterpriseId());
                    if (haveSeller){
                        return saleRepository.selectSaleReportGloballyOverAPeriodBySeller(
                                salesReportViewModel.getEnterpriseId(),
                                salesReportViewModel.getShiftId(),
                                salesReportViewModel.getSellerId(),
//                                startAndEnd.getValue0(),
                                Helper.convertDateToString(startAndEnd.getValue0(), "dd/MM/yyyy, HH:mm:ss"),
                                Helper.convertDateToString(startAndEnd.getValue1(), "dd/MM/yyyy, HH:mm:ss")
//                                startAndEnd.getValue1()
                        );
                    }else {
                        return saleRepository.selectSaleReportGloballyOverAPeriod(
                                salesReportViewModel.getEnterpriseId(),
                                salesReportViewModel.getShiftId(),
                                startAndEnd.getValue0(),
                                startAndEnd.getValue1()
                        );
                    }
                }
            } else {
                if (salesReportViewModel.getStartDate()!= null){
                    if (shift!=null){
                        Pair<Date, Date> startAndEnd = getStartAndEndDate(shift, Helper.convertStringToDate(salesReportViewModel.getStartDate(), "yyyy-MM-dd") , null, salesReportViewModel.getEnterpriseId());
                        if (haveSeller){
                            return saleRepository.selectSaleReportGloballyOverAPeriodBySeller(
                                    salesReportViewModel.getEnterpriseId(),
                                    salesReportViewModel.getShiftId(),
                                    salesReportViewModel.getSellerId(),
                                    Helper.convertDateToString(startAndEnd.getValue0(), "dd/MM/yyyy, HH:mm:ss"),
                                    Helper.convertDateToString(startAndEnd.getValue1(), "dd/MM/yyyy, HH:mm:ss")
//                                    startAndEnd.getValue0(),
//                                    startAndEnd.getValue1()
                            );
                        }else {
                            return saleRepository.selectSaleReportGloballyOverAPeriod(
                                    salesReportViewModel.getEnterpriseId(),
                                    salesReportViewModel.getShiftId(),
                                    startAndEnd.getValue0(),
                                    startAndEnd.getValue1()
                            );
                        }
                    }
                }
            }
        }
        return new ArrayList<>();

    }



}
