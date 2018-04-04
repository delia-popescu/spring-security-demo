
package com.training.springmvc.demospringmvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            	.antMatchers("/index.html").permitAll()
            	.antMatchers("/persons/**").hasRole("ADMIN")
            	.and()
            .httpBasic()
            .and()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/persons")
            .and()
            .exceptionHandling().accessDeniedPage("/login")
//            .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
            .and()
            .logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password("{noop}admin").roles("ADMIN")
            .and()
            .withUser("user").password("{noop}password").roles("USER");
    }

    @Bean
    public CustomAccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
