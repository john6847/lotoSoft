package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Enums.Roles;
import com.b.r.loteriab.r.Model.Role;
import com.b.r.loteriab.r.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 18/10/18.
 */
@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InitServices initServices;

    @Autowired
    private EnterpriseService enterpriseService;

    public Role findRoleByNameAndEnterpriseId(String name, Long enterpriseId) {
        return roleRepository.findByNameAndEnterpriseId(name, enterpriseId);
    }

    public List<Role> findAllByEnterpriseId(Long enterpriseId) {
        return roleRepository.findAllByEnterpriseId(enterpriseId);
    }

    public void createRoleForEnterprise(String enterpriseName, String superadmin, String admin, String seller, String recollector, String supervisor) {
        initServices.createRoles(enterpriseService.findEnterpriseByName(enterpriseName), superadmin, admin, seller, recollector, supervisor);
    }

    public List<Role> getSelectedRoles(String isAdmin, String isSeller, String isSupervisor, String isCollector, Long enterpriseId) {
        List<Role> roleSet = new ArrayList<>();
        if (isAdmin.equals("on")) {
            roleSet.add(findRoleByNameAndEnterpriseId(Roles.ROLE_ADMIN.name(), enterpriseId));
        }
        if (isSeller.equals("on")) {
            roleSet.add(findRoleByNameAndEnterpriseId(Roles.ROLE_SELLER.name(), enterpriseId));
        }
        if (isSupervisor.equals("on")) {
            roleSet.add(findRoleByNameAndEnterpriseId(Roles.ROLE_SUPERVISOR.name(), enterpriseId));
        }
        if (isCollector.equals("on")) {
            roleSet.add(findRoleByNameAndEnterpriseId(Roles.ROLE_COLLECTOR.name(), enterpriseId));
        }
        return roleSet;
    }

}
