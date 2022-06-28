package ServerTickets.Control.Security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class TicketsAuthProvider implements AuthenticationProvider {
    public static final Logger log = LogManager.getLogger(TicketsAuthProvider.class);
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserDetails userDetails= userDetailsService
                .loadUserByUsername((String) authentication.getPrincipal());
        log.info("TicketsAuthProvider : "+userDetails.getPassword());
        if(userDetails.getPassword().equals(authentication.getCredentials())){
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,
                    userDetails.getAuthorities());
        }
       return null;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
