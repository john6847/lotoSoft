package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.NumberThreeDigits;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
public interface NumberThreeDigitsRepository extends JpaRepository<NumberThreeDigits, Long> {

    NumberThreeDigits findNumberThreeDigitsById(Long id);

    Page<NumberThreeDigits> findAll(Pageable pageable);

    void deleteById(Long id);

    NumberThreeDigits save(NumberThreeDigits numberThreeDigits);
}
