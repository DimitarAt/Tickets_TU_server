package ServerTickets.Control.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class Role implements GrantedAuthority {


    private String role;

    public Role(){};
    public Role(String role) {
        this.role = role;
    }
    @Override
    public String getAuthority() {
        return role;
    }

    public String getName() {
        return role;
    }

    public Collection<? extends  GrantedAuthority> GetRoles(String[] roles){
        Collection<GrantedAuthority> list=new ArrayList<>();
        for (String auth :roles ) {
            list.add(new Role(auth));
        }
        return (Collection<? extends GrantedAuthority>) list;
    }
}
