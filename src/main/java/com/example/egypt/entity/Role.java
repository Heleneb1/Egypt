package com.example.egypt.entity;

public enum Role {
    ADMIN,
    USER
}
//TODO l'utiliser quand spring-security sera installé
//package com.example.egypt.entity;
//
//        import org.springframework.security.core.GrantedAuthority;
//
//public enum Role implements GrantedAuthority {
//    ADMIN,
//    USER;
//
//    @Override
//    public String getAuthority() {
//        return this.name();
//    }
//}
