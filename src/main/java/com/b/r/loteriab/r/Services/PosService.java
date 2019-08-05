package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Pos;
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
public class PosService {
    @Autowired
    private PosRepository posRepository;

    @Autowired
    private  EnterpriseService enterpriseService;

    private Result validateModel (Pos pos){
        Result result = new Result();

        if (pos.getDescription().isEmpty()){
            result.add("Deskripsyon an pa ka vid", "macAddress");
        }

        if (pos.getMacAddress().isEmpty()){
            result.add("MacAddress la pa ka vid", "macAddress");
        }

        if (pos.getSerial().isEmpty()){
            result.add("Serial la pa ka vid", "serial");
        }

        if (posRepository.findByMacAddressAndEnterpriseId(pos.getMacAddress(), pos.getEnterprise().getId())!= null){
            result.add("Mac Address la egziste deja", "macAddress");
        }

        if (posRepository.findPosBySerialAndEnterpriseId(pos.getSerial(), pos.getEnterprise().getId())!= null){
            result.add("Serial la egziste deja pou yon lot machin", "serial");
        }

        return result;
    }

    public Result savePos(Pos pos, Enterprise enterprise){
        pos.setEnterprise(enterpriseService.findEnterpriseByName(enterprise.getName()));
        Result result = validateModel(pos);
        if (!result.isValid()){
            return result;
        }
        try {
            pos.setCreationDate(new Date());
            pos.setModificationDate(new Date());
            pos.setEnabled(true);
            posRepository.save(pos);
        }catch (Exception ex){
            result.add("Pos la pa ka aktyalize reeseye ankò");
        }
        return  result;
    }

    public Result updatePos(Pos pos, Enterprise enterprise) {
        Result result = validateModel(pos);
        if (!result.isValid()){
            return result;
        }

        Pos currentPos = posRepository.findPosByIdAndEnterpriseId(pos.getId(), enterprise.getId());
        currentPos.setDescription(pos.getDescription());
        currentPos.setModificationDate(new Date());
        currentPos.setMacAddress(pos.getMacAddress());
        currentPos.setSerial(pos.getSerial());
        try {
            posRepository.save(currentPos);
        }catch (Exception ex){
            result.add("Pos la pa ka aktyalize reeseye ankò");
        }
        return  result;
    }

    public void deletePosId(Long id, Long enterpriseId){
        posRepository.deleteByIdAndEnterpriseId(id, enterpriseId);
    }

    public ArrayList<Pos> findAllPos(Long enterpriseId){
        return (ArrayList<Pos>)posRepository.findAllByEnterpriseId(enterpriseId);
    }

    public Page<Pos> findAllPosByState(int page, int itemPerPage, Boolean state, Long enterpriseId){
        Pageable pageable = PageRequest.of(page,itemPerPage);
        if(state != null){
            return posRepository.findAllByEnabledAndEnterpriseId(pageable,state, enterpriseId);
        }
        return posRepository.findAllByEnterpriseId(pageable, enterpriseId);
    }

    public Pos findPosById(Long id, Long enterpriseId){
        return posRepository.findPosByIdAndEnterpriseId(id, enterpriseId);
    }

    public Pos findPosByIdAndEnabled(Long id, boolean enabled, Long enterpriseId){
        return posRepository.findPosByIdAndEnabledAndEnterpriseId(id, enabled, enterpriseId);
    }

    public List<Pos> findPosByEnabled(Boolean enabled, Long enterpriseId){
        if (enabled!= null){
            return posRepository.findAllByEnabledAndEnterpriseId(enabled, enterpriseId);
        }
        return posRepository.findAllByEnterpriseId(enterpriseId);
    }

    public List<Pos> findPosBySellerId(Long sellerId, Long enterpriseId){
        List<Pos> pos = posRepository.selectPosRelatedToSeller(sellerId, enterpriseId, true);

        if (pos == null){
            pos = posRepository.selectAllPosFreeFromBankAndByEnterpriseId(true, enterpriseId);
        }
        return pos;
    }

    public List<Pos> findAllFreePosByEnabled(boolean enabled, Long enterpriseId){
        return posRepository.selectAllFreeAndEnabledPosByEnterpriseId(enabled, enterpriseId);
    }
    public List<Pos> findAllFreePosFromBankByEnabled(boolean enabled, Long enterpriseId){
        return posRepository.selectAllFreeAndEnabledPosForBankByEnterpriseId(enabled, enterpriseId);
    }

    public Result deletePosById(Long id, Long enterpriseId){
        Result result = new Result();
        Pos pos = posRepository.findPosByIdAndEnterpriseId(id, enterpriseId);
        if(pos == null) {
            result.add("Machin sa ou bezwen elimine a pa egziste");
            return result;
        }
        try{
            posRepository.deleteByIdAndEnterpriseId(id, enterpriseId);
        }catch (Exception ex){
            result.add("Machin la pa ka elimine reeseye ankò");
        }
        return result;
    }
}
