package com.bootcoin.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceBootcoinTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceBootcoinTransactionApplication.class, args);
	}

}
