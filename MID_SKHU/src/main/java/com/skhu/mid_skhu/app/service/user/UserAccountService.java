package com.skhu.mid_skhu.app.service.user;

import com.skhu.mid_skhu.app.entity.student.Student;
import com.skhu.mid_skhu.app.repository.StudentRefreshTokenRepository;
import com.skhu.mid_skhu.app.repository.StudentRepository;
import com.skhu.mid_skhu.global.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccountService {

    private final TokenProvider tokenProvider;
    private final HttpServletRequest request;
    private final StudentRepository studentRepository;
    private final StudentRefreshTokenRepository studentRefreshTokenRepository;

    @Transactional
    public void logout() {
        String token = tokenProvider.resolveToken(request);
        if (token != null) {
            tokenProvider.invalidateToken(token);
        }
        SecurityContextHolder.clearContext();
    }

    @Transactional
    public void deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        Long userId;
        if (principal instanceof UserDetails) {
            userId = Long.parseLong(((UserDetails) principal).getUsername());
        } else {
            userId = Long.parseLong(principal.toString());
        }

        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        studentRefreshTokenRepository.deleteByStudent(student);
        studentRepository.deleteById(userId);
        logout();
    }
}
