package com.me.pe.currencyexchange;

import com.me.pe.currencyexchange.security.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class CurrencyexchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyexchangeApplication.class, args);
	}

	/**
	 * WebSecurityConfig allow you to specify the access settings for published resources.
	 * In this case, allow the call to the controller /user, but for the rest it requires authentication.
	 * */
	@EnableWebSecurity
	@Configuration
	static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/user").permitAll()
					.antMatchers("/h2-console/**/**").permitAll()
					.anyRequest()
					.authenticated();

			// its require for h2-console
			http.headers().frameOptions().disable();
		}
	}

}
