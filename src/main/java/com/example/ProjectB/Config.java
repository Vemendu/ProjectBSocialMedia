package com.example.ProjectB;

import com.example.ProjectB.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class Config extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService ()
    {
        return new ClientService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .authorizeRequests()
                .antMatchers("/clients").authenticated()
                .antMatchers("/update").authenticated()
                .antMatchers("/update_admin").authenticated()
                .antMatchers("/delete_process").authenticated()
                .antMatchers("/view_finances").authenticated()
                .antMatchers("/add_finances").authenticated()
                .anyRequest().permitAll()
                .and().formLogin().usernameParameter("username").defaultSuccessUrl("/clients").permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
    }
    /*public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
    }
    */
}
