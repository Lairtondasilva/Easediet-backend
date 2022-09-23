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
				.route("diet", r -> r.path("/diets/**").filters(f -> f.filter(filter)).uri("lb://DIET"))
				.route("diet",
						r -> r.path("/translate/**").filters(f -> f.filter(filter)).uri("lb://DIET"))
				.route("nutritionist",
						r -> r.path("/nutritionist/**").filters(f -> f.filter(filter)).uri("lb://NUTRITIONIST"))
				.route("patient",
						r -> r.path("/patient/**").filters(f -> f.filter(filter)).uri("lb://PATIENT"))
				.route("patient",
						r -> r.path("/login").filters(f -> f.filter(filter)).uri("lb://PATIENT"))
				.route("payment",
						r -> r.path("/payment/**").filters(f -> f.filter(filter)).uri("lb://PAYMENT"))
				.route("diets-groups",
						r -> r.path("/diets-groups/**").filters(f -> f.filter(filter)).uri("lb://DIETS-GROUPS"))
				.build();
	}
}
