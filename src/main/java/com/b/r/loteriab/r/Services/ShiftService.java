package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Shift;
import com.b.r.loteriab.r.Repository.ShiftRepository;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Created by Dany on 04/05/2019.
 */
@Service
@Transactional
public class ShiftService {
    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private InitServices initServices;

    public Result updateShift(Shift shift, Long enterpriseId) {
        Result result = new Result();
        if (!result.isValid()) {
            return result;
        }

        Shift currentShift = shiftRepository.findShiftByIdAndEnterpriseId(shift.getId(), enterpriseId);
        currentShift.setName(shift.getName());
        currentShift.setOpenTime(shift.getOpenTime());
        currentShift.setCloseTime(shift.getCloseTime());
        currentShift.setEnabled(true);

        try {
            shiftRepository.save(currentShift);
        } catch (Exception ex) {
            result.add("Tip tiraj la pa ka aktyalize reeseye ankò");
        }
        return result;
    }

    public Shift findShiftById(Long id, Long enterpriseId) {
        return shiftRepository.findShiftByIdAndEnterpriseId(id, enterpriseId);
    }

    public ArrayList<Shift> findAllByEnterpriseId(Long enterpriseId) {
        return (ArrayList<Shift>) shiftRepository.findAllByEnterpriseIdOrderByIdDesc(enterpriseId);
    }

    public void createShiftForEnterprise(String enterpriseName) {
        initServices.createShift(enterpriseService.findEnterpriseByName(enterpriseName));
    }

}
