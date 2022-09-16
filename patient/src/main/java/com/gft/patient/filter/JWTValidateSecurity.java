package com.gft.patient.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.gft.patient.util.JwtUtil;

public class JWTValidateSecurity extends BasicAuthenticationFilter {

	public static final String HEADER_ATRIBUTTE = "Authorization";
	public static final String HEADER_PREFIX = "Bearer ";

	private final JwtUtil jwtUtil;

	public JWTValidateSecurity(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String atributte = request.getHeader(HEADER_ATRIBUTTE);
		if (atributte == null) {
			chain.doFilter(request, response);
			return;
		}
		if (!atributte.startsWith(HEADER_PREFIX)) {
			chain.doFilter(request, response);
		}

		String token = atributte.replace(HEADER_PREFIX, "");
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
		String claims = jwtUtil.getClaims(token).getSubject();

		if (claims == null) {
			return null;
		}

		return new UsernamePasswordAuthenticationToken(claims, null, new ArrayList<>());
	}

}