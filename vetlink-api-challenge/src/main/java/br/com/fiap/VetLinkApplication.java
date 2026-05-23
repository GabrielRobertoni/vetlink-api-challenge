package br.com.fiap;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class VetLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(VetLinkApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("VetLink API")
                        .version("1.0.0")
                        .description("API REST para gerenciamento integrado de saúde veterinária - Clyvo Vet Challenge")
                        .contact(new Contact()
                                .name("Clyvo Vet")
                                .url("https://clyvo.com.br"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
