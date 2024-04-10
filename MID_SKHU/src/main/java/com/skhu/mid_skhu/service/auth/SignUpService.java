package com.skhu.mid_skhu.service.auth;


import com.skhu.mid_skhu.repository.StudentRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpService {

    private final StudentRepository studentRepository;


}
