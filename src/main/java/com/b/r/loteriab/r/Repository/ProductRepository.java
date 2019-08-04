package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    List<Products> findAllByName(String name);
    List<Products> findAll ();
}
