package com.skhu.mid_skhu.app.service.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccountService {

    @Transactional
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
