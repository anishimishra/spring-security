package com.eazybytes.eazyschool.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import com.eazybytes.eazyschool.handler.CustomAuthenticationFailureHandler;
import com.eazybytes.eazyschool.handler.CustomAuthenticationSuccessHandler;

@Configuration
public class ProjectSecurityConfig {

	private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
	private final CustomAuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	ProjectSecurityConfig(CustomAuthenticationSuccessHandler theAuthenticationSuccessHandler,
			CustomAuthenticationFailureHandler theAuthenticationFailureHandler) {
		authenticationSuccessHandler = theAuthenticationSuccessHandler;
		authenticationFailureHandler = theAuthenticationFailureHandler;
	}

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		http.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests((requests) -> requests.requestMatchers("/dashboard").authenticated()
						.requestMatchers("/", "/home", "/holidays/**", "/contact", "/saveMsg", "/courses", "/about",
								"/assets/**", "/login/**")
						.permitAll())
				//login
				.formLogin(flc -> flc.loginPage("/login").usernameParameter("userid").passwordParameter("secretPwd")
						.defaultSuccessUrl("/dashboard").failureUrl("/login?error=true")
						.successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler))
				//logout
				.logout(loc -> loc.logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true)
						.clearAuthentication(true).deleteCookies("JSESSIONID"))
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("user").password("{noop}EazyBytes@12345").authorities("read").build();
		UserDetails admin = User.withUsername("admin")
				// EazyBytes@54321
				.password("{bcrypt}$2a$12$88.f6upbBvy0okEa7OfHFuorV29qeK.sVbB9VQ6J6dWM1bW6Qef8m").authorities("admin")
				.build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	/**
	 * From Spring Security 6.3 version
	 *
	 * @return
	 */
	@Bean
	public CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}

}
