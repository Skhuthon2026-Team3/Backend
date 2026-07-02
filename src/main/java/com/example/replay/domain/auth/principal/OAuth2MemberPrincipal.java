package com.example.replay.domain.auth.principal;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuth2MemberPrincipal implements OAuth2User {

    private final Long memberId;
    private final String email;
    private final String nameAttributeKey;
    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;

    public OAuth2MemberPrincipal(
            Long memberId,
            String email,
            String nameAttributeKey,
            Map<String, Object> attributes,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.memberId = memberId;
        this.email = email;
        this.nameAttributeKey = nameAttributeKey;
        this.attributes = attributes;
        this.authorities = authorities;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        Object name = attributes.get(nameAttributeKey);
        return name == null ? String.valueOf(memberId) : String.valueOf(name);
    }
}
