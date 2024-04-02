package com.skhu.mid_skhu.entity.interest;

import com.skhu.mid_skhu.entity.user_interest.User_Interest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
public class Interest {

    @JsonIgnore
    @Id
    @Column(name = "INTEREST_ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "INTEREST_NAME", nullable = false)
    private String name;

    @OneToMany(mappedBy = "interest", fetch = FetchType.LAZY)
    private List<User_Interest> students = new ArrayList<>();

    // 관심사 종류가 결정되면 추후에 추가 예정 (maybe enum?)
}
