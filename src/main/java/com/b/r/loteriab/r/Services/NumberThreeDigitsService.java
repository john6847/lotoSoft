package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.NumberThreeDigits;
import com.b.r.loteriab.r.Repository.NumberThreeDigitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dany on 04/05/2019.
 */
@Service
@Transactional
public class NumberThreeDigitsService {
    @Autowired
    private NumberThreeDigitsRepository numberThreeDigitsRepository;

    public NumberThreeDigits saveNumberThreeDigits (NumberThreeDigits numberThreeDigits){
        return numberThreeDigitsRepository.save(numberThreeDigits);
    }

    public void deleteNumberThreeDigitsId(Long id){
        numberThreeDigitsRepository.deleteById(id);
    }

    public Page<NumberThreeDigits> findAllNumberThreeDigits(int page, int itemPerPage){
        Pageable pageable = new PageRequest(page,itemPerPage);
        return numberThreeDigitsRepository.findAll(pageable);
    }

    public NumberThreeDigits findNumberThreeDigitsById(Long id){
        return numberThreeDigitsRepository.findNumberThreeDigitsById(id);
    }

}
