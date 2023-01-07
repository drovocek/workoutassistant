package ru.soft.common.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Getter
public class JwtUser extends JwtAuthenticationToken {

    private final UUID id;
    private final Set<String> roles;

    public JwtUser(Jwt jwt, Set<String> roles, UUID id) {
        super(jwt, getGrantedAuthorities(roles));
        this.id = id;
        this.roles = roles;
    }

    private static Collection<? extends GrantedAuthority> getGrantedAuthorities(Set<String> roles) {
        return roles.stream().map(r -> (GrantedAuthority) () -> "ROLE_" + r.toUpperCase()).toList();
    }

    public String getEmail() {
        return getName();
    }

    @Override
    public String toString() {
        return "JwtUser:" + id + '[' + getName() + ']';
    }
}