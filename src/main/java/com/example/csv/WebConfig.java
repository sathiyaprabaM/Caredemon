	package com.example.csv;
	
	import org.springframework.context.annotation.Configuration;
	import org.springframework.web.servlet.config.annotation.CorsRegistry;
	import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
	@Configuration
	public class WebConfig implements WebMvcConfigurer {
	
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/api/**")
	                .allowedOrigins("http://localhost:3000")  // Allow requests from your frontend
	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Ensure OPTIONS method is allowed
	                .allowedHeaders("*")  // Allow all headers
	                .allowCredentials(true)  // Allow credentials (cookies, etc.)
	                .maxAge(3600);  // Cache preflight requests for 1 hour
	    }
	}
