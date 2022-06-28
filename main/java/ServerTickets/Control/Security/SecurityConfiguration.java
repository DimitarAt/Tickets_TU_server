package ServerTickets.Control.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
   @Autowired
    UserDetailsService userDetailsService;
   @Autowired
   JWTAuthorizationFilter jwtFilter;
    @Autowired
    AuthenticationProvider authenticationProvider;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/tickets/register/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/tickets/login").permitAll()
                .antMatchers("/tickets/admin/**").hasAuthority("ADMIN")
                .antMatchers("/tickets/organizer/**").hasAuthority("ORGANIZER")
                .antMatchers("/tickets/distributor/**").hasAuthority("DISTRIBUTOR")
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic();
        return http.build();
    }

//    public WebSecurityCustomizer webSecurityCustomizer() {
//       return (web) -> web.ignoring().antMatchers("/**");
//   }

}