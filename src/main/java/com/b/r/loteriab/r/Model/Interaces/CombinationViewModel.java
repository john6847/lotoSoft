package com.b.r.loteriab.r.Model.Interaces;

/**
 * Created by Dany on 04/05/2019.
 */
public interface CombinationViewModel {

    Long getCombinationId();

    void setCombinationId(Long combinationId);

    Long getMaxPrice();

    void setMaxPrice(Long maxPrice);

    Long getCombinationTypeId();

    void setCombinationTypeId(Long combinationTypeId);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    Long getNumberTwoDigitsId();

    void setNumberTwoDigitsId(Long numberTwoDigitsId);

    String getNumberTwoDigits();

    void setNumberTwoDigits(String numberTwoDigits);

    Long getNumberThreeDigitsId();

    void setNumberThreeDigitsId(Long numberThreeDigitsId);

    String getNumberThreeDigits();

    void setNumberThreeDigits(String numberThreeDigits);

    Long getProductsId();

    void setProductsId(Long productsId);

    String getProductsName();

    void setProductsName(String productsName);
}
