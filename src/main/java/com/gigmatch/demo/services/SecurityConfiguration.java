package com.gigmatch.demo.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsLoader usersLoader;

    //Constructor        //Dependency Injection by Spring, it's wiring up for us
    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }


    @Bean  //Go out and get a passwordEncoder method and hashing BCrypt Passwords
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override  //CONFIGURATION METHOD: Authorization use this userLoader as your UserDetailService and use the password Encoder as your password encoder
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usersLoader) // How to find users by their username
                .passwordEncoder(passwordEncoder()) // How to encode and verify passwords
        ;
    }

    @Override
    //Using HTTP security OBJECT that's coming in saying on form
    // login the login page at /login where do you want them to go
    // if they successfully login this is ads for DH, this is properly configured to ads.
    //permit all important
    //and
    //logout send user to logout page with logout appended to the query string
    //and authorize request we require the following be authorized  / and /ads and permit all
    //in this instance if you see this then permit all?
    //last directive... these are the pages and authorize requests antMatchers if they try to
    //go to create or edit you have to make sure they are authenticated
    protected void configure(HttpSecurity http) throws Exception {
        http
                /* Login configuration */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/feed") // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login page
                /* Logout configuration */
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout") // append a query string value
                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeRequests()
                .antMatchers("/", "/welcomePage") // anyone can see the home and the ads pages
                .permitAll()
                /* Pages that require authentication */
                .and()
//                .authorizeRequests()
//                .antMatchers(
//                        "/feed/create", // only authenticated users can create ads
//                        "/feed/{id}/edit"// only authenticated users can edit ads
//                )
//                .authenticated()
        ;
    }
}