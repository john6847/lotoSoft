package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Enums.PaymentType;
import com.b.r.loteriab.r.Model.Seller;
import com.b.r.loteriab.r.Repository.SellerRepository;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;


    private Result validateModel (Seller seller){
        Result result = new Result();

        if (seller.getUser().getId() == null){
            result.add("Vandè a dwe gen kont avan li anrejistre", "User");
        }


        return result;
    }

    public Result saveSeller (Seller seller, String useMonthlyPayment, Enterprise enterprise){
        Result result = validateModel(seller);
        if (useMonthlyPayment.equals("on")){
            if (seller.getAmountCharged() <= 0){
                result.add("Ou dwe mete yon montan pa mwa");
                return result;
            }
        }else {
            if (seller.getPercentageCharged() <= 0){
                result.add("Ou dwe mete yon pousantaj pa mwa");
                return  result;
            }
        }
        if (!result.isValid()){
            return result;
        }
        int payment = useMonthlyPayment.equals("on") ? PaymentType.MONTHLY.ordinal(): PaymentType.PERCENTAGE.ordinal();
        seller.setEnterprise(enterprise);
        seller.setPaymentType(payment);
        seller.setCreationDate(new Date());
        seller.setModificationDate(new Date());
        seller.setLastPaymentDate(new Date());
        seller.setEnabled(true);
        try {
            sellerRepository.save(seller);
        }catch (Exception ex){
            result.add("Vandè a pa ka anrejistre reeseye ankò");
        }
        return  result;
    }

    public Result updateSeller(Seller seller,String useMonthlyPayment, Long enterpriseId) {
        Result result = new Result();
        if (useMonthlyPayment.equals("on")){
            if (seller.getAmountCharged() <= 0){
                result.add("Ou dwe mete yon montan pa mwa");
                return result;
            }
        }else {
            if (seller.getPercentageCharged() <= 0){
                result.add("Ou dwe mete yon pousantaj pa mwa");
                return  result;
            }
        }

        if (!result.isValid()){
            return result;
        }

        Seller currentSeller = sellerRepository.findSellerByIdAndEnterpriseId(seller.getId(), enterpriseId);
        currentSeller.setModificationDate(new Date());
        currentSeller.setLastPaymentDate(seller.getLastPaymentDate());
        currentSeller.setAmountCharged(seller.getAmountCharged());
        currentSeller.setGroups(seller.getGroups());
        currentSeller.setPercentageCharged(seller.getPercentageCharged());

        try {
            sellerRepository.save(currentSeller);
        }catch (Exception ex){
            result.add("Vandè a pa ka aktyalize reeseye ankò");
        }
        return  result;
    }

    public Seller findSellerById(Long id,Long enterpriseId){return sellerRepository.findSellerByIdAndEnterpriseId(id, enterpriseId);}

    public Seller findSellerByIdAndEnabled(Long id, boolean enabled, Long enterpriseId){
        return sellerRepository.findSellerByIdAndEnabledAndEnterpriseId(id,enabled,enterpriseId);
    }

    public ArrayList<Seller> findAllSellersByEnterpriseId(Long enterpriseId) {
        return (ArrayList<Seller>) sellerRepository.findAllByEnterpriseId(enterpriseId);
    }

    public ArrayList<Seller> findAllSellerByGroupsId(Long id, Long enterpriseId) {
        return (ArrayList<Seller>) sellerRepository.findAllByGroupsIdAndEnterpriseId(id, enterpriseId);
    }

    public Result deleteSellerById(Long id, Long enterpriseId){
        Result result = new Result();
        Seller draw = sellerRepository.findSellerByIdAndEnterpriseId(id, enterpriseId);
        if(draw == null) {
            result.add("Vandè sa ou bezwen elimine a pa egziste");
            return result;
        }
        try{
            sellerRepository.deleteSellerByIdAndEnterpriseId(id, enterpriseId);
        }catch (Exception ex){
            result.add("Vandè a pa ka elimine reeseye ankò");
        }
        sellerRepository.deleteSellerByIdAndEnterpriseId(id,enterpriseId);
        return result;
    }
    public List<Seller> findAllSellerByEnabled(Boolean enabled, Long enterpriseId){
        if (enabled!= null){
            return sellerRepository.findAllByEnabledAndEnterpriseId(enabled, enterpriseId);
        }
        return sellerRepository.findAllByEnterpriseId(enterpriseId);
    }

    public Page <Seller> findAllSellerByState(int page, int itemPerPage, Boolean state, Long enterpriseId){
        Pageable pageable = PageRequest.of(page,itemPerPage);
        if(state != null){
            return sellerRepository.findAllByEnabledAndEnterpriseId(pageable,state, enterpriseId);
        }
        return sellerRepository.findAllByEnterpriseId(pageable, enterpriseId);
    }

     public List <Seller> selectAllSellers(Long enterpriseId){
            return sellerRepository.selectAllSellersByEnterpriseId(true, enterpriseId);
        }

}
