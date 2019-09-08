package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.CombinationType;
import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Enums.CombinationTypes;
import com.b.r.loteriab.r.Model.ViewModel.Helper;
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

    @Autowired
    private Helper helper;

    private Result validateModel (CombinationType combinationType){
        Result result = new Result();

        if (combinationType.getProducts().getName().equals(CombinationTypes.BOLET.name())){
            if (combinationType.getPayedPriceFirstDraw() <= 0){
                result.add("Ou dwe antre yon pri pou premye lo a", "payedPriceFirstDraw");
                return result;
            }
            if (combinationType.getPayedPriceSecondDraw() <= 0){
                result.add("Ou dwe antre yon pri pou dezyèm lo a", "payedPriceSecondDraw");
                return result;
            }
            if (combinationType.getPayedPriceThirdDraw() <= 0){
                result.add("Ou dwe antre yon pri pou twazyèm lo a", "payedPriceThirdDraw");
                return result;
            }
        } else {
            if (combinationType.getPayedPrice() <= 0){
                result.add("Ou dwe antre yon pri pou " + helper.replace(combinationType.getProducts().getName(), "_", ""). toLowerCase(), "payedPriceThirdDraw");
                return result;
            }
        }
        return result;
    }

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
            result.add("Tip konbinezon an pa ka aktyalize reeseye ankò");
        }
        return  result;
    }


    public Result updateCombinationType(CombinationType combinationType, Enterprise enterprise) {
        CombinationType currentCombinationType = combinationTypeRepository.findCombinationTypeByIdAndEnterpriseId(combinationType.getId(), enterprise.getId());
        combinationType.setProducts(currentCombinationType.getProducts());
        combinationType = cleanModel(combinationType);
        Result result = validateModel(combinationType);
            if (!result.isValid()){
                return result;
            }

        if (combinationType.getProducts().getName().equals(CombinationTypes.BOLET.name())){
            currentCombinationType.setPayedPriceFirstDraw(combinationType.getPayedPriceFirstDraw());
            currentCombinationType.setPayedPriceSecondDraw(combinationType.getPayedPriceSecondDraw());
            currentCombinationType.setPayedPriceThirdDraw(combinationType.getPayedPriceThirdDraw());
        }else {
            currentCombinationType.setPayedPrice(combinationType.getPayedPrice());
        }
        currentCombinationType.setNote(combinationType.getNote());
        currentCombinationType.setModificationDate(new Date());

        try {
            combinationTypeRepository.save(currentCombinationType);
        }catch (Exception ex){
            result.add("Tip konbinezon an pa ka aktyalize, reeseye ankò");
        }
        return  result;
    }

    public Page<CombinationType> findAllCombinationTypeByEnterpriseId(int page, int itemPerPage, Boolean state, Long enterpriseId){
        Pageable pageable = PageRequest.of(page,itemPerPage);
        if (state!= null){
            return combinationTypeRepository.findAllByEnabledAndEnterpriseIdOrderByIdDesc(pageable, state, enterpriseId);
        }
        return combinationTypeRepository.findAllByEnterpriseIdOrderByIdDesc(pageable, enterpriseId);
    }

    public CombinationType findCombinationTypeByIdAndEntepriseId(Long id,Long enterpriseId){
        return combinationTypeRepository.findCombinationTypeByIdAndEnterpriseId(id, enterpriseId);
    }

    public List<CombinationType> findallByEnterpriseId (Long enterpriseId){
        return combinationTypeRepository.findAllByEnterpriseIdOrderByIdDesc(enterpriseId);
    }

     public List<CombinationType> findAllByEnterpriseName (String enterpriseName){
        return combinationTypeRepository.findAllByEnterpriseName(enterpriseName);
     }

    public List<CombinationType> findAllByEnabledAndEnterpriseId(Boolean enabled, Long enterpriseId){
        if (enabled!= null){
            return combinationTypeRepository.findAllByEnabledAndEnterpriseIdOrderByIdDesc(enabled, enterpriseId);
        }
        return combinationTypeRepository.findAllByEnterpriseIdOrderByIdDesc(enterpriseId);
    }

    public void initCombinationTypeForEnterprise (String enterpriseName, String bolet, String lotoTwaChif, String lotoKatChif, String opsyon, String maryaj, String extra){
        initServices.createCombinationType(enterpriseService.findEnterpriseByName(enterpriseName), bolet, lotoTwaChif, lotoKatChif, opsyon, maryaj, extra);
    }

}
