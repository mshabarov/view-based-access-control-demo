package com.vaadin.tutorial.crm.backend.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import com.vaadin.tutorial.crm.ui.views.crm.LoginView;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration
        extends VaadinWebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Delegating the responsibility of general configurations
        // of http security to the super class. It is configuring
        // the followings: Vaadin's CSRF protection by ignoring
        // framework's internal requests, default request cache,
        // ignoring public views annotated with @AnonymousAllowed,
        // restricting access to other views/endpoints, and enabling
        // ViewAccessChecker authorization.
        // You can add any possible extra configurations of your own
        // here (the following is just an example):

        // http.rememberMe().alwaysRemember(false);

        super.configure(http);

        // This is important to register your login view to the
        // view access checker mechanism:
        setLoginView(http, LoginView.class);
    }

    /**
     * Allows access to static resources, bypassing Spring security.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Configure your static resources with public access here:
        web.ignoring().antMatchers(
                "/images/**"
        );

        // Delegating the ignoring configuration for Vaadin's
        // related static resources to the super class:
        super.configure(web);
    }

    /**
     * Demo UserDetailService which only provide two hardcoded
     * in memory users and their roles.
     * NOTE: This should not be used in real world applications.
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        Collection<UserDetails> userDetails = userService.getUsers().stream().map(user ->
                User.withUsername(user.getUserName())
                        .password(user.getPassword())
                        .roles(user.getRoles().toArray(new String[0]))
                        .build()).collect(Collectors.toList());
        return new InMemoryUserDetailsManager(userDetails);
    }
}
