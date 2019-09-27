package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.NumberTwoDigits;
import com.b.r.loteriab.r.Repository.NumberTwoDigitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public NumberTwoDigits saveNumberTwoDigits(NumberTwoDigits numberTwoDigits) {
        return numberTwoDigitsRepository.save(numberTwoDigits);
    }

}
