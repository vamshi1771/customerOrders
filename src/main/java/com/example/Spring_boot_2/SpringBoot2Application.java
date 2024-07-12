package com.example.Spring_boot_2;

import com.example.Spring_boot_2.controllers.CustomerController;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBoot2Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(SpringBoot2Application.class, args);

		CustomerController controller = context.getBean(CustomerController.class);
	}

}

