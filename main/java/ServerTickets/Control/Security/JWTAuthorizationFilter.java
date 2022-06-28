package ServerTickets.Control.Security;


import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    public static final Logger log = LogManager.getLogger(JWTAuthorizationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            log.info(request.getMethod()+"    "+request.getServletPath());
            if (checkJWTToken(request)) {

                Claims claims = validateToken(request);
                if (claims.get(SecurityGlobalVar.ClaimName) != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            }else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());

        }

    }

    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request
                .getHeader(SecurityGlobalVar.HeaderName)
                .replace(SecurityGlobalVar.JwtPrefix, "");
        log.info("token extracted ");
        return Jwts
                .parserBuilder()
                .setSigningKey(SecurityGlobalVar.secretKey.getBytes())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List) claims.get(SecurityGlobalVar.ClaimName);
        for (String s:authorities
             ) {System.out.println(s);

        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        log.info("client is authenticated: "+auth.isAuthenticated());

    }

    private boolean checkJWTToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(SecurityGlobalVar.HeaderName);
        if (authenticationHeader == null || !authenticationHeader.startsWith(SecurityGlobalVar.JwtPrefix))
        {log.info("token NOT found");
            return false;}
        log.info("token found");
        return true;
    }
}
