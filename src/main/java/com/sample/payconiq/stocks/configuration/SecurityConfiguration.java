package com.sample.payconiq.stocks.configuration;


import com.sample.payconiq.stocks.filter.AuthenticationRequestFilter;
import com.sample.payconiq.stocks.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationRequestFilter authenticationRequestFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // We don't need CSRF for this example
        http.cors().and().csrf().disable()
                .authorizeRequests().
                 antMatchers(HttpMethod.GET, "/api/stocks/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "/api/stocks").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/stocks/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/stocks/").hasRole("ADMIN")
                .antMatchers("/authenticate").permitAll().anyRequest().authenticated().and().
                exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).
                and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().addFilterBefore(authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
