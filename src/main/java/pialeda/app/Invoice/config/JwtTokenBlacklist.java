package pialeda.app.Invoice.config;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class JwtTokenBlacklist {
    private Set<String> invalidatedTokens = new HashSet<>();

    public void addInvalidatedToken(String token) {
        invalidatedTokens.add(token);
    }

    public boolean isTokenInvalidated(String token) {
        return invalidatedTokens.contains(token);
    }
}
