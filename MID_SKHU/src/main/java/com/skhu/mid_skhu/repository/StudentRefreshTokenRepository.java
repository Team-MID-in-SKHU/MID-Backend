package com.skhu.mid_skhu.repository;

import com.skhu.mid_skhu.entity.student.Student;
import com.skhu.mid_skhu.entity.student.StudentRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRefreshTokenRepository extends JpaRepository<StudentRefreshToken, Long> {
    Optional<StudentRefreshToken> findByStudent_UserId(Long userId);
    void deleteByStudent(Student student);


}
