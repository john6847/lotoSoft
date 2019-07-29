package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.Role;
import com.b.r.loteriab.r.Model.TenantContext;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Repository.RoleRepository;
import com.b.r.loteriab.r.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by Dany on 22/04/2019.
 */


@Service
public class SecurityServices implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * Creando el usuario por defecto y su rol.
     */
    public void crearUsuarioAdmin(){
        System.out.println("Creación del usuario y rol en la base de datos");
        Users userAdmin = userRepository.findByUsername("bradmin");
        if (userAdmin == null){

            Role rolAdmin = roleRepository.findByName("ROLE_ADMIN");// Todo: Delete ROLE
            Role rolSuperMegaAdmin = roleRepository.findByName("ROLE_SUPER_MEGA_ADMIN");
            Users admin = new Users();
            admin.setUsername("bradmin");
            admin.setName("Administratè");
            admin.setPassword(bCryptPasswordEncoder.encode("bradmin")); // Todo: Change password
            admin.setEnabled(true);
            admin.setCreationDate(new Date());
            admin.setModificationDate(new Date());

            admin.setRoles(new ArrayList<>(Arrays.asList(rolAdmin, rolSuperMegaAdmin)));
            userRepository.save(admin);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] usernameAndEnterprise= StringUtils.split(
                username, String.valueOf(Character.LINE_SEPARATOR));
        if (usernameAndEnterprise == null || usernameAndEnterprise.length != 2) {
            throw new UsernameNotFoundException("Username and macAddress must be provided");
        }
        Users user = userRepository.findByUsernameAndEnterpriseName(usernameAndEnterprise[0], usernameAndEnterprise[1]);
        if (user == null) {
            throw new UsernameNotFoundException(
                        String.format("Itilizatè sa pa egziste pou antrepriz sa, itilizatè=%s, antrepriz=%s",
                                usernameAndEnterprise[0], usernameAndEnterprise[1]));
        }

        TenantContext tenantContext = new TenantContext(usernameAndEnterprise[0],user.getEnterprise().getId());
        new Thread(tenantContext).start();

        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, grantedAuthorities);
    }
}