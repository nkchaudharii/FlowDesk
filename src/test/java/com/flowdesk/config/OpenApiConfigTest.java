package com.flowdesk.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigTest {

    @Test
    void shouldExposeBearerSecuritySchemeAndGlobalRequirement() {
        OpenApiConfig config = new OpenApiConfig();

        OpenAPI openAPI = config.flowDeskOpenApi();

        assertThat(openAPI.getComponents()).isNotNull();
        assertThat(openAPI.getComponents().getSecuritySchemes()).containsKey("bearerAuth");

        SecurityScheme securityScheme = openAPI.getComponents().getSecuritySchemes().get("bearerAuth");
        assertThat(securityScheme).isNotNull();
        assertThat(securityScheme.getType()).isEqualTo(SecurityScheme.Type.HTTP);
        assertThat(securityScheme.getScheme()).isEqualTo("bearer");
        assertThat(securityScheme.getBearerFormat()).isEqualTo("JWT");

        assertThat(openAPI.getSecurity()).isNotEmpty();
        assertThat(openAPI.getSecurity().get(0)).containsKey("bearerAuth");
    }
}
