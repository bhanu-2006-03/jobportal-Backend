package com.jobnest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * Single Page Application (SPA) Web Configuration.
 * Ensures that React Router browser history routes (e.g. /jobs, /dashboard, /login)
 * are forwarded to index.html, while preserving /api/** REST endpoints and static files.
 */
@Configuration
public class SpaWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(@NonNull String resourcePath, @NonNull Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);

                        // If the actual static file exists (e.g., .js, .css, .svg), serve it directly
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }

                        // Do not route /api endpoints to index.html
                        if (resourcePath.startsWith("api") || resourcePath.startsWith("api/")) {
                            return null;
                        }

                        // Forward all client-side React routes to index.html
                        return location.createRelative("index.html");
                    }
                });
    }
}
