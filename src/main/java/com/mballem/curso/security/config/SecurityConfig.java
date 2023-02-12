package com.mballem.curso.security.config;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
                    // CONFIGURANDO SPRING NA VERSÃO ANTIGA
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //LIBERANDO PAGINA HOME
                .antMatchers("/", "/home").permitAll()
                //LIBERANDO RECURSOS DE APARENCIA
                .antMatchers("/webjars/**", "/css/**", "/image/**","/js/**").permitAll()




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
}
