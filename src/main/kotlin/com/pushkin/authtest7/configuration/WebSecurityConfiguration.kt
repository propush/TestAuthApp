package com.pushkin.authtest7.configuration

import com.pushkin.authtest7.security.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    @Autowired
    private val pwdUserAuthenticationProvider: PwdUserAuthenticationProvider,
    @Autowired
    private val jwtDecoder: JWTDecoder,
    @Autowired
    private val tokenHelperService: TokenHelperService
) : WebSecurityConfigurerAdapter() {

    @Bean(name = [BeanIds.AUTHENTICATION_MANAGER])
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.authenticationProvider(pwdUserAuthenticationProvider)
    }

    override fun configure(http: HttpSecurity?) {
        http!!
            .cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/swagger-ui.html/**", "/webjars/**", "/v2/**", "/swagger-resources/**", "/actuator/**")
            .hasRole("SWAGGER").and().httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers("/login", "/ws/**", "/signup/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(
                JWTAuthenticationFilter(jwtDecoder, authenticationManagerBean(), tokenHelperService),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilter(JWTAuthorizationFilter(jwtDecoder, authenticationManagerBean()))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

}
