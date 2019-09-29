package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Pos;
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
public class PosService {
    @Autowired
    private PosRepository posRepository;

    @Autowired
    private EnterpriseService enterpriseService;

    private Result validateModel(Pos pos) {
        Result result = new Result();

        if (pos.getDescription().isEmpty()) {
            result.add("Deskripsyon an pa ka vid", "macAddress");
        }

        if (pos.getSerial().isEmpty()) {
            result.add("Nimewo seri la pa ka vid", "serial");
        }

        Pos savedPos = posRepository.findPosByDescriptionAndEnterpriseIdAndDeletedFalse(pos.getDescription(), pos.getEnterprise().getId());
        if (savedPos != null) {
            if (pos.getId() == null || pos.getId() <= 0) {
                result.add("Deskripsyon sa egziste deja pou yon lòt machin", "description");
            } else {
                if (!pos.getId().equals(savedPos.getId())) {
                    result.add("Deskripsyon sa egziste deja pou yon lòt machin", "description");
                }
            }
        }

        savedPos = posRepository.findPosBySerialAndEnterpriseIdAndDeletedFalse(pos.getSerial(), pos.getEnterprise().getId());
        if (savedPos != null) {
            if (pos.getId() == null || pos.getId() <= 0) {
                result.add("Nimewo seri sa egziste deja pou yon lòt machin", "serial");
            } else {
                if (!pos.getId().equals(savedPos.getId())) {
                    result.add("Nimewo seri sa egziste deja pou yon lòt machin", "serial");
                }
            }
        }

        return result;
    }

    public Result savePos(Pos pos, Enterprise enterprise) {
        pos.setEnterprise(enterpriseService.findEnterpriseByName(enterprise.getName()));
        Result result = validateModel(pos);
        if (!result.isValid()) {
            return result;
        }
        try {
            pos.setCreationDate(new Date());
            pos.setModificationDate(new Date());
            pos.setEnabled(true);
            posRepository.save(pos);
        } catch (Exception ex) {
            result.add("Machin sa pa ka anrejistre, reeseye ankò");
        }
        return result;
    }

    public Result updatePos(Pos pos, Enterprise enterprise) {
        pos.setEnterprise(enterprise);
        Result result = validateModel(pos);
        if (!result.isValid()) {
            return result;
        }

        Pos currentPos = posRepository.findPosByIdAndEnterpriseIdAndDeletedFalse(pos.getId(), enterprise.getId());
        currentPos.setDescription(pos.getDescription());
        currentPos.setModificationDate(new Date());
        currentPos.setSerial(pos.getSerial());
        try {
            posRepository.save(currentPos);
        } catch (Exception ex) {
            result.add("machin sa pa ka aktyalize, reeseye ankò");
        }
        return result;
    }

    public Page<Pos> findAllPosByState(int page, int itemPerPage, Boolean state, Long enterpriseId) {
        Pageable pageable = PageRequest.of(page - 1, itemPerPage);
        if (state != null) {
            return posRepository.findAllByEnabledAndDeletedFalseAndEnterpriseIdOrderByIdDesc(pageable, state, enterpriseId);
        }
        return posRepository.findAllByEnterpriseIdAndDeletedFalseOrderByIdDesc(pageable, enterpriseId);
    }

    public Pos findPosById(Long id, Long enterpriseId) {
        return posRepository.findPosByIdAndEnterpriseIdAndDeletedFalse(id, enterpriseId);
    }

    public List<Pos> findPosBySellerId(Long sellerId, Long enterpriseId, int updating) {
        List<Pos> pos = posRepository.selectPosRelatedToSeller(sellerId, enterpriseId, true);

        if (updating <= 0) {
            if (pos == null) {
                pos = posRepository.selectAllPosFreeFromBankAndByEnterpriseId(true, enterpriseId);
            }
        } else {
            List<Pos> freePos = posRepository.selectAllPosFreeFromBankAndByEnterpriseId(true, enterpriseId);
            pos.addAll(freePos);
        }
        return pos;
    }

    public List<Pos> findAllFreePosByEnabled(boolean enabled, Long enterpriseId) {
        return posRepository.selectAllFreeAndEnabledPosByEnterpriseId(enabled, enterpriseId);
    }

    public Result deletePosById(Long id, Long enterpriseId) {
        Result result = new Result();
        Pos pos = posRepository.findPosByIdAndEnterpriseIdAndDeletedFalse(id, enterpriseId);
        pos.setDeleted(true);
        try {
            posRepository.save(pos);
        } catch (Exception ex) {
            result.add("Machin lan pa ka elimine, reeseye ankò");
        }
        return result;
    }
}
