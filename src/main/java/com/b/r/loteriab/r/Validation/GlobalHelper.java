package com.b.r.loteriab.r.Validation;

import com.b.r.loteriab.r.Model.Draw;
import com.b.r.loteriab.r.Model.Enums.Shifts;
import com.b.r.loteriab.r.Model.Shift;
import com.b.r.loteriab.r.Repository.DrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class GlobalHelper {

    @Autowired
    private DrawRepository drawRepository;

    public Draw getLastDraw(Long enterpriseId, Shift activeShift, Shift inactiveShift) {
        if (activeShift != null && inactiveShift != null){
            if (activeShift.getName().equals(Shifts.Maten.name())){
                Date date = Helper.addDays(new Date(), -1);
                return drawRepository.findByDrawDateAndEnterpriseIdAndShiftId(Helper.setTimeToDate(date, "00:00:00".split(":")),enterpriseId, inactiveShift.getId());
            }
            else {
                return drawRepository.findByDrawDateAndEnterpriseIdAndShiftId(Helper.setTimeToDate(new Date(), "00:00:00".split(":")),enterpriseId, inactiveShift.getId());
            }
        }
        return  null;
    }
}
