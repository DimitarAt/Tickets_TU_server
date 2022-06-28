package ServerTickets.enums.EnumsToDBConverters;

import ServerTickets.enums.Roles;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RolesToDBConverter implements AttributeConverter<Roles,String> {

    @Override
    public String convertToDatabaseColumn(Roles roles) {

        return roles!=null ? (String) roles.getRoleCode() : null;
    }

    @Override
    public Roles convertToEntityAttribute(String roleCode) {

        for (Roles role: Roles.values())
        {
            if (role.equals(roleCode))return role;
        }
        return null;
    }
}
