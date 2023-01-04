package ru.soft.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class JwtSecurityConfiguration {

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorize ->
//                        authorize.requestMatchers("/api/**").hasRole("USER")
//                                .anyRequest().authenticated());
        return buildJwt(http);
    }

    protected SecurityFilterChain buildJwt(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //.oauth2ResourceServer(oauth2 -> oauth2.jwt(
//                        jwt -> jwt.jwtAuthenticationConverter(JwtUtil::createJwtUser))) // https://github.com/spring-projects/spring-security/issues/7834
                .exceptionHandling()
                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                .and()
                .oauth2ResourceServer().jwt();
        return http.build();
    }
}