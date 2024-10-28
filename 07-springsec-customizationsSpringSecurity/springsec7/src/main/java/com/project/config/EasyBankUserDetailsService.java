package com.project.config;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.model.Customer;
import com.project.repository.CustomerRepository;

@Service
public class EasyBankUserDetailsService implements UserDetailsService {
	private final CustomerRepository customerRespository;

	EasyBankUserDetailsService(CustomerRepository theCustomerRespository) {
		customerRespository = theCustomerRespository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Customer customer = customerRespository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Details not found for the user " + username));
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
		return new User(customer.getEmail(), customer.getPwd(), authorities);
	}
}
