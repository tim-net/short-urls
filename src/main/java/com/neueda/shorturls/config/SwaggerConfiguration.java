package com.neueda.shorturls.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Profile(value = {"swagger"})
@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements WebMvcConfigurer {
    @Value("${springfox.documentation.swagger.ui.enabled:false}")
    private boolean swaggerUiEnabled;
    @Value("${springfox.documentation.swagger.ui.path:}")
    private String swaggerUiPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        if (swaggerUiEnabled) {
            if (StringUtils.isNotBlank(swaggerUiPath)) {
                registry.addViewController(swaggerUiPath + "/v2/api-docs")
                        .setViewName("forward:/v2/api-docs");
                registry.addViewController(swaggerUiPath + "/swagger-resources/configuration/ui")
                        .setViewName("forward:/swagger-resources/configuration/ui");
                registry.addViewController(swaggerUiPath + "/swagger-resources/configuration/security")
                        .setViewName("forward:/swagger-resources/configuration/security");
                registry.addViewController(swaggerUiPath + "/swagger-resources")
                        .setViewName("forward:/swagger-resources");
            }
        }
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (swaggerUiEnabled) {
            registry.addResourceHandler(swaggerUiPath + "/swagger-ui.html**")
                    .addResourceLocations("classpath:/META-INF/resources/");

            registry.addResourceHandler(swaggerUiPath + "/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build()
                .apiInfo(apiInfo());
    }

    @SuppressWarnings("Guava")
    private Predicate<String> paths() {
        return Predicates.not(PathSelectors.regex("/error"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Short url REST API",
                "",
                "0.0.1",
                "",
                new Contact(
                        "Timofei Netisov",
                        "",
                        "netisov@gmail.com"
                ),
                "",
                "",
                Collections.emptyList()
        );
    }
}
