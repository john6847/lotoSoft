package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Role;
import com.b.r.loteriab.r.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Role saveRole (Role role, Enterprise enterprise){
        // TODO: Set entterprise to entity
        role.setEnterprise(enterprise);
        roleRepository.save(role);

        return role;
    }

    public void deleteRoleByIdAndEnterpriseId(Long id, Long enterpriseId){
        roleRepository.deleteRolByIdAndEnterpriseId(id, enterpriseId);
    }

    public Role findRoleByNameAndEnterpriseId (String name, Long enterpriseId){
        return roleRepository.findByNameAndEnterpriseId(name, enterpriseId);
    }

    public List<Role> findAllByEnterpriseId(Long enterpriseId){
        return roleRepository.findAllByEnterpriseId(enterpriseId);
    }

    public void createRoleForEnterprise(String enterpriseName, String admin, String seller, String recollector, String supervisor ){
        initServices.createRoles(enterpriseService.findEnterpriseByName(enterpriseName), admin, seller, recollector, supervisor);
    }

}
