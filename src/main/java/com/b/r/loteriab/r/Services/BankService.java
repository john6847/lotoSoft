package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Bank;
import com.b.r.loteriab.r.Model.Seller;
import com.b.r.loteriab.r.Model.ViewModel.Helper;
import com.b.r.loteriab.r.Repository.BankRepository;
import com.b.r.loteriab.r.Repository.SellerRepository;
import com.b.r.loteriab.r.Validation.NumberHelper;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Service
@Transactional
public class BankService {
    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private Helper helper;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private SellerRepository sellerRepository;

    private Result validateModel(Bank bank) {
        Result result = new Result();

        if (bank.getDescription().isEmpty()) {
            result.add("Deskripsyon an pa ka vid", "description");
        }

        if (Long.parseLong(NumberHelper.getNumericValue(bank .getId()).toString()) <= 0) {
            if (bankRepository.findBankByDescriptionIgnoreCaseAndEnterpriseId(bank.getDescription(), bank.getEnterprise().getId()) != null) {
                result.add("Deskripsyon bank sa egziste deja");
                return result;
            }
        } else {
            List<Bank> existedBank =  bankRepository.selectbankIfExist(bank.getId(), bank.getDescription(), bank.getEnterprise().getId());
            if (existedBank != null && existedBank.size() > 0){
                result.add("Deskripsyon bank sa egziste deja");
                return result;
            }
        }


        if (bank.getSerial().isEmpty()) {
            result.add("Serial la pa ka vid", "serial");
        }

        return result;
    }

    public Result saveBank(Bank bank, Enterprise enterprise) {
        bank.setSerial(helper.createBankSerial(enterprise));
        bank.setEnterprise(enterpriseService.findEnterpriseByName(enterprise.getName()));
        Result result = validateModel(bank);
        if (!result.isValid()) {
            return result;
        }
        try {
            if (bank.getSeller() != null) {
                Seller seller = sellerService.findSellerById(bank.getSeller().getId(), enterprise.getId());
                if (seller != null) {
                    seller.setPos(bank.getPos());
                    sellerRepository.save(seller);
                }
            }
            bank.setCreationDate(new Date());
            bank.setModificationDate(new Date());
            bank.setEnabled(true);
            bankRepository.save(bank);
        } catch (Exception ex) {
            result.add("Bank la pa ka aktyalize reeseye ankò");
        }
        return result;
    }

    public Result updateBank(Bank bank, Enterprise enterprise) {
        Result result = validateModel(bank);
        if (!result.isValid()) {
            return result;
        }

        Bank currentBank = bankRepository.findBankByIdAndEnterpriseId(bank.getId(), enterprise.getId());
        if (bank.getSeller() != null) {
            Seller seller = sellerService.findSellerById(bank.getSeller().getId(), enterprise.getId());
            if (seller != null) {
                seller.setPos(bank.getPos());
                sellerRepository.save(seller);
            }
        }
        currentBank.setDescription(bank.getDescription());
        currentBank.setAddress(bank.getAddress());
        currentBank.setPos(bank.getPos());
        currentBank.setSeller(bank.getSeller());
        currentBank.setModificationDate(new Date());
        currentBank.getAddress().setRegion(bank.getAddress().getRegion());
        currentBank.getAddress().setSector(bank.getAddress().getSector());
        currentBank.getAddress().setCity(bank.getAddress().getCity());
        currentBank.getAddress().setStreet(bank.getAddress().getStreet());
        try {
            bankRepository.save(currentBank);
        } catch (Exception ex) {
            result.add("Bank la pa ka aktyalize reeseye ankò");
        }
        return result;
    }

    public Page<Bank> findAllBankByState(int page, int itemPerPage, Boolean state, Long enterpriseId) {
        Pageable pageable = PageRequest.of(page - 1, itemPerPage);
        if (state != null) {
            return bankRepository.findAllByEnabledAndEnterpriseIdOrderByIdDesc(state, enterpriseId, pageable);
        }
        return bankRepository.findAllByEnterpriseIdOrderByIdDesc(enterpriseId, pageable);
    }

    public Bank findBankById(Long id, Long enterpriseId) {
        return bankRepository.findBankByIdAndEnterpriseId(id, enterpriseId);
    }

    public Bank findBankBySellerIdAndEnterpriseId(Long sellerId, Long enterpriseId) {
        return bankRepository.findBankBySellerIdAndEnterpriseId(sellerId, enterpriseId);
    }

    public Result deleteBankById(Long id, Long enterpriseId) {
        Result result = new Result();
        Bank bank = bankRepository.findBankByIdAndEnterpriseId(id, enterpriseId);
        if (bank == null) {
            result.add("Bank sa ou bezwen elimine a pa egziste");
            return result;
        }
        try {
            bankRepository.deleteById(id);
        } catch (Exception ex) {
            result.add("Bank la pa ka elimine reeseye ankò");
        }
        return result;
    }
}
