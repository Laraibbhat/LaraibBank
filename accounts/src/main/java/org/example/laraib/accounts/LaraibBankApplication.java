package org.example.laraib.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Laraib Bank API Documentation",
                version = "1.0",
                description = "API documentation for Laraib Bank application",
                contact = @Contact(
                        name = "Laraib Bank",
                        email = "lmushtaq10@gmail.com",
                        url= "https://www.kashify.shop"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @io.swagger.v3.oas.annotations.servers.Server(
                        url = "http://localhost:8080",
                        description = "Local server"
                )
        },
        externalDocs = @ExternalDocumentation(
                description = "Laraib Bank API Documentation",
                url = "https://www.kashify.shop"
        )
)
public class LaraibBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaraibBankApplication.class, args);
    }

}
