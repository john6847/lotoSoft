package com.b.r.loteriab.r.Services;

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
            return result.add("Ou dwe rantre yon nom pou antrepriz la");
        }

        if (enterpriseRepository.findEnterpriseByName(enterprise.getName()) != null){
            return result.add("Antrepriz sa egziste deja");
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

    public Page<Enterprise> findAllEnterpriseByState(int page, int itemPerPage, Boolean state){
        Pageable pageable = PageRequest.of(page, itemPerPage);
        if(state != null){
            return enterpriseRepository.findAllByEnabled(pageable,state);
        }
        return enterpriseRepository.findAll(pageable);
    }

    public Enterprise findEnterpriseById(Long id){
        return enterpriseRepository.findEnterpriseById(id);
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
}
