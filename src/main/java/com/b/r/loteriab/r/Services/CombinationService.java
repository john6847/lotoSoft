package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Combination;
import com.b.r.loteriab.r.Model.CombinationType;
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

    public List<CombinationViewModel> findAllCombinations(String combination, int limit){
        // borlet
        if (combination.matches("(^[0-9]{2}$)")){
            return findAllCombinationTwoDigits(combination, limit, CombinationTypes.BOLET.name());
        }

        if (combination.matches("(^[0-9]{3}$)")){
            return findAllCombinationThreeDigits(combination, limit, CombinationTypes.LOTO_TWA_CHIF.name());
        }

        if (combination.matches("(^[0-9]{4}$)")){

            return findAllCombinationTwoDigitsAndTwoDigits(combination.substring(0, 2), combination.substring(2,4), 8, CombinationTypes.OPSYON.name(), CombinationTypes.LOTO_KAT_CHIF.name());
        }

        if (combination.matches("(^[0-9]{5}$)")){
            return findAllCombinationThreeDigitsAndTwoDigits( combination.substring(3,5),combination.substring(0, 3), limit, CombinationTypes.EXTRA.name());
        }

        if (combination.matches("^([0-9]{2})(\\s)([0-9]{2})$")){
            String [] arr = combination.split(" ");
            if(arr[0].length()== 1){
                arr[0]= "0"+arr[0];
            }
            if(arr[1].length()== 1){
                arr[1]= "0"+arr[1];
            }
            return findAllCombinationTwoDigitsAndTwoDigits(arr[0], arr[1], 8, CombinationTypes.OPSYON.name(), CombinationTypes.LOTO_KAT_CHIF.name());
        }

//        extra
        if (combination.matches("^([0-9]{3})(\\s)([0-9]{2})$")){
            String [] arr = combination.split(" ");
            return findAllCombinationThreeDigitsAndTwoDigits(arr[0], arr[1], limit, CombinationTypes.EXTRA.name());
        }

//        extra
        if (combination.matches("^([0-9]{2})(\\s)([0-9]{3})$")){
            String [] arr = combination.split(" ");
            return findAllCombinationThreeDigitsAndTwoDigits(arr[1], arr[0], limit, CombinationTypes.EXTRA.name());
        }

//        Maryaj
        if (combination.matches("^([0-9]{2})([x]|[X])([0-9]{2})$") ||
                combination.matches("^([0-9]{2})\\s([x]|[X])\\s([0-9]{2})$") ||
                combination.matches("^([0-9]{2})([x]|[X])\\s([0-9]{2})$")||
                combination.matches("^([0-9]{2})\\s([x]|[X])([0-9]{2})$")
        ){
            if (combination.contains("x")){
                String [] arr = combination.split("x");
                if(arr[0].length()== 1)
                    arr[0]= "0"+arr[0];

                if(arr[1].length()== 1)
                    arr[1]= "0"+arr[1];
                return findAllCombinationTwoDigitsAndTwoDigits(arr[0], arr[1], limit,CombinationTypes.MARYAJ.name(), "");
            }
            if (combination.contains("X")){
                String [] arr = combination.split("X");
                if(arr[0].length()== 1){
                    arr[0]= "0"+arr[0];
                }
                if(arr[1].length()== 1){
                    arr[1]= "0"+arr[1];
                }
                return findAllCombinationTwoDigitsAndTwoDigits(arr[0], arr[1], limit, CombinationTypes.MARYAJ.name(), "");
            }

        }

        return new ArrayList<>();
    }

    private List<CombinationViewModel> findAllCombinationTwoDigits(String numberTwoDigits, int limit, String type){

        return combinationRepository.selectAllByNumberTwoDigits(numberTwoDigits, limit, type, CombinationViewModel.class);
    }

    private List<CombinationViewModel> findAllCombinationThreeDigits(String numberThreeDigits, int limit, String type){

        return combinationRepository.selectAllByNumberThreeDigits(numberThreeDigits, limit, type, CombinationViewModel.class);
    }

    private List<CombinationViewModel> findAllCombinationThreeDigitsAndTwoDigits(String numberTwoDigits, String numberThreeDigits, int limit, String type){

        return combinationRepository.selectAllByNumberTwoDigitsAndNumberThreeDigits(numberTwoDigits, numberThreeDigits, limit, type,CombinationViewModel.class);
    }

    private List<CombinationViewModel> findAllCombinationTwoDigitsAndTwoDigits(String numberTwoDigits1, String numberTwoDigits2, int limit, String type1,String type2){
        if (numberTwoDigits1.equals(numberTwoDigits2)){
            if (type2.isEmpty()){
                return combinationRepository.selectAllBySameNumberTwoDigitsAndNumberTwoDigitsAndTypeMARYAJ(numberTwoDigits1, type1, CombinationViewModel.class);
            }
            return combinationRepository.selectAllBySameNumberTwoDigitsAndNumberTwoDigitsAndTypeLOTOANDOPSYON(numberTwoDigits1, type1, type2, CombinationViewModel.class);
        } else {
            if (type2.isEmpty()){
                return combinationRepository.selectAllByNumberTwoDigitsAndNumberTwoDigits(numberTwoDigits1, numberTwoDigits2, limit, type1, CombinationViewModel.class);
            }
            return combinationRepository.selectAllByNumberTwoDigitsAndNumberTwoDigitsAndType(numberTwoDigits1, numberTwoDigits2, limit, type1, type2, CombinationViewModel.class);
        }
    }

    public Combination findCombinationById(Long id){
        return combinationRepository.findCombinationById(id);
    }

    public int updateCombinationGroup (Long combinationTypeId, boolean enabled, double maxPrice){
        CombinationType combinationType = combinationTypeRepository.findCombinationTypeById(combinationTypeId);
        if (maxPrice <= 0){
            return combinationRepository.updateCombinationState(combinationType, enabled);
        }
        return combinationRepository.updateCombinationMaxPrice(combinationType, enabled, maxPrice);
    }

}
