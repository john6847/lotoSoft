package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Pos;
import com.b.r.loteriab.r.Model.ViewModel.SalesReportViewModel;
import com.b.r.loteriab.r.Repository.PosRepository;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Service
@Transactional
public class ReportService {
    @Autowired
    private PosRepository posRepository;

    @Autowired
    private  EnterpriseService enterpriseService;

//    public Result getSalesReport(SalesReportViewModel salesReportViewModel){
//        if (salesReportViewModel.getStartDate() != null){
//
//        }
//        return result;
//    }
}
