package telran.ashkelon2020.security.configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityAuthorizationConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/account/register");

	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.httpBasic();
		httpSecurity.csrf().disable();
		httpSecurity.authorizeRequests()
			.antMatchers(HttpMethod.GET).permitAll()
			.antMatchers(HttpMethod.POST,"/forum/posts/**").permitAll()
			.antMatchers("/account/user/{login}/role/{role}**")
				.hasRole("ADMINISTRATOR")
			.antMatchers("/account/login**", "/forum/post/{id}/like**")
				.access("@customSecurity.checkExpDate(authentication.name)")
			.antMatchers("/account/login**", "/forum/post/{id}/like**")
				.hasAnyRole("ADMINISTRATOR", "MODERATOR","USER")
			.antMatchers(HttpMethod.DELETE,"/user/{login}")
				.access("#login == authentication.name")
			.antMatchers(HttpMethod.PUT,"/forum/post/{id}**")
				.access("@customSecurity.checkPostAuthority(#id, authentication.name) or hasRole('MODERATOR')")
			.antMatchers("/account/password**").authenticated()
			.anyRequest().authenticated();

			
	}
}
