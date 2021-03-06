package com.asoprofarma.internos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asoprofarma.internos.dao.IUsuarioDao;
import com.asoprofarma.internos.entity.Usuario;



@Service
public class IUsuarioService implements UserDetailsService {
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			
		Usuario usuario = usuarioDao.findByUsername(username);
		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role->new SimpleGrantedAuthority(role.getNombre()))
				.collect(Collectors.toList());
				
				
		return new User(usuario.getUsername(),usuario.getPassword(),usuario.getEnabled(), true,true,true,authorities );
	}

}
