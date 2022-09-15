package com.gft.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gft.gateway.filter.JwtAuthenticationFilter;

@Configuration
public class GatewayConfig {

	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("auth-service", r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("lb://AUTH-SERVICE"))
				.route("diet-service", r -> r.path("/diets/**").filters(f -> f.filter(filter)).uri("lb://DIET-SERVICE"))
				.route("diet-service",
						r -> r.path("/translate/**").filters(f -> f.filter(filter)).uri("lb://DIET-SERVICE"))
				.route("nutritionist-service",
						r -> r.path("/nutritionist/**").filters(f -> f.filter(filter)).uri("lb://NUTRITIONIST-SERVICE"))
				.route("patient-service",
						r -> r.path("/patient/**").filters(f -> f.filter(filter)).uri("lb://PATIENT-SERVICE"))
				.route("payment-service",
						r -> r.path("/payment/**").filters(f -> f.filter(filter)).uri("lb://PAYMENT-SERVICE"))
				.route("diets-groups-service",
						r -> r.path("/diets-groups/**").filters(f -> f.filter(filter)).uri("lb://DIETS-GROUPS-SERVICE"))
				.build();
	}
}
