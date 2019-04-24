package com.asoprofarma.internos.filter;

import java.io.IOException;
import java.util.Map;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		Logger logger = LogManager.getLogger("com.asoprofarma.internos.restcontroller");
		
		String authorization = request.getHeader("authorization");
		//if (authorization != null && (request.getMethod().equalsIgnoreCase("POST")||request.getMethod().equalsIgnoreCase("PUT")||request.getMethod().equalsIgnoreCase("DELETE"))) {
			String token = authorization.split(" ")[1];

			System.out.println(token);
			String valid[] = token.split("\\.");
			if (valid.length > 1) {
				String tokenHeader = token.split("\\.")[0];
				String tokenBody = token.split("\\.")[1];
				String tokenSignature = token.split("\\.")[2];

				Base64 base64 = new Base64();
				base64.decode(tokenBody);
				String body = new String(base64.decode(tokenBody));
				ObjectMapper mapper = new ObjectMapper();
				Map<String,Object> jsonRequest = mapper.readValue(body, new TypeReference<Map<String,Object>>() {
				});
				logger.info("** "+jsonRequest.get("user_name")+" **");
		
			}
			
		
			
		//}
		System.out.println("!!!!!!UserFilter");
		filterChain.doFilter(request, response);
	}

}
