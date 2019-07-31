package com.b.r.loteriab.r.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by Dany on 22/04/2019.
 */
@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ConfigurationSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAccessDeniedHandler  accessDeniedHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Configuration by JPA
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }
    /*
     * .Let you configure the security rules
     * @param http
     * @throws Exception
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .addFilterBefore(authenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/","/home","/css/**", "/js/**").permitAll() //permitiendo llamadas a esas urls.
                .antMatchers("/dbconsole/**").permitAll()
                .antMatchers("/draw/**","/combinationType/**", "/seller/**", "/pos/**","/configuration").hasAnyRole("ADMIN", "SUPER_ADMIN")
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/**").access("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_SUPER_MEGA_ADMIN')")
                .antMatchers("/", "/login", "/logout").permitAll()
                .and()
                .formLogin()
                    .loginPage("/login") //indicando la ruta que estaremos utilizando.
//                    .successHandler(customAuthenticationSuccessHandler)
//                    .failureUrl("/login?error") //en caso de fallar puedo indicar otra pagina.
                    .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/login");


        http.headers().frameOptions().disable();
    }


    public SimpleAuthenticationFilter authenticationFilter() throws Exception {
        SimpleAuthenticationFilter filter = new SimpleAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationFailureHandler(failureHandler());
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        return filter;
    }

    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login?error");
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers( "/my-ws/**", "/deadRouteBR/**","/assets/**","/fonts/**", "/css/**","/bootstrap-toggle/**", "/js/**", "/img/**", "/dist/**", "/mdb/**");

    }


























}
