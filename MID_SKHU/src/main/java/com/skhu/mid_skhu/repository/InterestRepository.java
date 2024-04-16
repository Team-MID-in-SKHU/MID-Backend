package com.skhu.mid_skhu.repository;

import com.skhu.mid_skhu.entity.interest.Interest;
import com.skhu.mid_skhu.entity.interest.InterestCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {

    Optional<Interest> findByCategory(InterestCategory category);
}
