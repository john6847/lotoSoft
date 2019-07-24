package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Pos;
import com.b.r.loteriab.r.Repository.PosRepository;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    private Result validateModel (Pos pos){
        Result result = new Result();

        if (pos.getDescription().isEmpty()){
            result.add("Deskripsyon an pa ka vid", "macAddress");
        }

        if (pos.getMacAddress().isEmpty()){
            result.add("MacAddress la pa ka vid", "macAddress");
        }

        if (pos.getSerial().isEmpty()){
            result.add("Serial la pa ka vid", "serial");
        }

        if (posRepository.findByMacAddress(pos.getMacAddress())!= null){
            result.add("Mac Address la egziste deja", "macAddress");
        }

        if (posRepository.findPosBySerial(pos.getSerial())!= null){
            result.add("Serial la egziste deja pou yon lot machin", "serial");
        }

//        verifying if desctription already exist for that company's machine
//        get enterprise from session
//        HttpSession session = httpServletRequest.getSession();
//        if (posRepository.findPosByDescriptionAndEnterpriseId(pos.getDescription(), )!= null){
//            result.add("Serial la egziste deja pou yon lot machin", "serial");
//        }

        return result;
    }

    public Result savePos(Pos pos){
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
            result.add("Pos la pa ka aktyalize reeseye ankò");
        }
        return  result;
    }

    public Result updatePos(Pos pos) {
        Result result = validateModel(pos);
        if (!result.isValid()){
            return result;
        }

        Pos currentPos = posRepository.findPosById(pos.getId());
        currentPos.setDescription(pos.getDescription());
        currentPos.setModificationDate(new Date());
        currentPos.setMacAddress(pos.getMacAddress());
        currentPos.setSerial(pos.getSerial());
        try {
            posRepository.save(currentPos);
        }catch (Exception ex){
            result.add("Pos la pa ka aktyalize reeseye ankò");
        }
        return  result;
    }

    public void deletePosId(Long id){
        posRepository.deleteById(id);
    }

    public ArrayList<Pos> findAllPos(){
        return (ArrayList<Pos>)posRepository.findAll();
    }

    public Page<Pos> findAllPosByState(int page, int itemPerPage, Boolean state){
        Pageable pageable = PageRequest.of(page,itemPerPage);
        if(state != null){
            return posRepository.findAllByEnabled(pageable,state);
        }
        return posRepository.findAll(pageable);
    }

    public Page<Pos> findAllPosEnabled(int page, int itemPerPage){
        Pageable pageable = new PageRequest(page,itemPerPage);
        return posRepository.findAllByEnabled(pageable, true);
    }

    public Pos findPosById(Long id){
        return posRepository.findPosById(id);
    }

    public Pos findPosByIdAndEnabled(Long id, boolean enabled){
        return posRepository.findPosByIdAndEnabled(id, enabled);
    }

    public List<Pos> findPosByEnabled(Boolean enabled){
        if (enabled!= null){
            return posRepository.findAllByEnabled(enabled);
        }
        return posRepository.findAll();
    }

    public List<Pos> findAllFreePosByEnabled(boolean enabled){
        return posRepository.selectAllFreeAndEnabledPos(enabled);
    }

    public Result deletePosById(Long id){
        Result result = new Result();
        Pos pos = posRepository.findPosById(id);
        if(pos == null) {
            result.add("Machin sa ou bezwen elimine a pa egziste");
            return result;
        }
        try{
            posRepository.deleteById(id);
        }catch (Exception ex){
            result.add("Machin la pa ka elimine reeseye ankò");
        }
        return result;
    }
}
