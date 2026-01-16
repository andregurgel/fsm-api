package dev.andregurgel.fsm_api.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenService {
    private Map<String, Object> getAdditionalInfo() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = authentication.getToken();
        return jwt.getClaims();
    }

    public Object getTokenField(String claim) {
        return getAdditionalInfo().get(claim);
    }

    public Long getUserId() {
        return Long.parseLong(getTokenField("user_id").toString());
    }
}
