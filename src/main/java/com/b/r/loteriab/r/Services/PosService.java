package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Filter.Dto.PosDto;
import com.b.r.loteriab.r.Model.Filter.Dto.PosListRequest;
import com.b.r.loteriab.r.Model.Filter.Mapper.PosMapper;
import com.b.r.loteriab.r.Model.Filter.Specification.PosListSpecification;
import com.b.r.loteriab.r.Model.Pos;
import com.b.r.loteriab.r.Repository.PosRepository;
import com.b.r.loteriab.r.Services.errors.ErrorResponse;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final PosListSpecification posListSpecification;

    public PosService(PosListSpecification posListSpecification) {
        this.posListSpecification = posListSpecification;
    }

    private Result validateModel (Pos pos){
        Result result = new Result();

        if (pos.getDescription().isEmpty()){
            result.add("Deskripsyon an pa ka vid", "macAddress");
        }

        if (pos.getSerial().isEmpty()){
            result.add("Nimewo seri la pa ka vid", "serial");
        }

        Pos  savedPos = posRepository.findPosByDescriptionAndEnterpriseId(pos.getDescription(), pos.getEnterprise().getId());
        if (savedPos!= null){
            if (pos.getId() == null || pos.getId() <= 0){
                result.add("Deskripsyon sa egziste deja pou yon lòt machin", "description");
            } else {
                if (!pos.getId().equals(savedPos.getId())){
                    result.add("Deskripsyon sa egziste deja pou yon lòt machin", "description");
                }
            }
        }

        savedPos = posRepository.findPosBySerialAndEnterpriseId(pos.getSerial(), pos.getEnterprise().getId());
        if (savedPos!= null){
            if (pos.getId() == null || pos.getId() <= 0){
                result.add("Nimewo seri sa egziste deja pou yon lòt machin", "serial");
            } else {
                if (!pos.getId().equals(savedPos.getId())){
                    result.add("Nimewo seri sa egziste deja pou yon lòt machin", "serial");
                }
            }
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
            result.add("Machin sa pa ka anrejistre, reeseye ankò");
        }
        return  result;
    }

    public Result updatePos(Pos pos, Enterprise enterprise) {
        pos.setEnterprise(enterprise);
        Result result = validateModel(pos);
        if (!result.isValid()){
            return result;
        }

        Pos currentPos = posRepository.findPosByIdAndEnterpriseId(pos.getId(), enterprise.getId());
        currentPos.setDescription(pos.getDescription());
        currentPos.setModificationDate(new Date());
        currentPos.setSerial(pos.getSerial());
        try {
            posRepository.save(currentPos);
        }catch (Exception ex){
            result.add("machin sa pa ka aktyalize, reeseye ankò");
        }
        return  result;
    }

    public ArrayList<Pos> findAllPos(Long enterpriseId){
        return (ArrayList<Pos>)posRepository.findAllByEnterpriseIdOrderByIdDesc(enterpriseId);
    }

    public Page<Pos> findAllPosByState(int page, int itemPerPage, Boolean state, Long enterpriseId){
        Pageable pageable = PageRequest.of(page - 1 ,itemPerPage);
        if(state != null){
            return posRepository.findAllByEnabledAndEnterpriseIdOrderByIdDesc(pageable,state, enterpriseId);
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
            return posRepository.findAllByEnabledAndEnterpriseIdOrderByIdDesc(enabled, enterpriseId);
        }
        return posRepository.findAllByEnterpriseIdOrderByIdDesc(enterpriseId);
    }

    public List<Pos> findPosBySellerId(Long sellerId, Long enterpriseId, int updating){
        List<Pos> pos = posRepository.selectPosRelatedToSeller(sellerId, enterpriseId, true);

        if (updating <= 0){
            if (pos == null){
                pos = posRepository.selectAllPosFreeFromBankAndByEnterpriseId(true, enterpriseId);
            }
        } else {
            List<Pos> freePos = posRepository.selectAllPosFreeFromBankAndByEnterpriseId(true, enterpriseId);
            pos.addAll(freePos);
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
            result.add("Machin sa ou bezwen elimine a, pa egziste");
            return result;
        }
        try{
            posRepository.deleteByIdAndEnterpriseId(id, enterpriseId);
        }catch (Exception ex){
            result.add("Machin lan pa ka elimine, reeseye ankò");
        }
        return result;
    }

    public Page<Pos> findAll(PosListRequest request, int page, int count) {
        Pageable pageable = PageRequest.of(page - 1 , count, sort(request));


//        Pageable sortedByName =
//                PageRequest.of(0, 3, Sort.by("name"));
//
//        Pageable sortedByPriceDesc =
//                PageRequest.of(0, 3, Sort.by("price").descending());
//
//        Pageable sortedByPriceDescNameAsc =
//                PageRequest.of(0, 5, Sort.by("price").descending().and(Sort.by("name")));




        return posRepository.findAll(posListSpecification.getFilter(request), pageable);
    }
    public boolean isAsc (String isAsc){
        return isAsc.equals("asc");
    }
    public Sort sort (PosListRequest request){
        Sort sort = Sort.by("id");
        if (!request.getSortDescription().isEmpty()){
            sort.and(Sort.by("serial"));
            if (isAsc(request.getSerial())){
                sort.ascending();
            }else {
                sort.descending();
            }
        }

//        TODO: add what left
        return sort;
    }

}
