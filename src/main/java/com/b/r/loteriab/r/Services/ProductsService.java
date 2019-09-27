package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Products;
import com.b.r.loteriab.r.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Roman on 18/10/18.
 */
@Service
@Transactional
public class ProductsService {
    @Autowired
    private ProductRepository productRepository;

    public List<Products> findAll() {
        return productRepository.findAll();
    }
}
