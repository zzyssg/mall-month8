package com.zzy.mall.common.config;

import com.zzy.mall.common.domain.SwaggerProperty;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: mall
 * @description: swagger基础配置类，便于其他模块继承
 * @author: zzy
 * @create: 2024-07-20
 */
public abstract class SwaggerBaseConfig {

    @Bean
    public Docket createDocket(){
        SwaggerProperty swaggerProperties = swaggerProperties();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(swaggerProperties))
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getPackageUrl()))
                .paths(PathSelectors.any())
                .build();
        if (swaggerProperties.isEnableAuth()){
            docket.securitySchemes(securitySchemes()).securityContexts(securityContexts());
        }
        return docket;

    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> res = new ArrayList<>();
        res.add(getContextByPath("/*/.*"));
        return res;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();

    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> res = new ArrayList<>();
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
        authorizationScopes[0] = authorizationScope;
        SecurityReference reference =  new SecurityReference("Authorization",authorizationScopes);
        res.add(reference);
        return res;

    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> res = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization","Authorization","header");
        res.add(apiKey);
        return res;
    }

    private ApiInfo apiInfo(SwaggerProperty swaggerProperties) {
        return  new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .contact(new Contact(swaggerProperties.getName(), swaggerProperties.getAddress(), swaggerProperties.getEmail()))
                .build();
    }

    public abstract SwaggerProperty swaggerProperties();

    public BeanPostProcessor generateBeanPostProcessor(){
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }

}