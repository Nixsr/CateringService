package it.uniroma3.siw.cateringService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={
//DataSourceAutoConfiguration.class,
//DataSourceTransactionManagerAutoConfiguration.class
//})
public class CateringServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CateringServiceApplication.class, args);
	}

}
