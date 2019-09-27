package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.NumberThreeDigits;
import com.b.r.loteriab.r.Repository.NumberThreeDigitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public NumberThreeDigits saveNumberThreeDigits(NumberThreeDigits numberThreeDigits) {
        return numberThreeDigitsRepository.save(numberThreeDigits);
    }
}
