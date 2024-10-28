package com.project.config;

import static org.springframework.security.config.Customizer.withDefaults;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import com.project.exceptionhandling.CustomBasicAuthenticationEntryPoint;

@Configuration
@Profile("!prod")
public class ProjectSecurityConfig {
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		// http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
		// http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
		
		http.sessionManagement(smc -> smc
				//session fixation attack protection
				//.sessionFixation(sfc->sfc.newSession())
				//redirect user to a different page after session timeout
				.invalidSessionUrl("/invalidSession").maximumSessions(3).maxSessionsPreventsLogin(true))
		//only HTTP traffic
		.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())
		//disable CSRF
		.csrf(csrfConfig -> csrfConfig.disable());
		
		http.authorizeHttpRequests(
				(requests) -> requests.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards")
						.authenticated().requestMatchers("/notices", "/contact", "/error","/register","/invalidSession").permitAll());
		http.formLogin(withDefaults());
		//http.httpBasic(withDefaults());
		http.httpBasic(hbc->hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
		// GLOBAL CONFIG
				// http.exceptionHandling(ehc-> ehc.authenticationEntryPoint(new
				// CustomBasicAuthenticationEntryPoint()));
		//http.exceptionHandling(ehc-> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()).accessDeniedPage("/denied"));
		return http.build();
	}

	/*@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		return new JdbcUserDetailsManager(dataSource);
	}*/

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}
}
