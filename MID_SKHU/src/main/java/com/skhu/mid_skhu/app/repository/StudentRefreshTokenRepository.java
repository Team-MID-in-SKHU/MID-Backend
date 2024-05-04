package com.skhu.mid_skhu.app.repository;

import com.skhu.mid_skhu.app.entity.student.Student;
import com.skhu.mid_skhu.app.entity.student.StudentRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRefreshTokenRepository extends JpaRepository<StudentRefreshToken, Long> {
    Optional<StudentRefreshToken> findByStudent_UserId(Long userId);
    void deleteByStudent(Student student);


}
