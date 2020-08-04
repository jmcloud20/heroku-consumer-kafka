package com.pccw.cloud.consumerapp;

import com.pccw.cloud.consumerapp.service.heroku.kafka.DefaultConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConsumerAppApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(ConsumerAppApplication.class, args);
	}
}
