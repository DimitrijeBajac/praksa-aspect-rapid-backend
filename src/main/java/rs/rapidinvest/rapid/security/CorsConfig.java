package rs.rapidinvest.rapid.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500") // Dozvoljava pristup samo sa ove adrese
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Dozvoljava određene HTTP metode
                .allowCredentials(true); // Omogućava korišćenje kredencijala (npr. kolačića)
    }
}

