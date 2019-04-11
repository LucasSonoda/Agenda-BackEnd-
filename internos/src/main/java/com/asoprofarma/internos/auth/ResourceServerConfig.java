package com.asoprofarma.internos.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	// Esta clase es donde centralizamos nuestros recursos y damos o quitamos
	// acceso, igual en cada clase propiamente dicha vamos
	// a ir tambien agregando los accesos de manera
	// manual. Desde aca es global
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/grupo/all").permitAll().anyRequest().authenticated();
	}

}
