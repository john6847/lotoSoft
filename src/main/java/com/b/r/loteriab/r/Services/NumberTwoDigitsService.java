package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.NumberTwoDigits;
import com.b.r.loteriab.r.Repository.NumberTwoDigitsRepository;
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
public class NumberTwoDigitsService {
    @Autowired
    private NumberTwoDigitsRepository numberTwoDigitsRepository;

    public NumberTwoDigits saveNumberTwoDigits (NumberTwoDigits numberTwoDigits){
        return numberTwoDigitsRepository.save(numberTwoDigits);
    }

    public void deleteNumberTwoDigitsId(Long id){
        numberTwoDigitsRepository.deleteById(id);
    }

    public Page<NumberTwoDigits> findAllNumberTwoDigits(int page, int itemPerPage){
        Pageable pageable = new PageRequest(page,itemPerPage);
        return numberTwoDigitsRepository.findAll(pageable);
    }

    public NumberTwoDigits findNumberTwoDigitsById(Long id){
        return numberTwoDigitsRepository.findNumberTwoDigitsById(id);
    }

}
