package com.skhu.mid_skhu.entity.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Event {

    @JsonIgnore
    @Id
    @Column(name = "EVENT_ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EVENT_TITLE", nullable = false)
    private String title;

    @Column(name = "EVENT_DESCRIPTION", nullable = false)
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd - HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startAt;

    @JsonFormat(pattern = "yyyy-MM-dd - HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endAt;

    // 일정 장소에 대한 열거형 타입으로 관리?

    // 관심사의 종류와 설계가 정해진다면 -> interestId 해당 일정이 속한 관심사의 식별자를 참조할 수 있도록해야함
}
