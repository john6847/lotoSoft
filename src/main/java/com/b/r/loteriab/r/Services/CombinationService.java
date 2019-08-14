package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Combination;
import com.b.r.loteriab.r.Model.CombinationType;
import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Enums.CombinationTypes;
import com.b.r.loteriab.r.Model.Interaces.CombinationViewModel;
import com.b.r.loteriab.r.Repository.CombinationRepository;
import com.b.r.loteriab.r.Repository.CombinationTypeRepository;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Service
@Transactional
public class CombinationService {
    @Autowired
    private CombinationRepository combinationRepository;

    @Autowired
    private CombinationTypeRepository combinationTypeRepository;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private InitServices initServices;

    @Autowired
    private CombinationTypeService combinationTypeService;

    public Result updateCombination (Combination combination){
        Result result = validateModel(combination);
        if (!result.isValid()){
            return result;
        }

        try {
            combinationRepository.save(combination);
        }catch (Exception ex){
            result.add("Konbinezon la pa ka aktyalize reeseye ankò");
        }
        return  result;
    }

    private Result validateModel (Combination combination){
        Result result = new Result();

        return result;
    }


    public void deleteCombinationId(Long id){
        combinationRepository.deleteById(id);
    }

    public List<Combination> findAllCombinations(String combination, Enterprise enterprise){
        CombinationType combinationType = null;
        CombinationType secondCombinationType = null;
        // borlet
        if (combination.matches("(^[0-9]{2}$)")){
            combinationType = combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.BOLET.name(), enterprise.getId());
            if (combinationType !=null)
                return findAllSimpleCombination(combination, combinationType.getId(), enterprise.getId());
            return  new ArrayList<>();
        }

//        loto 3
        if (combination.matches("(^[0-9]{3}$)")){
            combinationType = combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.LOTO_TWA_CHIF.name(), enterprise.getId());
            if (combinationType !=null)
                return findAllSimpleCombination(combination, combinationType.getId(), enterprise.getId());
            return  new ArrayList<>();
        }

//        loto 4 and opsyon
        if (combination.matches("(^[0-9]{4}$)")){
            combinationType = combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.LOTO_KAT_CHIF.name(), enterprise.getId());
            secondCombinationType = combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.OPSYON.name(), enterprise.getId());
            if (combinationType!= null && secondCombinationType != null){
                String first = combination.substring(0, 2);
                String second = combination.substring(2, 4);
                if (first.equals(second)){
                    return findAllCombinationForLoto4AndOpsyon(first+" "+second, "", combinationType.getId(),
                            secondCombinationType.getId(), enterprise.getId());
                }
                return findAllCombinationForLoto4AndOpsyon(first+" "+second, second + " "+ first, combinationType.getId(),
                        secondCombinationType.getId(), enterprise.getId());
            }
            return  new ArrayList<>();
        }

//        extra
        if (combination.matches("(^[0-9]{5}$)")){
            combinationType = combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.EXTRA.name(), enterprise.getId());
            if (combinationType != null)
                return findAllSimpleCombination(combination.substring(0, 3) +" "+combination.substring(3,5), combinationType.getId(), enterprise.getId());
            return new ArrayList<>();
        }
//      loto 4 and opsyon
        if (combination.matches("^([0-9]{2})(\\s)([0-9]{2})$")){
            String [] arr = combination.split(" ");
            combinationType = combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.LOTO_KAT_CHIF.name(), enterprise.getId());
            secondCombinationType = combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.OPSYON.name(), enterprise.getId());

            if (combinationType!= null && secondCombinationType != null){
                if (arr[0].equals(arr[1])){
                    return findAllCombinationForLoto4AndOpsyon(combination, "", combinationType.getId(), secondCombinationType.getId(), enterprise.getId());
                }
                return findAllCombinationForLoto4AndOpsyon(combination, arr[1]+" "+arr[0], combinationType.getId(), secondCombinationType.getId(), enterprise.getId());
            }
            return new ArrayList<>();
        }

//        extra
        if (combination.matches("^([0-9]{3})(\\s)([0-9]{2})$")){
            combinationType = combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.EXTRA.name(), enterprise.getId());
            if (combinationType != null)
                return findAllSimpleCombination(combination, combinationType.getId(), enterprise.getId());
            return new ArrayList<>();
        }

//        Maryaj
        if (combination.matches("^([0-9]{2})([x]|[X])([0-9]{2})$") ||
                combination.matches("^([0-9]{2})\\s([x]|[X])\\s([0-9]{2})$") ||
                combination.matches("^([0-9]{2})([x]|[X])\\s([0-9]{2})$")||
                combination.matches("^([0-9]{2})\\s([x]|[X])([0-9]{2})$")
        ){
            combinationType = combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.MARYAJ.name(), enterprise.getId());

            if (combinationType != null){
                String [] arr = null;
                if (combination.contains("x"))
                    arr = combination.split("x");
                else if (combination.contains("X"))
                    arr = combination.split("X");
                if (arr != null) {
                    String first = arr[0].trim();
                    String second = arr[1].trim();
                    if (first.equals(second))
                        return findAllCombinationForMaryaj(first + "x"+ second,"",  combinationType.getId(), enterprise.getId());
                    return findAllCombinationForMaryaj(first + "x"+ second, second+"x"+first, combinationType.getId(), enterprise.getId());

                }

            }
        }

        return new ArrayList<>();
    }

    private List<Combination> findAllSimpleCombination(String resultCombination, Long combinationTypeId, Long enterpriseId){

        return combinationRepository.findAllByResultCombinationAndCombinationTypeIdAndEnterpriseId(resultCombination, combinationTypeId, enterpriseId, Combination.class);
    }


//    Maryaj

    private List<Combination> findAllCombinationForMaryaj(String resultCombination, String reverseResultCombination, Long combinationTypeId, Long enterpriseId){
        if(reverseResultCombination.isEmpty()){
            return findAllSimpleCombination(resultCombination, combinationTypeId, enterpriseId);
        }
        return combinationRepository
                .findAllByResultCombinationOrResultCombinationAndCombinationTypeIdAndEnterpriseId(resultCombination, reverseResultCombination, combinationTypeId, enterpriseId, Combination.class);
    }

//    Loto 4 and Opsyon

    private List<Combination> findAllCombinationForLoto4AndOpsyon(String resultCombination, String reverseResultCombination, Long combinationTypeId1,Long combinationTypeId2, Long enterpriseId){
        if(reverseResultCombination.isEmpty()){
            return combinationRepository
                    .findAllByResultCombinationAndCombinationTypeIdOrCombinationTypeIdAndEnterpriseId(resultCombination, combinationTypeId1, combinationTypeId2, enterpriseId, Combination.class);
        }
        return combinationRepository
                .findAllByResultCombinationOrResultCombinationAndCombinationTypeIdOrCombinationTypeIdAndEnterpriseId(resultCombination, reverseResultCombination, combinationTypeId1, combinationTypeId2, enterpriseId, Combination.class);
    }


    public Combination findCombinationById(Long id){
        return combinationRepository.findCombinationById(id);
    }

    public int updateCombinationGroup (Long combinationTypeId, boolean enabled, double maxPrice, Enterprise enterprise){
        CombinationType combinationType = combinationTypeRepository.findCombinationTypeByIdAndEnterpriseId(combinationTypeId, enterprise.getId()); //TODO: Get with enterprise
        if (maxPrice <= 0){
            return combinationRepository.updateCombinationState(combinationType, enabled, enterprise);
        }
        return combinationRepository.updateCombinationMaxPrice(combinationType, enabled, maxPrice, enterprise);
    }

    public void initCombinationForEnterprise(String enterpriseName){
        initServices.createCombinations(enterpriseService.findEnterpriseByName(enterpriseName), combinationTypeService.findAllByEnterpriseName(enterpriseName));
    }

}
