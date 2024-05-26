package com.skhu.mid_skhu.app.entity.student;

import com.skhu.mid_skhu.app.entity.interest.InterestCategory;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @Column(name = "STUDENT_NO", nullable = false, unique = true)
    @Size(min = 9, max = 9)
    private String studentNo;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "STUDENT_NAME", length = 10, nullable = false)
    private String name;

    @JsonIgnore
    @Column(name = "PHONE_NUMBER", length = 14, nullable = false)
    private String phoneNumber;

    //    @Column(name = "FCM_TOKEN", nullable = false)
    //    private String fcmToken;

    @Column(name = "ROLE_TYPE", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "INTEREST_CATEGORY", nullable = false)
    private List<InterestCategory> category;

    public void updateDetails(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Cascade 통한 엔티티 생명주기 관리 -> student 엔티티가 삭제되면 student에 속한 interest엔티티도 같이 삭제된다.
//    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Interest> interests = new ArrayList<>();

    // @JsonIgnore or @JsonManagedReference 찾아보고 JSON 직렬화 문제 관리해놓기
}