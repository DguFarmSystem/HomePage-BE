package org.farmsystem.homepage.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${deployment.frontend.production}")
    private String frontendProduction;

    @Value("${deployment.frontend.test}")
    private String frontendTest;

    @Value("${deployment.backend.production}")
    private String backendProduction;

    @Value("${deployment.backend.test}")
    private String backendTest;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);

        // CORS 허용할 URL
        List<String> allowedOrigins = Arrays.asList(
                "http://localhost:5173",
                frontendProduction,
                frontendTest,
                backendProduction,
                backendTest
        );
        config.setAllowedOrigins(allowedOrigins);

        config.addAllowedHeader("*");
        config.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
