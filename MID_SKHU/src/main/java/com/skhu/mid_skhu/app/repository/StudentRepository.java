package com.skhu.mid_skhu.app.repository;

import com.skhu.mid_skhu.app.entity.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByStudentNo(String studentNo);

    Optional<Student> findByStudentNo(String studentNo);
}
