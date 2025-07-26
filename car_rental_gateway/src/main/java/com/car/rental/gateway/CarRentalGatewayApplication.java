package com.car.rental.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class CarRentalGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalGatewayApplication.class, args);
	}

	@Bean
	public KeyResolver keyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()) ;
	}
}
