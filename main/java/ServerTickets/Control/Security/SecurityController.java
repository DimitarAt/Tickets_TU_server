package ServerTickets.Control.Security;

import ServerTickets.DAO.CommunicationsDAOImpl;
import ServerTickets.DAO.CustomerDAOImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
//@RequestMapping(value = "localhost:8080")
public class SecurityController {
    @Autowired
    AuthenticationProvider provider;
    @Autowired
    CustomerDAOImpl customerService;

    public static final Logger log = LogManager.getLogger(SecurityController.class);



    @GetMapping(value="/login" )

    public String login(HttpServletRequest request, HttpServletResponse response) {
        log.info("login method called");
        String name=request.getHeader("name");
        String password=request.getHeader("password");
        UsernamePasswordAuthenticationToken token=
                new UsernamePasswordAuthenticationToken(name,password);
        Authentication auth= provider.authenticate(token);
        log.info("the token is: "+getJWTToken(auth));
        String a=(new CommunicationsDAOImpl()).UnreceivedMessages(name);


        return ((new CommunicationsDAOImpl()).UnreceivedMessages(name)+","+getJWTToken(auth));
    }



    private String getJWTToken( Authentication auth) {
        String secretKey = SecurityGlobalVar.secretKey;
       SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
       String name= auth.getName();

        String token = Jwts
                .builder()
                .setId(SecurityGlobalVar.JwtId)
                .setSubject(name)
                .claim(SecurityGlobalVar.ClaimName,AuthorityUtils.authorityListToSet(auth.getAuthorities()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityGlobalVar.tokenExpireTime))
                .signWith(key,SignatureAlgorithm.HS256).compact();
        log.info("new jwt token created");
        customerService.UpdateToken(name,token);
        return SecurityGlobalVar.JwtPrefix+ token;
    }




   }

