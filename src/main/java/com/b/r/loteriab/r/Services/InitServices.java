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
//        createShift();
        createProducts();
//        createRoles();
//        createCombinationType();
//        createCombinations();
        securityServices.crearUsuarioAdmin();
    }

    /**
     * Create CombinationType on Init
     * @return
     */
    public void createCombinationType(Enterprise enterprise, String bolet, String lotoTwaChif, String lotoKatChif, String opsyon, String maryaj, String extra) {
        List<Products> products = productRepository.findAll();
        if(products.size() > 0)
        {
            List<CombinationType> combinationTypes = combinationTypeRepository.findAllByEnterpriseId(enterprise.getId());
            if(combinationTypes.size() <= 0){
                for(Products product: products){
                    CombinationType combinationType = new CombinationType();
                    combinationType.setEnabled(true);
                    combinationType.setModificationDate(new Date());
                    combinationType.setCreationDate(new Date());
                    combinationType.setNote("");
                    combinationType.setProducts(product);

                    if(product.getName().equals(CombinationTypes.BOLET.name())) {
                        if (bolet.equals("on")){
                            combinationType.setPayedPriceFirstDraw(50);
                            combinationType.setPayedPriceSecondDraw(20);
                            combinationType.setPayedPriceThirdDraw(10);
                            combinationType.setPayedPrice(0);
                        } else {
                            continue;
                        }

                    }else if(product.getName().equals(CombinationTypes.MARYAJ.name())){
                        if (maryaj.equals("on")){
                            combinationType.setPayedPrice(1000);
                            combinationType.setPayedPriceFirstDraw(0);
                            combinationType.setPayedPriceSecondDraw(0);
                            combinationType.setPayedPriceThirdDraw(0);
                        }else {
                            continue;
                        }
                    }else if (product.getName().equals(CombinationTypes.LOTO_TWA_CHIF.name())){
                        if (lotoTwaChif.equals("on")){
                            combinationType.setPayedPrice(500);
                            combinationType.setPayedPriceFirstDraw(0);
                            combinationType.setPayedPriceSecondDraw(0);
                            combinationType.setPayedPriceThirdDraw(0);
                        }else {
                            continue;
                        }
                    } else if (product.getName().equals(CombinationTypes.LOTO_KAT_CHIF.name()) || product.getName().equals(CombinationTypes.OPSYON.name())){
                        if (lotoKatChif.equals("on")){
                            combinationType.setPayedPrice(5000);
                            combinationType.setPayedPriceFirstDraw(0);
                            combinationType.setPayedPriceSecondDraw(0);
                            combinationType.setPayedPriceThirdDraw(0);
                        }else {
                            continue;
                        }
                    }else if (product.getName().equals(CombinationTypes.EXTRA.name())){
                        if (extra.equals("on")){
                            combinationType.setPayedPrice(25000);
                            combinationType.setPayedPriceFirstDraw(0);
                            combinationType.setPayedPriceSecondDraw(0);
                            combinationType.setPayedPriceThirdDraw(0);
                        }else {
                            continue;
                        }
                    }
                    combinationType.setEnterprise(enterprise);
                    combinationTypeRepository.save(combinationType);
                }
            }
        }
    }

    /**
     * Create NumberThreeDigits on Init
     * @return
     */
    public  void createNumberThreeDigits(){
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
    public  void createNumberTwoDigits(){
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
    public  void createProducts(){
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
    public  void createRoles(Enterprise enterprise, String superadmin, String admin, String seller, String recollector, String supervisor){
        List<Role> roles = roleRepository.findAllByEnterpriseId(enterprise.getId());
        if(roles.size()<=0){
            for (Roles role: Roles.values()){
                if(role.name().equals(Roles.ROLE_SUPER_ADMIN.name())){
                    if (superadmin.equals("on")){
                        Role newRole = new Role();
                        newRole.setName(role.name());
                        newRole.setEnterprise(enterprise);
                        roleRepository.save(newRole);
                    }
                } else if(role.name().equals(Roles.ROLE_ADMIN.name())){
                    if (admin.equals("on")){
                       Role newRole = new Role();
                        newRole.setName(role.name());
                        newRole.setEnterprise(enterprise);
                        roleRepository.save(newRole);
                    }
                } else if(role.name().equals(Roles.ROLE_SELLER.name())){
                    if (seller.equals("on")){
                       Role newRole = new Role();
                        newRole.setName(role.name());
                        newRole.setEnterprise(enterprise);
                        roleRepository.save(newRole);
                    }
                } else if(role.name().equals(Roles.ROLE_COLLECTOR.name())){
                    if (recollector.equals("on")){
                       Role newRole = new Role();
                        newRole.setName(role.name());
                        newRole.setEnterprise(enterprise);
                        roleRepository.save(newRole);
                    }
                } else if(role.name().equals(Roles.ROLE_SUPERVISOR.name())){
                    if (supervisor.equals("on")){
                       Role newRole = new Role();
                        newRole.setName(role.name());
                        newRole.setEnterprise(enterprise);
                        roleRepository.save(newRole);
                    }
                }
            }
        }
    }

    /**
     * Create Shift on Init
     * @return
     */
    public  void createShift(Enterprise enterprise){
        List<Shift> shifts = shiftRepository.findAllByEnterpriseId(enterprise.getId());
        if(shifts.size()<=0){
            for (Shifts shift: Shifts.values()){
               Shift newShift = new Shift();
                newShift.setName(shift.name());
                newShift.setEnterprise(enterprise);
                shiftRepository.save(newShift);
            }
        }
    }

    /**
     * Create Combination on Init
     * @return
     */
    public  void createCombinations(Enterprise enterprise, List<CombinationType> combinationTypes){

        for (CombinationType combinationType: combinationTypes){
            if (combinationType.getProducts().getName().equals(CombinationTypes.BOLET.name())){
                saveBolet(enterprise);
            } if (combinationType.getProducts().getName().equals(CombinationTypes.LOTO_TWA_CHIF.name())){
                saveLoto3(enterprise);
            } if (combinationType.getProducts().getName().equals(CombinationTypes.LOTO_KAT_CHIF.name())){
                save4Chif(CombinationTypes.LOTO_KAT_CHIF.name(), enterprise);
            } if (combinationType.getProducts().getName().equals(CombinationTypes.OPSYON.name())){
                save4Chif(CombinationTypes.OPSYON.name(), enterprise);
            } if (combinationType.getProducts().getName().equals(CombinationTypes.MARYAJ.name())){
                save4Chif(CombinationTypes.MARYAJ.name(), enterprise);
            } if (combinationType.getProducts().getName().equals(CombinationTypes.EXTRA.name())){
                saveExtra(enterprise);
            }
        }
    }
    public void saveBolet (Enterprise enterprise) {
        List<Combination> combinations = combinationRepository.findAllByCombinationTypeProductsNameAndEnterpriseId(CombinationTypes.BOLET.name(), enterprise.getId());
        if(combinations.size() <= 0){
            List<NumberTwoDigits> numberTwoDigits = numberTwoDigitsRepository.findAll();
            CombinationType combinationType = combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.BOLET.name(), enterprise.getId());
            for (int i=0; i< numberTwoDigits.size(); i++){
                Combination combination = new Combination();
                combination.setCombinationType(combinationType);
                combination.setEnabled(true);
                combination.setMaxPrice(1000);

                List<NumberTwoDigits> currentNumberTwoDigits =  new ArrayList<>();
                currentNumberTwoDigits.add(numberTwoDigits.get(i));
                combination.setNumberTwoDigits(currentNumberTwoDigits);
                combination.setEnterprise(enterprise);
                combination.setResultCombination(numberTwoDigits.get(i).getNumberInStringFormat());
                combination.setSequence((long) (i+1));
                combinationRepository.save(combination);
            }
        }
    }

    public void saveLoto3 (Enterprise enterprise) {
        List<Combination> combinations = combinationRepository.findAllByCombinationTypeProductsNameAndEnterpriseId(CombinationTypes.LOTO_TWA_CHIF.name(), enterprise.getId());
        if(combinations.size() <= 0){
            List<NumberThreeDigits> numberThreeDigits = numberThreeDigitsRepository.findAll();
            long sequence = combinationRepository.findByEnterpriseIdAndCombinationTypeProductsNameOrderBySequenceDesc(enterprise.getId(), CombinationTypes.BOLET.name()).get(0).getSequence();
            CombinationType combinationType = combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.LOTO_TWA_CHIF.name(), enterprise.getId());
            for (int i=0; i< numberThreeDigits.size(); i++){
                Combination combination = new Combination();
                combination.setCombinationType(combinationType);
                combination.setEnabled(true);
                combination.setMaxPrice(1000);
                combination.setEnterprise(enterprise);
                combination.setResultCombination(numberThreeDigits.get(i).getNumberInStringFormat());
                combination.setNumberThreeDigits(numberThreeDigits.get(i));
                combination.setSequence((sequence+ i + 1));
                combinationRepository.save(combination);
            }
        }
    }

    public void save4Chif (String type, Enterprise enterprise) {
        List<Combination> combinations = new ArrayList<>();
        if (type.equals(CombinationTypes.LOTO_KAT_CHIF.name())){
            combinations= combinationRepository.findAllByCombinationTypeProductsNameAndEnterpriseId(CombinationTypes.LOTO_KAT_CHIF.name(), enterprise.getId());
        }
        if (type.equals(CombinationTypes.OPSYON.name())){
            combinations= combinationRepository.findAllByCombinationTypeProductsNameAndEnterpriseId(CombinationTypes.OPSYON.name(), enterprise.getId());
        }
        if (type.equals(CombinationTypes.MARYAJ.name())){
            combinations= combinationRepository.findAllByCombinationTypeProductsNameAndEnterpriseId(CombinationTypes.MARYAJ.name(),enterprise.getId());
        }
        if(combinations.size() <= 0){
            long sequence = 0;
            CombinationType combinationType = new CombinationType();
            if (type.equals(CombinationTypes.LOTO_KAT_CHIF.name())){
                sequence = combinationRepository.findByEnterpriseIdAndCombinationTypeProductsNameOrderBySequenceDesc(enterprise.getId(), CombinationTypes.LOTO_TWA_CHIF.name()).get(0).getSequence();
                combinationType = combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.LOTO_KAT_CHIF.name(), enterprise.getId());
            }
            if (type.equals(CombinationTypes.OPSYON.name())){
                sequence =combinationRepository.findByEnterpriseIdAndCombinationTypeProductsNameOrderBySequenceDesc(enterprise.getId(), CombinationTypes.LOTO_KAT_CHIF.name()).get(0).getSequence();
                combinationType= combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.OPSYON.name(), enterprise.getId());
            }
            if (type.equals(CombinationTypes.MARYAJ.name())){
                sequence =combinationRepository.findByEnterpriseIdAndCombinationTypeProductsNameOrderBySequenceDesc(enterprise.getId(),CombinationTypes.OPSYON.name()).get(0).getSequence();
                combinationType = combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.MARYAJ.name(), enterprise.getId());
            }
            List<NumberTwoDigits> numberTwoDigits = numberTwoDigitsRepository.findAll();
            List <List<NumberTwoDigits>> numbersTwoDigitsList = new ArrayList<>();
            numbersTwoDigitsList.add(numberTwoDigits);
            numbersTwoDigitsList.add(numberTwoDigits);
            GeneratePermutations4Chif(combinationType, enterprise, numbersTwoDigitsList,sequence,0, "", 0);
        }
    }

    public void saveExtra (Enterprise enterprise) {
        List<Combination> combinations = combinationRepository.findAllByCombinationTypeProductsNameAndEnterpriseId(CombinationTypes.EXTRA.name(), enterprise.getId());
        if(combinations.size() <= 0){
            List<NumberTwoDigits> numberTwoDigits = numberTwoDigitsRepository.findAll();
            List<NumberThreeDigits> numberThreeDigits = numberThreeDigitsRepository.findAll();
            CombinationType combinationType = combinationTypeRepository.findByProductsNameAndEnterpriseId(CombinationTypes.EXTRA.name(), enterprise.getId());
            long sequence = combinationRepository.findByEnterpriseIdAndCombinationTypeProductsNameOrderBySequenceDesc(enterprise.getId(),CombinationTypes.MARYAJ.name()).get(0).getSequence();
            List <List<Object>> numbersObject = new ArrayList<>();
            List<Object> objectsThreeDigits = new ArrayList<>(numberThreeDigits);
            List<Object> objectsTwoDigits = new ArrayList<>(numberTwoDigits);

            numbersObject.add(objectsThreeDigits);
            numbersObject.add(objectsTwoDigits);
            GeneratePermutations5Chif(combinationType, enterprise, numbersObject ,sequence,0, "", 0);
        }
    }

    private void GeneratePermutations4Chif(CombinationType combinationType, Enterprise enterprise, List<List<NumberTwoDigits>> Lists, long sequence, int depth, String current, int currentIndex)
    {
        if(depth == Lists.size())
        {
            Combination combination = new Combination();
            combination.setCombinationType(combinationType);
            combination.setEnabled(true);
            combination.setEnterprise(enterprise);
            combination.setMaxPrice(1000);
            String [] cur = current.split("/");
            combination.setNumberTwoDigits(Arrays.asList(Lists.get(0).get(Integer.parseInt(cur[0])), Lists.get(0).get(Integer.parseInt(cur[1]))));
            if (combinationType.getProducts().getName().equals(CombinationTypes.MARYAJ.name())){
                combination.setResultCombination(Lists.get(0).get(Integer.parseInt(cur[0])).getNumberInStringFormat()+"x"+ Lists.get(0).get(Integer.parseInt(cur[1])).getNumberInStringFormat());
            } else if (combinationType.getProducts().getName().equals(CombinationTypes.OPSYON.name()) || combinationType.getProducts().getName().equals(CombinationTypes.LOTO_KAT_CHIF.name())){
                combination.setResultCombination(Lists.get(0).get(Integer.parseInt(cur[0])).getNumberInStringFormat()+" "+ Lists.get(0).get(Integer.parseInt(cur[1])));
            }
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
            GeneratePermutations4Chif(combinationType, enterprise, Lists, (sequence+depth+1),depth + 1, current + i + "/", currentIndex);
        }
    }
//
    private void GeneratePermutations5Chif(CombinationType combinationType, Enterprise enterprise, List<List<Object>> Lists, long sequence, int depth, String current, int currentIndex)
    {
        if(depth == Lists.size())
        {
            Combination combination = new Combination();
            combination.setCombinationType(combinationType);
            combination.setEnabled(true);
            combination.setEnterprise(enterprise);
            combination.setMaxPrice(1000);
            String [] cur = current.split("/");
            combination.setNumberThreeDigits(NumberThreeDigits.class.cast(Lists.get(0).get(Integer.parseInt(cur[0]))));
            combination.setNumberTwoDigits(Arrays.asList(NumberTwoDigits.class.cast(Lists.get(1).get(Integer.parseInt(cur[1])))));
            combination.setResultCombination(NumberThreeDigits.class.cast(Lists.get(0).get(Integer.parseInt(cur[0]))).getNumberInStringFormat()+" "+ NumberTwoDigits.class.cast(Lists.get(1).get(Integer.parseInt(cur[1]))).getNumberInStringFormat());
            combination.setSequence((sequence + 1));
            combinationRepository.save(combination);
            return;
        }

        for(int i = 0; i < Lists.get(depth).size(); ++i)
        {
            GeneratePermutations5Chif(combinationType, enterprise, Lists, (sequence+depth+1),depth + 1, current + i + "/", currentIndex);
        }
    }

}