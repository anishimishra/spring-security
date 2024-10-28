package com.project.config;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
public class EasyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	EasyBankUsernamePwdAuthenticationProvider(UserDetailsService theUserDetailsService,
			PasswordEncoder thePasswordEncoder) {
		userDetailsService = theUserDetailsService;
		passwordEncoder = thePasswordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		//if(passwordEncoder.matches(pwd, userDetails.getPassword())) {
			// fetch the age details and perform validations to check if age>18
			return new UsernamePasswordAuthenticationToken(username, pwd,userDetails.getAuthorities());
	//	} else {
		//	throw new BadCredentialsException("Invalid password!");
		//}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
