package com.skhu.mid_skhu.app.repository;

import com.skhu.mid_skhu.app.entity.interest.InterestCategory;
import com.skhu.mid_skhu.app.entity.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByStudentNo(String studentNo);

    Optional<Student> findByStudentNo(String studentNo);

    List<Student> findAllByCategory(InterestCategory category);

    List<Student> findByCategoryIn(Set<InterestCategory> categories);
}
