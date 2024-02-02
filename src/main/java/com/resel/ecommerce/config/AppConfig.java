package com.resel.ecommerce.config;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.cors(cors -> {
                    cors.configurationSource(new CorsConfigurationSource() {
                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                            CorsConfiguration cfg = new CorsConfiguration();
                            cfg.setAllowedOriginPatterns(Collections.singletonList("http://127.0.0.1:5500"));
                            cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                            cfg.setAllowCredentials(true);
                            cfg.setAllowedHeaders(Collections.singletonList("*"));
                            cfg.setExposedHeaders(Arrays.asList("Authorization"));
                            return cfg;
                        }
                    });
                }).authorizeHttpRequests(auth ->{
                    auth
                            .requestMatchers(HttpMethod.POST,"http://localhost:5454/api/admin/products/create").permitAll()
                            .requestMatchers(HttpMethod.DELETE,"http://localhost:5454/api/admin/products/{productId}/delete").permitAll()
                            .requestMatchers(HttpMethod.PUT,"http://localhost:5454/api/admin/products/{productId}/update").permitAll()
                            .requestMatchers(HttpMethod.POST,"http://localhost:5454/auth/signin").permitAll()
                            .requestMatchers(HttpMethod.GET,"http://localhost:5454/api/users/profile").permitAll()
                            .requestMatchers(HttpMethod.GET,"http://localhost:5454/api/cartItem/byUser/{userId}").permitAll()


                            .requestMatchers(HttpMethod.POST,"/auth/signup").permitAll()
                            .requestMatchers(HttpMethod.POST,"/auth/signin").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/users/profile").permitAll()
                            .requestMatchers(HttpMethod.POST,"/api/admin/products/create").permitAll()
                            .requestMatchers(HttpMethod.DELETE,"/api/admin/products/{productId}/delete").permitAll()
                            .requestMatchers(HttpMethod.PUT,"/api/admin/products/{productId}/update").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/cartItem/byUser/{userId}").permitAll()



                            .requestMatchers(HttpMethod.GET,"/api/admin/products/all").permitAll()
                            .requestMatchers(HttpMethod.GET,"api/products/id/{productId}").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/cart/getCart").permitAll()
                            .requestMatchers(HttpMethod.PUT,"/api/cart/add").permitAll()
                            .requestMatchers(HttpMethod.POST,"/otp/send-otp").permitAll()
                            .requestMatchers(HttpMethod.POST,"/otp/validate-otp").permitAll()


                            .requestMatchers(HttpMethod.POST,"/api/order/create").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/order/{id}").permitAll()

                            .requestMatchers(HttpMethod.POST,"/api/cart/*").permitAll()
//                            .requestMatchers(HttpMethod.GET, "/customers","/hello").hasRole("ADMIN")
//                            .requestMatchers(HttpMethod.GET, "/customers/**").hasAnyRole("ADMIN","USER")
//                            .requestMatchers("/swagger-ui*/**","/v3/api-docs/**").permitAll()
                            .anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.disable())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();

    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
