package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.CombinationType;
import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Enums.CombinationTypes;
import com.b.r.loteriab.r.Model.Products;
import com.b.r.loteriab.r.Repository.CombinationTypeRepository;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Service
@Transactional
public class CombinationTypeService {
    @Autowired
    private CombinationTypeRepository combinationTypeRepository;

    @Autowired
    private InitServices initServices;

    @Autowired
    private EnterpriseService enterpriseService;

    private CombinationType cleanModel (CombinationType combinationType) {
        if (combinationType.getProducts().getName().equals(CombinationTypes.BOLET.name())){
            combinationType.setPayedPrice(0);
        }else {
            combinationType.setPayedPriceFirstDraw(0);
            combinationType.setPayedPriceSecondDraw(0);
            combinationType.setPayedPriceThirdDraw(0);
        }
        return  combinationType;
    }
    public Result saveCombinationType (CombinationType combinationType, Enterprise enterprise){
        combinationType = cleanModel(combinationType);
        Result result = validateModel(combinationType);
        if (!result.isValid()){
            return result;
        }

        try {
            combinationType.setEnterprise(enterprise);
            combinationType.setEnabled(true);
            combinationType.setCreationDate(new Date());
            combinationType.setModificationDate(new Date());
            combinationTypeRepository.save(combinationType);
        }catch (Exception ex){
            result.add("Konbinezon an pa ka aktyalize reeseye ankò");
        }
        return  result;
    }

    private Result validateModel (CombinationType combinationType){
        Result result = new Result();

        if (combinationType.getProducts() == null){
            result.add("Tip Konbinezon an pa ka ret vid", "Konbinezon");
            return result;
        }
        return result;
    }

    public Result updateCombinationType(CombinationType combinationType) {
        combinationType = cleanModel(combinationType);
        Result result = validateModel(combinationType);
            if (!result.isValid()){
                return result;
            }

        CombinationType currentCombinationType = combinationTypeRepository.findCombinationTypeByIdAndEnterpriseId(combinationType.getId(), combinationType.getEnterprise().getId());
        currentCombinationType.setProducts(combinationType.getProducts());
        if (combinationType.getProducts().getName().equals(CombinationTypes.BOLET.name())){
            currentCombinationType.setPayedPriceFirstDraw(combinationType.getPayedPriceFirstDraw());
            currentCombinationType.setPayedPriceSecondDraw(combinationType.getPayedPriceSecondDraw());
            currentCombinationType.setPayedPriceThirdDraw(combinationType.getPayedPriceThirdDraw());
        }else {
            currentCombinationType.setPayedPrice(combinationType.getPayedPrice());
        }
        currentCombinationType.setNote(combinationType.getNote());
        currentCombinationType.setModificationDate(new Date ());

        try {
            combinationTypeRepository.findCombinationTypeByIdAndEnterpriseId(combinationType.getId(),combinationType.getEnterprise().getId());
        }catch (Exception ex){
            result.add("Konbinezon an pa ka aktyalize reeseye ankò");
        }
        return  result;
    }


    public Result deleteCombinationTypeIdAndEnterpriseId(Long id, Long enterpriseId){
        Result result = new Result();
        CombinationType combinationType = combinationTypeRepository.findCombinationTypeByIdAndEnterpriseId(id, enterpriseId);
        if(combinationType == null) {
            result.add("Konbinezon sa ou bezwen elimine a pa egziste");
            return result;
        }
        try{
            combinationTypeRepository.deleteById(id);
        }catch (Exception ex){
            result.add("Konbinezon an pa ka elimine reeseye ankò");
        }
        return result;
    }

    public Page<CombinationType> findAllCombinationTypeByEnterpriseId(int page, int itemPerPage, Boolean state, Long enterpriseId){
        Pageable pageable = PageRequest.of(page,itemPerPage);
        if (state!= null){
            return combinationTypeRepository.findAllByEnabledAndEnterpriseId(pageable, state, enterpriseId);
        }
        return combinationTypeRepository.findAllByEnterpriseId(pageable, enterpriseId);
    }

    public CombinationType findCombinationTypeByIdAndEntepriseId(Long id,Long enterpriseId){
        return combinationTypeRepository.findCombinationTypeByIdAndEnterpriseId(id, enterpriseId);
    }

    public List<CombinationType> findallByEnterpriseId (Long enterpriseId){
        return combinationTypeRepository.findAllByEnterpriseId(enterpriseId);
    }

     public List<CombinationType> findAllByEnterpriseName (String enterpriseName){
        return combinationTypeRepository.findAllByEnterpriseName(enterpriseName);
     }

    public List<CombinationType> findAllByEnabledAndEnterpriseId(Boolean enabled, Long enterpriseId){
        if (enabled!= null){
            return combinationTypeRepository.findAllByEnabledAndEnterpriseId(enabled, enterpriseId);
        }
        return combinationTypeRepository.findAllByEnterpriseId(enterpriseId);
    }

    public void initCombinationTypeForEnterprise (String enterpriseName, String bolet, String lotoTwaChif, String lotoKatChif, String opsyon, String maryaj, String extra){
        initServices.createCombinationType(enterpriseService.findEnterpriseByName(enterpriseName), bolet, lotoTwaChif, lotoKatChif, opsyon, maryaj, extra);
    }

}
