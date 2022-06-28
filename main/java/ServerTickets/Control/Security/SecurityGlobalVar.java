package ServerTickets.Control.Security;

import java.text.SimpleDateFormat;

public class SecurityGlobalVar {
    public final static String secretKey="SecretTicketsKeySecretTicketsKey";
    public final static String JwtId="TicketsApp";
    public final static String JwtPrefix="Bearer";
    public final static String ClaimName="authorities";
    public final static String HeaderName="Authorization";
    public final static Long tokenExpireTime=(long) 900000;
    public final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh-mm");
}
