package com.di.twitter.analytics.query.client.config;

import com.di.twitter.analytics.app.config.UserConfigData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserConfigData userConfigData;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception  {
        httpSecurity.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/**").hasRole("USER")
                .anyRequest()
                .fullyAuthenticated();
    }

}
