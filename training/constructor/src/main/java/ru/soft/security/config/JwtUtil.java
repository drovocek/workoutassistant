package ru.soft.security.config;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

@Slf4j
@UtilityClass
public class JwtUtil {

    public JwtUser createJwtUser(Jwt jwt) {
        log.debug("create JwtUser for '{}'", jwt.getSubject());
        Set<String> roles;
        @SuppressWarnings("unchecked")
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
        if (realmAccess == null || realmAccess.isEmpty()) {
            roles = Collections.emptySet();
        } else {
            @SuppressWarnings("unchecked")
            List<String> rolesList = (List<String>) realmAccess.get("roles");
            roles = new HashSet<>(rolesList);
        }
        JwtUser jwtUser = new JwtUser(jwt, roles, null);
        System.out.println("!!!!!!!!!");
        System.out.println(jwtUser.getRoles());
        log.info(jwtUser.toString());
        return jwtUser;
    }
}
