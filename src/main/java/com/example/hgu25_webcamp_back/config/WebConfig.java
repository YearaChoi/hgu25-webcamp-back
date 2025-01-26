package com.example.hgu25_webcamp_back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // static/files 경로에 대한 매핑 설정
        registry.addResourceHandler("/api/cpost/files/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/src/main/resources/static/files");
    }
}
