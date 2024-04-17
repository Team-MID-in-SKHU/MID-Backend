package com.skhu.mid_skhu.repository;

import com.skhu.mid_skhu.entity.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByStudentNo(String studentNo);

    Student findByStudentNo(String studentNo);
}
