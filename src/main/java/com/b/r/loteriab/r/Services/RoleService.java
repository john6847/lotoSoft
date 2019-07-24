package com.b.r.loteriab.r.Services;

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

    public long cantidadUsuario(){
        return roleRepository.count();
    }

    public Role saveRole (Role role){
        roleRepository.save(role);
        return role;
    }

    public void elimarRolPorId(Long id){
        roleRepository.deleteRolById(id);
    }

    public Role findRoleByName (String name){
        return roleRepository.findByName(name);
    }

    public List<Role> todosRoles(){
        return roleRepository.findAll();
    }

}
