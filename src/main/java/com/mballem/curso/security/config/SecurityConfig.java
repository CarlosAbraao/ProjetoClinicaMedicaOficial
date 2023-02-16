package com.mballem.curso.security.config;


import com.mballem.curso.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // CRIPTOGRAFANDO A SENHA DO USUARIO
    @Autowired
    private UsuarioService service;



                    // CONFIGURANDO SPRING NA VERSÃO ANTIGA
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //LIBERANDO PAGINA HOME
                .antMatchers("/", "/home").permitAll()
                //LIBERANDO RECURSOS DE APARENCIA
                .antMatchers("/webjars/**", "/css/**", "/image/**","/js/**").permitAll()


                // ACESSOS PRIVADOS ADMIN
                .antMatchers("/u/**").hasAuthority("ADMIN")



                //ACESSOS PRIVADOR MEDICOS
                .antMatchers("/medicos/**").hasAuthority("MEDICOS")



                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                // SE A PAGINA LOGIN TIVER SUCESSO DIRECIONA PARA A PAGINA ABAIXO
                .defaultSuccessUrl("/", true)
                // SE FALHAR O LOGIN
                .failureUrl("/login-error").permitAll()
                // QUANDO FIZER LOGOUT DIRECIONA PARA RAIZ
                .and()
                .logout()
                .logoutSuccessUrl("/");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
    }
}
