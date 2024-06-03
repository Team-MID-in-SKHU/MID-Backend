package com.skhu.mid_skhu.app.service.user;

import com.skhu.mid_skhu.global.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccountService {

    private final TokenProvider tokenProvider;
    private final HttpServletRequest request;

    @Transactional
    public void logout() {
        String token = tokenProvider.resolveToken(request);
        if (token != null) {
            tokenProvider.invalidateToken(token);
        }
        SecurityContextHolder.clearContext();
    }
}
