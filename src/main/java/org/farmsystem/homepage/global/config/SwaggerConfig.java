package org.farmsystem.homepage.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;

@Configuration
@Profile({"local", "dev"})
public class SwaggerConfig {

    @Value("${api.base-url}")
    private String baseUrl;

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("FarmSystem Homepage API Document")
                .description("FarmSystem Homepage API 명세서입니다.")
                .version("v1.0.0");

        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .in(HEADER)
                .bearerFormat("JWT")
                .scheme("Bearer");

        Components components = new Components().addSecuritySchemes("token", securityScheme);

        Server server = new Server();
        server.setUrl(baseUrl);

        return new OpenAPI()
                .info(info)
                .components(components)
                .addServersItem(server);
    }

    //운영환경 임시토큰 발급 API Swagger 문서에서 제거
    @Bean
    @Profile("prod")
    public GlobalOpenApiCustomizer hidePaths() {
        return openApi -> openApi.getPaths().remove("/api/auth/token/{userId}");
    }
}
