package paginaAspe.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import paginaAspe.Security.AuthFilter;
import paginaAspe.Security.JwtUtil;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${security.jwt.secret}")
    private String jwtSecret;
    @Value("${security.jwt.expiration-hours}")
    private long jwtHours;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(jwtSecret, jwtHours);
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter(JwtUtil jwt) {
        FilterRegistrationBean<AuthFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new AuthFilter(jwt));
        reg.addUrlPatterns("/api/*");
        reg.setOrder(1);
        return reg;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*").allowedMethods("*").allowedHeaders("*")
                .exposedHeaders("Content-Disposition");
    }
}
