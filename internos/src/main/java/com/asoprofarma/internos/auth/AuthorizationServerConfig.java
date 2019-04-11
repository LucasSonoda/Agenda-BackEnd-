package com.asoprofarma.internos.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired //AutoCasteamos el Encriptador de SpringSecurityConfig
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired //AutoCasteamos el AuthenticacionManager de SpringSecurityConfig
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@Override 
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()") //Indicamos que todos pueden pedirnos un token
		.checkTokenAccess("isAuthenticated()"); //Indicamos que los usuarios authenticados pueden checkear el token
	}

	@Override //Aca se registan los clientes osea el FRONT-END
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		clients.inMemory().withClient("internosApp")
		.secret(passwordEncoder.encode("alaska"))
		.scopes("read","write")
		.authorizedGrantTypes("password","refresh_token")
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(3600);
		
	}

	@Override /// Proceso de autenticacion, genera el token. Se valida tambien el token del
				/// usuario. Osea se encarga de todo lo que tiene que ver con el token
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
				.tokenStore(tokenStore())  //Opcional
				.accessTokenConverter(accessTokenConverter());
	}

	@Bean //ES opcional ya que por debajo SpringSecurity lo genera, pero aca es un ejemplo de como utilizarlo
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean //Esta es la llave que van a tener puestos nuestros tokens y poder ser validados.
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey("internosApp.BackEnd.De.Agenda");
		return jwtAccessTokenConverter;
	}
	
	
}
