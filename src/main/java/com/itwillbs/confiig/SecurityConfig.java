package com.itwillbs.confiig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final MyUserDetailsService myUserDetailsService;
	
	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
//		BCryptPasswordEncoder : 스프링 시큐리티에서 제공하는 클래스 중 하나
//        비밀번호를 암호와하는 데 사용할 수 있는 메서드 가진 클래스
//		BCrypt 해싱 함수를 사용해서 비밀번호를 인코딩해주는 메서드 제공
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http ) throws  Exception {
		String[] urlsToBePermittedAll = {
				"/",
                "/login/**",
                "/logout/**",
                "/insert/**",
                "/boardList/**",
                "/content/**",
                "/css/**",
                "/js/**"
        };
		String[] urlsToBeHasUser = {
				"/info/**",
				"/update/**",
				"/main/**",
				"/delete/**",
				"/boardUpdate/**",
				"/boardDelete/**",
				"/boardWrite/**",
				
		};
		String[] urlsToBeHasAdmin = {
				"/list/**",
				"/admin/**",
				
		};
		http.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers(urlsToBePermittedAll).permitAll()
				.requestMatchers(urlsToBeHasUser).hasAnyRole("USER","ADMIN")
				.requestMatchers(urlsToBeHasAdmin).hasRole("ADMIN")
				.anyRequest().authenticated()
				);
		http.formLogin(form -> form
				.loginPage("/login")
				.loginProcessingUrl("/loginPro")
				.usernameParameter("id")
				.passwordParameter("pass")
				.defaultSuccessUrl("/main")
				.failureUrl("/login")
				)
		.logout(logoutCustomizer
				-> logoutCustomizer
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/")
				)
				.userDetailsService(myUserDetailsService);
		
		return http.build();
	}//filterChain
	
}
