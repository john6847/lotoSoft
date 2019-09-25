package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Enums.Roles;
import com.b.r.loteriab.r.Model.Role;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Repository.UserRepository;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 'org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * Created by Roman on 18/10/18.
 */
@Service
@Transactional
public class UsersService {
    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private RoleService roleServices;
    @Autowired
    private EnterpriseService enterpriseService;

    private Result validateModel (Users users){
        Result result = new Result();

        if (users.getName().isEmpty()){
            result.add("Ou dwe rantre non an", "Name");
        }
        if (users.getUsername().isEmpty()){
            result.add("Ou dwe rantre non itilizatè a", "Username");
        }
        if (users.getId()== null){
            if (users.getPassword().isEmpty()){
                result.add("Ou dwe rantre modpas la", "Password");
            }
            if (users.getPassword().isEmpty()){
                result.add("Ou dwe rantre modpas la", "Password");
            }
        }

        return result;
    }

    public void  deleteUser(Long id, Long enterpriseId){

        Users users = usersRepository.findUsersByIdAndEnterpriseId(id, enterpriseId);
        for(Role role: users.getRoles())
        {
            roleServices.deleteRoleByIdAndEnterpriseId(role.getId(), enterpriseId);
        }
        usersRepository.deleteByIdAndEnterpriseId(id, enterpriseId);
    }

    public Result saveUsers(Users user, String isSuperAdmin, Enterprise enterprise){
        Result result = validateModel(user);

        if (!result.isValid()){
            return result;
        }

        if (isSuperAdmin.equals("on")) {
            if (user.getEnterprise() == null){
                result.add("Ou dwe rantre non antrepriz la", "enterprise");
            }

            Role superAdmin = roleServices.findRoleByNameAndEnterpriseId("ROLE_SUPER_ADMIN", user.getEnterprise().getId());
            if (superAdmin == null){
                result.add("Rol super admin nan pa egziste");
                return result;
            }

            user.setRoles(new ArrayList<Role>());
            user.getRoles().add(superAdmin);
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (isSuperAdmin.equals("off")){
            user.setEnterprise(enterpriseService.findEnterpriseByName(enterprise.getName()));
        }
        user.setCreationDate(new Date());
        user.setModificationDate(new Date());
        user.setEnabled(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));


        try {
            usersRepository.save(user);
        }catch (Exception ex){
            result.add("Itilizatè a pa ka anrejistre reeseye ankò");
        }

        return result;
    }

    public Users findUserByUsernameAndEnterpriseId(String username, Long enterpriseId){
        return usersRepository.findByUsernameAndEnterpriseId(username, enterpriseId);
    }

    public Users findUserByUsername(String username){
        return usersRepository.findByUsername(username);
    }


    public List<Users> findAllUsersByEnterpriseId(Long enterpriseId){
        return usersRepository.findAllByEnterpriseId(enterpriseId);
    }

    public List<Users> findAllUsersSuperAdmin(){
        return usersRepository.selectUserSuperAdmin(Roles.ROLE_SUPER_ADMIN.name());
    }

    public List<Users> findAllUsersExceptSuperAdminAndEnterpriseId(Long enterpriseId){
        return usersRepository.selectAllUserExceptSuperAdminAndEnterpriseId(Roles.ROLE_SUPER_ADMIN.name(), Roles.ROLE_SUPER_MEGA_ADMIN.name(), enterpriseId);
    }


    public void deleteUsersById(Long id, Long enterpriseId){
        usersRepository.deleteByIdAndEnterpriseId(id, enterpriseId);
    }

    public Result updateUsers(Users user, String isSuperAdmin, Long enterpriseId){
        Result result = validateModel(user);
        if (!result.isValid()){
            return result;
        }

        Users currentUser = usersRepository.findUsersById(user.getId());
        if(currentUser == null){
            result.add("Itilizatè ak nimewo "+ user.getId() + " pa egziste");
            return  result;
        }

        currentUser.setModificationDate(new Date());

        if (isSuperAdmin.equals("on")) {
            if (user.getEnterprise() == null){
                result.add("Ou dwe rantre non antrepriz la", "enterprise");
            }
        } else {
            List<Role> roles =  roleServices.findAllByEnterpriseId(enterpriseId);
            currentUser.getRoles().clear();
            for (int i = 0; i < user.getRoles().size(); i++){
                for (Role role : roles){
                    if (user.getRoles().get(i).getName().equals(role.getName())) {
                        currentUser.getRoles().add(role);
                        break;
                    }

                }
            }
        }

        currentUser.setName(user.getName());
        if (isSuperAdmin.equals("on")){
            currentUser.setEnterprise(user.getEnterprise());
        }
        currentUser.setUsername(user.getUsername());
        try {
            usersRepository.save(currentUser);
        }catch (Exception ex){
            result.add("Ititilizatè a pa ka aktyalize reeseye ankò");
        }
        return  result;
    }


    public Result deleteUserById(Long id, Long enterpriseId){
        Result result = new Result();
        Users users = usersRepository.findUsersByIdAndEnterpriseId(id, enterpriseId);
        if(users == null) {
            result.add("Ititilizatè sa ou bezwen elimine a pa egziste");
            return result;
        }
        try{
            usersRepository.deleteByIdAndEnterpriseId(id, enterpriseId);
        }catch (Exception ex){
            result.add("Ititilizatè la pa ka elimine reeseye ankò");
        }
        return result;
    }

    public Page<Users> findAllUsersByState(int page, int itemPerPage, Boolean state, Long enterpriseId){
        Pageable pageable = PageRequest.of(page,itemPerPage);
        if(state != null){
            return usersRepository.selectUserExceptSuperAdminAndEnabledAndEnterpriseId(Roles.ROLE_SUPER_ADMIN.name(), Roles.ROLE_SUPER_MEGA_ADMIN.name(),state,enterpriseId, pageable);
        }
        return usersRepository.selectUserExceptSuperAdminAndEnterpriseId(Roles.ROLE_SUPER_ADMIN.name(), Roles.ROLE_SUPER_MEGA_ADMIN.name(),enterpriseId,pageable);
    }

    public Page<Users> findAllUsersByStateSuperAdmin(int page, int itemPerPage, Boolean state){
        Pageable pageable = PageRequest.of(page,itemPerPage);
        if(state != null){
            return usersRepository.selectUserSuperAdminAndEnabled(Roles.ROLE_SUPER_ADMIN.name(), state, pageable);
        }
        return usersRepository.selectUserSuperAdmin(Roles.ROLE_SUPER_ADMIN.name(), pageable);
    }

    public Users findUser(Long id, Long enterpriseId){
        return usersRepository.findUsersByIdAndEnterpriseId(id, enterpriseId);
    }


    public Users findUserById(Long id){
        return usersRepository.findUsersById(id);
    }


    public List<Users> findAllUsersByEnabled(String name, boolean enabled, Long enterpriseId){
        return usersRepository.selectUserByNameAndEnabledAndEnterpriseId(name, enabled, enterpriseId);
    }
}
