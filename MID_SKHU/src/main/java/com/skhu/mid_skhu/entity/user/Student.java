package com.skhu.mid_skhu.entity.user;

import com.skhu.mid_skhu.entity.user_interest.User_Interest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Student {

    @JsonIgnore
    @Id
    @Column(name = "STUDENT_ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "STUDENT_NO", nullable = false)
    private String studentNo;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "STUDENT_NAME", length = 20, nullable = false)
    private String name;

    @JsonIgnore
    @Column(name = "PHONE_NUMBER", length = 128, nullable = false)
    private String phoneNumber;

    @Column(name = "ROLE_TYPE", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<User_Interest> interests = new ArrayList<>();
}