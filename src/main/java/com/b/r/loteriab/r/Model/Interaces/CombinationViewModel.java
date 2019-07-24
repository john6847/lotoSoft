package com.b.r.loteriab.r.Model.Interaces;

/**
 * Created by Dany on 04/05/2019.
 */
public interface CombinationViewModel {

    public Long getCombinationId();

    public void setCombinationId(Long combinationId);

    public Long getMaxPrice();

    public void setMaxPrice(Long maxPrice);

    public Long getCombinationTypeId();

    public void setCombinationTypeId(Long combinationTypeId);

    public boolean isEnabled();

    public void setEnabled(boolean enabled);

    public Long getNumberTwoDigitsId();

    public void setNumberTwoDigitsId(Long numberTwoDigitsId);

    public String getNumberTwoDigits();

    public void setNumberTwoDigits(String numberTwoDigits);

    public Long getNumberThreeDigitsId();

    public void setNumberThreeDigitsId(Long numberThreeDigitsId);

    public String getNumberThreeDigits();

    public void setNumberThreeDigits(String numberThreeDigits);

    public Long getProductsId();

    public void setProductsId(Long productsId);

    public String getProductsName();

    public void setProductsName(String productsName);
}
