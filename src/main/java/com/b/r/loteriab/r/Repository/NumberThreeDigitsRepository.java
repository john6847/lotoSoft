package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.NumberThreeDigits;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
@Transactional
public interface NumberThreeDigitsRepository extends JpaRepository<NumberThreeDigits, Long> {

    NumberThreeDigits findNumberThreeDigitsById(Long id);

    Page<NumberThreeDigits> findAll(Pageable pageable);

    List<NumberThreeDigits> findAll();

    NumberThreeDigits save(NumberThreeDigits numberThreeDigits);
}
