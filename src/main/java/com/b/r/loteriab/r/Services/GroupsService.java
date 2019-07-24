package com.b.r.loteriab.r.Services;


import com.b.r.loteriab.r.Model.Address;
import com.b.r.loteriab.r.Model.Groups;
import com.b.r.loteriab.r.Repository.AddressRepository;
import com.b.r.loteriab.r.Repository.GroupsRepository;
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
public class GroupsService {
    @Autowired
    private GroupsRepository groupRepository;

    @Autowired
    private AddressRepository addressRepository;

    private Result validateModel (Groups groups){
        Result result = new Result();

        if (groups.getDescription().isEmpty()){
            result.add("Ou sipoze bay yon deskripsyon pou gwoup la");
        }

        if (groups.getParentSeller() == null){
            result.add("Responsab gwoup la pa ka vid");
        }

        if (groups.getAddress() == null){
            result.add("Ou dwe antre yon adrès pou gwoup la");
        }

        return result;
    }

    public Result save(Groups groups){
        Result result = validateModel(groups);
        if (!result.isValid()){
            return result;
        }
        try {
            Address address = addressRepository.save(groups.getAddress());
            if (address!=null){
                groups.setAddress(address);
            }
            groups.setCreationDate(new Date());
            groups.setModificationDate(new Date());
            groups.setEnabled(true);
            groupRepository.save(groups);
        }catch (Exception ex){
            result.add("Gwoup la pa ka aktyalize reeseye ankò");
        }
        return  result;
    }

    public void deleteGroupsId(Long id){
        groupRepository.deleteById(id);
    }

    public List <Groups> findAll(){
        return groupRepository.findAll();
    }

    public List<Groups> findAllGroups(){
        return groupRepository.findAll();
    }

    public Groups findGroupsById (Long id) {
        return groupRepository.findGroupsById(id);
    }

    public Result deleteGroupsById(Long id){
        Result result = new Result();
        Groups groups = groupRepository.findGroupsById(id);
        if(groups == null) {
            result.add("Gwoup sa pa egziste");
            return result;
        }
        try{
            groupRepository.deleteById(id);
        }catch (Exception ex){
            return result.add("Gwoup la pa ka elimine reeseye ankò");
        }
        return result;
    }


    public Page<Groups> findAllGroupByState(int page, int itemPerPage, Boolean state){
        Pageable pageable = PageRequest.of(page,itemPerPage);
        if(state != null){
            return groupRepository.findAllByEnabled(pageable,state);
        }
        return groupRepository.findAll(pageable);
    }

}
