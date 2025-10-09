package com.example.grace.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir:uploads/temoignages}")
    private String temoignageUploadDir;

    @Value("${file.tutoriel-upload-dir:uploads/tutoriels}")
    private String tutorielUploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Permettre l'accès aux fichiers témoignages uploadés via URL
        registry.addResourceHandler("/uploads/temoignages/**")
                .addResourceLocations("file:" + temoignageUploadDir + "/");

        // Permettre l'accès aux vidéos tutoriels uploadées via URL
        registry.addResourceHandler("/uploads/tutoriels/**")
                .addResourceLocations("file:" + tutorielUploadDir + "/");
    }
}