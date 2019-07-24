package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.*;
import com.b.r.loteriab.r.Model.Enums.CombinationTypes;
import com.b.r.loteriab.r.Model.Enums.Roles;
import com.b.r.loteriab.r.Model.Enums.Shifts;
import com.b.r.loteriab.r.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Dany on 22/04/2019.
 */


@Service
public class InitServices {
    @Autowired
    private NumberTwoDigitsService numberTwoDigitsService;

    @Autowired
    private NumberThreeDigitsService numberThreeDigitsService;

    @Autowired
    private NumberThreeDigitsRepository numberThreeDigitsRepository;

    @Autowired
    private NumberTwoDigitsRepository numberTwoDigitsRepository;

    @Autowired
    private SecurityServices securityServices;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private CombinationRepository combinationRepository;

    @Autowired
    private  CombinationTypeRepository combinationTypeRepository;

    public  void init () {
        createNumberThreeDigits();
        createNumberTwoDigits();
        createProducts();
        createRoles();
        createShift();
        createCombinationType();
//        createCombinations();
        securityServices.crearUsuarioAdmin();
    }

    /**
     * Create CombinationType on Init
     * @return
     */
    private void createCombinationType() {
        List<Products> products = productRepository.findAll();
        if(products.size() > 0)
        {
            List<CombinationType> combinationTypes = combinationTypeRepository.findAll();
            if(combinationTypes.size() <= 0){
                for(Products product: products){
                    CombinationType combinationType = new CombinationType();
                    combinationType.setEnabled(true);
                    combinationType.setModificationDate(new Date());
                    combinationType.setCreationDate(new Date());
                    combinationType.setNote("");
                    combinationType.setProducts(product);
                    if(product.getName().equals(CombinationTypes.BOLET.name())) {
                        combinationType.setPayedPriceFirstDraw(50);
                        combinationType.setPayedPriceSecondDraw(20);
                        combinationType.setPayedPriceThirdDraw(10);
                        combinationType.setPayedPrice(0);

                    }else if(product.getName().equals(CombinationTypes.MARYAJ.name())){
                        combinationType.setPayedPrice(1000);
                        combinationType.setPayedPriceFirstDraw(0);
                        combinationType.setPayedPriceSecondDraw(0);
                        combinationType.setPayedPriceThirdDraw(0);
                    }else if (product.getName().equals(CombinationTypes.LOTO_TWA_CHIF.name())){
                        combinationType.setPayedPrice(500);
                        combinationType.setPayedPriceFirstDraw(0);
                        combinationType.setPayedPriceSecondDraw(0);
                        combinationType.setPayedPriceThirdDraw(0);
                    } else if (product.getName().equals(CombinationTypes.LOTO_KAT_CHIF.name()) || product.getName().equals(CombinationTypes.OPSYON.name())){
                        combinationType.setPayedPrice(5000);
                        combinationType.setPayedPriceFirstDraw(0);
                        combinationType.setPayedPriceSecondDraw(0);
                        combinationType.setPayedPriceThirdDraw(0);
                    }else if (product.getName().equals(CombinationTypes.EXTRA.name())){
                        combinationType.setPayedPrice(25000);
                        combinationType.setPayedPriceFirstDraw(0);
                        combinationType.setPayedPriceSecondDraw(0);
                        combinationType.setPayedPriceThirdDraw(0);
                    }
                    combinationTypeRepository.save(combinationType);
                }
            }
        }
    }

    /**
     * Create NumberThreeDigits on Init
     * @return
     */
    private  void createNumberThreeDigits(){
        List<NumberThreeDigits> listNumberThreeDigits = numberThreeDigitsRepository.findAll();
        if(listNumberThreeDigits.size()<=0){
            for (int i=0; i<1000;  i++){
                NumberThreeDigits numberThreeDigits = new NumberThreeDigits();
                numberThreeDigits.setNumberInIntegerFormat(i);
                if (i<10) {
                    numberThreeDigits.setNumberInStringFormat("00" + i);
                }
                if (i>= 10 && i<100) {
                    numberThreeDigits.setNumberInStringFormat("0" + i);
                }
                if (i>= 100) {
                    numberThreeDigits.setNumberInStringFormat(Integer.toString(i));
                }
                numberThreeDigitsService.saveNumberThreeDigits(numberThreeDigits);
            }
        }
    }

    /**
     * Create NumberTwoDigits on Init
     * @return
     */
    private  void createNumberTwoDigits(){
        List<NumberTwoDigits> listNumberTwoDigits = numberTwoDigitsRepository.findAll();
        if(listNumberTwoDigits.size()<=0){
            for (int i=0; i<100;  i++){
                NumberTwoDigits numberTwoDigits = new NumberTwoDigits();
                numberTwoDigits.setNumberInIntegerFormat(i);
                if (i<10) {
                    numberTwoDigits.setNumberInStringFormat("0" + i);
                }
                if (i>= 10) {
                    numberTwoDigits.setNumberInStringFormat(Integer.toString(i));
                }
                numberTwoDigitsService.saveNumberTwoDigits(numberTwoDigits);
            }
        }
    }

    /**
     * Create Products on Init
     * @return
     */
    private  void createProducts(){
        List<Products> products = productRepository.findAll();
        if(products.size()<=0){
            for (CombinationTypes combinationTypes: CombinationTypes.values()){
                Products product = new Products();
                product.setName(combinationTypes.name());
                productRepository.save(product);
            }
        }
    }

    /**
     * Create ROLES on Init
     * @return
     */
    private  void createRoles(){
        List<Role> roles = roleRepository.findAll();
        if(roles.size()<=0){
            for (Roles role: Roles.values()){
               Role newRole = new Role();
                newRole.setName(role.name());
                roleRepository.save(newRole);
            }
        }
    }

    /**
     * Create Shift on Init
     * @return
     */
    private  void createShift(){
        List<Shift> shifts = shiftRepository.findAll();
        if(shifts.size()<=0){
            for (Shifts shift: Shifts.values()){
               Shift newShift = new Shift();
                newShift.setName(shift.name());
                shiftRepository.save(newShift);
            }
        }
    }

    /**
     * Create Combination on Init
     * @return
     */
    private  void createCombinations(){
        for (CombinationTypes combinationTypes : CombinationTypes.values()){
            if (combinationTypes.name().equals(CombinationTypes.BOLET.name())){
                saveBolet();
            } if (combinationTypes.name().equals(CombinationTypes.LOTO_TWA_CHIF.name())){
                saveLoto3();
            } if (combinationTypes.name().equals(CombinationTypes.LOTO_KAT_CHIF.name())){
                save4Chif(CombinationTypes.LOTO_KAT_CHIF.name());
            } if (combinationTypes.name().equals(CombinationTypes.OPSYON.name())){
                save4Chif(CombinationTypes.OPSYON.name());
            } if (combinationTypes.name().equals(CombinationTypes.MARYAJ.name())){
                save4Chif(CombinationTypes.MARYAJ.name());
            } if (combinationTypes.name().equals(CombinationTypes.EXTRA.name())){
                saveExtra();
            }
        }
    }
    private void saveBolet () {
        List<Combination> combinations = combinationRepository.findAllByCombinationType_ProductsName(CombinationTypes.BOLET.name());
        if(combinations.size() <= 0){
            List<NumberTwoDigits> numberTwoDigits = numberTwoDigitsRepository.findAll();
            CombinationType combinationType = combinationTypeRepository.findByProductsName(CombinationTypes.BOLET.name());
            for (int i=0; i< numberTwoDigits.size(); i++){
                Combination combination = new Combination();
                combination.setCombinationType(combinationType);
                combination.setEnabled(true);
                combination.setMaxPrice(1000);

                List<NumberTwoDigits> currentNumberTwoDigits =  new ArrayList<>();
                currentNumberTwoDigits.add(numberTwoDigits.get(i));
                combination.setNumberTwoDigits(currentNumberTwoDigits);
                combination.setSequence((long) (i+1));
                combinationRepository.save(combination);
            }
        }
    }

    private void saveLoto3 () {
        List<Combination> combinations = combinationRepository.findAllByCombinationType_ProductsName(CombinationTypes.LOTO_TWA_CHIF.name());
        if(combinations.size() <= 0){
            List<NumberThreeDigits> numberThreeDigits = numberThreeDigitsRepository.findAll();
            long sequence = combinationRepository.findByCombinationTypeProductsNameOrderBySequenceDesc(CombinationTypes.BOLET.name()).get(0).getSequence();
            CombinationType combinationType = combinationTypeRepository.findByProductsName(CombinationTypes.LOTO_TWA_CHIF.name());
            for (int i=0; i< numberThreeDigits.size(); i++){
                Combination combination = new Combination();
                combination.setCombinationType(combinationType);
                combination.setEnabled(true);
                combination.setMaxPrice(1000);
                combination.setNumberThreeDigits(numberThreeDigits.get(i));
                combination.setSequence((sequence+ i + 1));
                combinationRepository.save(combination);
            }
        }
    }

    private void save4Chif (String type) {
        List<Combination> combinations = new ArrayList<>();
        if (type.equals(CombinationTypes.LOTO_KAT_CHIF.name())){
            combinations= combinationRepository.findAllByCombinationType_ProductsName(CombinationTypes.LOTO_KAT_CHIF.name());
        }
        if (type.equals(CombinationTypes.OPSYON.name())){
            combinations= combinationRepository.findAllByCombinationType_ProductsName(CombinationTypes.OPSYON.name());
        }
        if (type.equals(CombinationTypes.MARYAJ.name())){
            combinations= combinationRepository.findAllByCombinationType_ProductsName(CombinationTypes.MARYAJ.name());
        }
        if(combinations.size() <= 0){
            long sequence = 0;
            CombinationType combinationType = new CombinationType();
            if (type.equals(CombinationTypes.LOTO_KAT_CHIF.name())){
                sequence = combinationRepository.findByCombinationTypeProductsNameOrderBySequenceDesc(CombinationTypes.LOTO_TWA_CHIF.name()).get(0).getSequence();
                combinationType = combinationTypeRepository.findByProductsName(CombinationTypes.LOTO_KAT_CHIF.name());
            }
            if (type.equals(CombinationTypes.OPSYON.name())){
                sequence =combinationRepository.findByCombinationTypeProductsNameOrderBySequenceDesc(CombinationTypes.LOTO_KAT_CHIF.name()).get(0).getSequence();
                combinationType= combinationTypeRepository.findByProductsName(CombinationTypes.OPSYON.name());
            }
            if (type.equals(CombinationTypes.MARYAJ.name())){
                sequence =combinationRepository.findByCombinationTypeProductsNameOrderBySequenceDesc(CombinationTypes.OPSYON.name()).get(0).getSequence();
                combinationType = combinationTypeRepository.findByProductsName(CombinationTypes.MARYAJ.name());
            }
            List<NumberTwoDigits> numberTwoDigits = numberTwoDigitsRepository.findAll();
            List <List<NumberTwoDigits>> numbersTwoDigitsList = new ArrayList<>();
            numbersTwoDigitsList.add(numberTwoDigits);
            numbersTwoDigitsList.add(numberTwoDigits);
            GeneratePermutations4Chif(combinationType, numbersTwoDigitsList,sequence,0, "", 0);

        }
    }

    private void saveExtra () {
        List<Combination> combinations = combinationRepository.findAllByCombinationType_ProductsName(CombinationTypes.EXTRA.name());
        if(combinations.size() <= 0){
            List<NumberTwoDigits> numberTwoDigits = numberTwoDigitsRepository.findAll();
            List<NumberThreeDigits> numberThreeDigits = numberThreeDigitsRepository.findAll();
            CombinationType combinationType = combinationTypeRepository.findByProductsName(CombinationTypes.EXTRA.name());
            long sequence = combinationRepository.findByCombinationTypeProductsNameOrderBySequenceDesc(CombinationTypes.MARYAJ.name()).get(0).getSequence();
            List <List<Object>> numbersObject = new ArrayList<>();
            List<Object> objectsThreeDigits = new ArrayList<>(numberThreeDigits);
            List<Object> objectsTwoDigits = new ArrayList<>(numberTwoDigits);

            numbersObject.add(objectsThreeDigits);
            numbersObject.add(objectsTwoDigits);
            GeneratePermutations5Chif(combinationType, numbersObject ,sequence,0, "", 0);
        }
    }

    private void GeneratePermutations4Chif(CombinationType combinationType, List<List<NumberTwoDigits>> Lists, long sequence, int depth, String current, int currentIndex)
    {
        if(depth == Lists.size())
        {
            Combination combination = new Combination();
            combination.setCombinationType(combinationType);
            combination.setEnabled(true);
            combination.setMaxPrice(1000);
            String [] cur = current.split("/");
            combination.setNumberTwoDigits(Arrays.asList(Lists.get(0).get(Integer.parseInt(cur[0])), Lists.get(0).get(Integer.parseInt(cur[1]))));
            combination.setSequence((sequence + 1));
            combinationRepository.save(combination);
            return;
        }

        for(int i = 0; i < Lists.get(depth).size(); ++i)
        {
            if (combinationType.getProducts().getName().equals(CombinationTypes.MARYAJ.name())){
                if (i > 0 && depth == 0)
                    currentIndex = i;

                if(i == 0 && depth > 0)
                    i = currentIndex;
            }
            GeneratePermutations4Chif(combinationType, Lists, (sequence+depth+1),depth + 1, current + i + "/", currentIndex);
        }
    }

    private void GeneratePermutations5Chif(CombinationType combinationType, List<List<Object>> Lists, long sequence, int depth, String current, int currentIndex)
    {
        if(depth == Lists.size())
        {
            Combination combination = new Combination();
            combination.setCombinationType(combinationType);
            combination.setEnabled(true);
            combination.setMaxPrice(1000);
            String [] cur = current.split("/");
            combination.setNumberThreeDigits(NumberThreeDigits.class.cast(Lists.get(0).get(Integer.parseInt(cur[0]))));
            combination.setNumberTwoDigits(Arrays.asList(NumberTwoDigits.class.cast(Lists.get(1).get(Integer.parseInt(cur[1])))));
            combination.setSequence((sequence + 1));
            combinationRepository.save(combination);
            return;
        }

        for(int i = 0; i < Lists.get(depth).size(); ++i)
        {
            GeneratePermutations5Chif(combinationType, Lists, (sequence+depth+1),depth + 1, current + i + "/", currentIndex);
        }
    }

}