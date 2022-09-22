package com.gft.gateway.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.gft.gateway.exception.JwtTokenMalformedException;
import com.gft.gateway.exception.JwtTokenMissingException;
import com.gft.gateway.util.JwtUtil;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
		// String atributte = request.getHeaders().get("Authorization").get(0);

		final List<String> apiEndpoints = List.of("/register", "/login", "refreshtoken");
		final Map<String, List<String>> nutritionistEndPoints = new HashMap<>();
		nutritionistEndPoints.put("POST", List.of("/diets", "/diets-groups", "/nutritionist", "payment"));
		nutritionistEndPoints.put("GET",
				List.of("/patient", "/nutritionist/all", "/diets-groups/all", "/diets/all",
						"/payment/all"));
		nutritionistEndPoints.put("PUT", List.of("/nutritionist", "/diets", "/diets-groups"));
		nutritionistEndPoints.put("DELETE",
				List.of("/nutritionist", "/payment", "/diets", "/diets-groups", "/patient"));

		Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));
		Predicate<ServerHttpRequest> isNutritionistPostSecured = r -> nutritionistEndPoints.get("POST").stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));
		Predicate<ServerHttpRequest> isNutritionistGetSecured = r -> nutritionistEndPoints.get("GET").stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));
		Predicate<ServerHttpRequest> isNutritionistPutSecured = r -> nutritionistEndPoints.get("PUT").stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));
		Predicate<ServerHttpRequest> isNutritionistDeleteSecured = r -> nutritionistEndPoints.get("DELETE").stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));

		if (isApiSecured.test(request)) {
			if (!request.getHeaders().containsKey("Authorization")) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);

				return response.setComplete();
			}

			if (!request.getHeaders().getOrEmpty("Authorization").get(0).startsWith("Bearer ")) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				return response.setComplete();
			}

			final String token = request.getHeaders().getOrEmpty("Authorization").get(0).replace("Bearer ", "");

			try {
				jwtUtil.validateToken(token);
			} catch (JwtTokenMalformedException | JwtTokenMissingException e) {
				// e.printStackTrace();

				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.BAD_REQUEST);

				return response.setComplete();
			}

			Claims claims = jwtUtil.getClaims(token);

			if (!isNutritionistPostSecured.test(request) && claims.containsValue("PATIENT")
					&& request.getMethodValue().equals("POST")) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				return response.setComplete();
			}

			if (!isNutritionistGetSecured.test(request) && claims.containsValue("PATIENT")
					&& request.getMethodValue().equals("GET")) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				return response.setComplete();
			}

			if (!isNutritionistPutSecured.test(request) && claims.containsValue("PATIENT")
					&& request.getMethodValue().equals("PUT")) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				return response.setComplete();
			}

			if (!isNutritionistDeleteSecured.test(request) && claims.containsValue("PATIENT")
					&& request.getMethodValue().equals("DELETE")) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				return response.setComplete();
			}

		}

		return chain.filter(exchange);
	}

}
