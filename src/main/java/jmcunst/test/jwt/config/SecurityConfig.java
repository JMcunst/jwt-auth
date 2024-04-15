package jmcunst.test.jwt.config;

import jmcunst.test.jwt.common.properties.CorsProperties;
import jmcunst.test.jwt.entity.RoleType;
import jmcunst.test.jwt.token.JwtAccessDeniedHandler;
import jmcunst.test.jwt.token.JwtAuthenticationEntryPoint;
import jmcunst.test.jwt.token.JwtFilter;
import jmcunst.test.jwt.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CorsProperties corsProperties;

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private final String[] allowedServerUrls = {"/", "/swagger-ui/**", "/v3/**", "/access-test", "/welcome", "/health-check"};
    private final String[] allowedUserUrls = {"/api/v1/auth/login", "/api/v1/user/signup", "/api/v1/auth/token", };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers(allowedServerUrls).permitAll()
                        .requestMatchers(allowedUserUrls).permitAll()
                        .requestMatchers("/api/**").hasAnyAuthority(RoleType.USER.getCode())
                        .requestMatchers("/api/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
                        .anyRequest().authenticated()
                ).exceptionHandling(
                        (exceptionHandling) ->
                                exceptionHandling
                                        .accessDeniedHandler(jwtAccessDeniedHandler)
                                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                ).addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}
