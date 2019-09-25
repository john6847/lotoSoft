package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Address;
import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.ViewModel.Helper;
import com.b.r.loteriab.r.Repository.EnterpriseRepository;
import com.b.r.loteriab.r.Validation.Result;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dany on 04/05/2019.
 */
@Service
@Transactional
public class EnterpriseService {
    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private Helper helper;

    private Result validateModel (Enterprise enterprise){
        Result result = new Result();

        if (enterprise.getName().isEmpty()){
            result.add("Ou dwe rantre yon nom pou antrepriz la");
            return result;
        }

        if (enterpriseRepository.findEnterpriseByName(enterprise.getName()) != null){
            result.add("Antrepriz sa egziste deja");
            return result;
        }

        if (enterprise.getSubDomain().isEmpty()){
            result.add("Ou dwe rantre subdomen nan.");
            return result;
        }

        if (enterpriseRepository.findEnterpriseBySubDomain(enterprise.getSubDomain()) != null){
            result.add("Antrepriz sa egziste deja");
            return result;
        }

        return result;
    }

    public Result saveEnterprise(Enterprise enterprise){
        Result result = validateModel(enterprise);
        if (!result.isValid()){
            return result;
        }
        try {
            Pair <String, Integer> enterpriseIdentifier= helper.createNewEnterpriseIdentifier();
            enterprise.setIdentifier(enterpriseIdentifier.getValue0());
            enterprise.setSequence(enterpriseIdentifier.getValue1());
            enterprise.setCreationDate(new Date());
            enterprise.setModificationDate(new Date());
            enterprise.setEnabled(true);
            enterpriseRepository.save(enterprise);
        }catch (Exception ex){
            result.add("Antrepriz la pa ka Kreye reeseye ankò");
        }
        return  result;
    }

    public Result updateEnterprise(Enterprise enterprise) {
        Result result = validateModel(enterprise);
        if (!result.isValid()){
            return result;
        }

        Enterprise currentEnterprise = enterpriseRepository.findEnterpriseById(enterprise.getId());
        currentEnterprise.setName(enterprise.getName());
        currentEnterprise.setModificationDate(new Date());
        try {
            enterpriseRepository.save(currentEnterprise);
        }catch (Exception ex){
            result.add("Antrepriz la pa ka aktyalize reeseye ankò");
        }
        return  result;
    }

    public ArrayList<Enterprise> findAllEnterprise(){
        return (ArrayList<Enterprise>)enterpriseRepository.findAll();
    }

    public ArrayList<Enterprise> selectAllEnterpriseExcept(){
        return (ArrayList<Enterprise>)enterpriseRepository.selectAllEnterpriseExcept("BR-tenant");
    }

    public Page<Enterprise> findAllEnterpriseByState(int page, int itemPerPage, Boolean state){
        Pageable pageable = PageRequest.of(page, itemPerPage);
        if(state != null){
            return enterpriseRepository.findAllByEnabledOrderByIdDesc(pageable,state);
        }
        return enterpriseRepository.findAllByOrderById(pageable);
    }

    public Enterprise findEnterpriseById(Long id){
        return enterpriseRepository.findEnterpriseById(id);
    }
    public Enterprise findEnterpriseByName(String name){
        return enterpriseRepository.findEnterpriseByName(name);
    }

    public Enterprise findEnterpriseByIdAndEnabled(Long id, boolean enabled){
        return enterpriseRepository.findEnterpriseByEnabledAndId(enabled, id);
    }

    public Result deleteEnterpriseById(Long id){
        Result result = new Result();
        Enterprise enterprise= enterpriseRepository.findEnterpriseById(id);
        if(enterprise == null) {
            result.add("Antrepriz sa ou bezwen elimine a pa egziste");
            return result;
        }
        try{
            enterpriseRepository.deleteById(id);
        }catch (Exception ex){
            result.add("Machin la pa ka elimine reeseye ankò");
        }
        return result;
    }

    public Address buildAddress (String country, String region, String city, String sector, String street, int number, String phone, String email){

        Address address = new Address();
        if (!country.isEmpty()){
            address.setCountry(country);
        }
        if (!region.isEmpty()){
            address.setRegion(region);
        }
        if (!city.isEmpty()){
            address.setCity(city);
        }
        if (!sector.isEmpty()){
            address.setSector(sector);
        }
        if (number!=0){
            address.setNumber(number);
        }
        if (!street.isEmpty()){
            address.setStreet(street);
        }
        if (!phone.isEmpty()){
            address.setPhone(phone);
        }
        if (!email.isEmpty()){
            address.setEmail(email);
        }
        return address;
    }
}
