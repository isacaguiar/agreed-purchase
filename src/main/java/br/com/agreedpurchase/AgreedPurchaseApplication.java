package br.com.agreedpurchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
public class AgreedPurchaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgreedPurchaseApplication.class, args);
	}

}
