package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.GlobalConfiguration;
import com.b.r.loteriab.r.Model.Pos;
import com.b.r.loteriab.r.Repository.GlobalConfigurationRepository;
import com.b.r.loteriab.r.Repository.PosRepository;
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
public class GlobalConfigurationService {
    @Autowired
    private GlobalConfigurationRepository globalConfigurationRepository;

    @Autowired
    private EnterpriseService enterpriseService;

    private Result validateModel(GlobalConfiguration globalConfiguration) {
        Result result = new Result();

        if (globalConfiguration.isLimitCombinationPrice()) {
            if(globalConfiguration.getMaxLimitCombinationPrice() <= 0)
            result.add("Ou dwe rantre pri limit pou yo vann konbinezon an");
        }
        if (globalConfiguration.isNotifyLimitCombinationPrice()) {
            if(globalConfiguration.getMaxLimitCombinationPrice() <= 0)
            result.add("Ou dwe rantre pri limit pou yo vann konbinezon an");
        }

        if (globalConfiguration.isDeleteUserTokenAfterAmountOfTime()) {
            if(globalConfiguration.getUserTokenLifeTime() <= 0)
            result.add("Ou dwe rantre kantite minit pou vande fe konekte avan li reouvri sesyon li");
        }

        if (globalConfiguration.isCanDeleteTicket()) {
            if(globalConfiguration.getTicketLifeTime() <= 0)
            result.add("Ou dwe rantre kantite minit pou vande a fe avan li elimine tike a");
        }

        return result;
    }

    public Result saveGlobalConfiguration(GlobalConfiguration globalConfiguration, Enterprise enterprise) {
        globalConfiguration = this.clearEntity(globalConfiguration);
        globalConfiguration.setEnterprise(enterpriseService.findEnterpriseByName(enterprise.getName()));
        Result result = validateModel(globalConfiguration);
        if (!result.isValid()) {
            return result;
        }
        try {
            globalConfigurationRepository.save(globalConfiguration);
        } catch (Exception ex) {
            result.add("Konfigirasyon sa pa ka anrejistre, reeseye ankò");
        }
        return result;
    }

    public Result upadteGlobalConfiguration(GlobalConfiguration globalConfiguration, Enterprise enterprise) {
        globalConfiguration = this.clearEntity(globalConfiguration);

        globalConfiguration.setEnterprise(enterpriseService.findEnterpriseByName(enterprise.getName()));
        Result result = validateModel(globalConfiguration);
        if (!result.isValid()) {
            return result;
        }

        GlobalConfiguration savedGlobalConfiguration = globalConfigurationRepository.findByEnterpriseId(enterprise.getId());
        if (savedGlobalConfiguration == null){
            result.add("Konfigirasyon sa pa ka anrejistre, reeseye ankò");
            return result;
        }

        savedGlobalConfiguration.setLimitCombinationPrice(globalConfiguration.isLimitCombinationPrice());
        savedGlobalConfiguration.setNotifyLimitCombinationPrice(globalConfiguration.isNotifyLimitCombinationPrice());
        savedGlobalConfiguration.setMaxLimitCombinationPrice(globalConfiguration.getMaxLimitCombinationPrice());
        savedGlobalConfiguration.setTransferSaleToAnotherShift(globalConfiguration.isTransferSaleToAnotherShift());
        savedGlobalConfiguration.setCanDeleteTicket(globalConfiguration.isCanDeleteTicket());
        savedGlobalConfiguration.setDeleteUserTokenAfterAmountOfTime(globalConfiguration.isDeleteUserTokenAfterAmountOfTime());
        savedGlobalConfiguration.setUserTokenLifeTime(globalConfiguration.getUserTokenLifeTime());
        savedGlobalConfiguration.setCanDeleteTicket(globalConfiguration.isCanDeleteTicket());
        savedGlobalConfiguration.setTicketLifeTime(globalConfiguration.getTicketLifeTime());
        savedGlobalConfiguration.setCanReplayTicket(globalConfiguration.isCanReplayTicket());
        try {
            globalConfigurationRepository.save(globalConfiguration);
        } catch (Exception ex) {
            result.add("Machin sa pa ka anrejistre, reeseye ankò");
        }
        return result;
    }

    public GlobalConfiguration findGlobalConfiguration(Long enterpriseId){
        return globalConfigurationRepository.findByEnterpriseId(enterpriseId);
    }

    private GlobalConfiguration clearEntity (GlobalConfiguration globalConfiguration){
        if (!globalConfiguration.isLimitCombinationPrice()) {
            globalConfiguration.setMaxLimitCombinationPrice(0);
            globalConfiguration.setNotifyLimitCombinationPrice(false);
        }
        if (!globalConfiguration.isNotifyLimitCombinationPrice()) {
            globalConfiguration.setMaxLimitCombinationPrice(0);
        }

        if (!globalConfiguration.isDeleteUserTokenAfterAmountOfTime()) {
            globalConfiguration.setUserTokenLifeTime(0);
        }

        if (!globalConfiguration.isCanDeleteTicket()) {
            globalConfiguration.setTicketLifeTime(0);
        }
        return globalConfiguration;
    }
}
