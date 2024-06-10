package com.skhu.mid_skhu.app.entity.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skhu.mid_skhu.app.dto.event.requestDto.EventUpdateRequestDto;
import com.skhu.mid_skhu.app.entity.interest.InterestCategory;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
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
import java.util.List;
import java.util.stream.Collectors;

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

    @Column(name = "EVENT_LOCATION", nullable = false)
    private String eventLocation;

    @Column(name = "EVENT_IMAGES")
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> imageUrls;

    @JsonFormat(pattern = "yyyy-MM-dd - HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startAt;

    @JsonFormat(pattern = "yyyy-MM-dd - HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endAt;

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "INTEREST_CATEGORY", nullable = false)
    private List<InterestCategory> categories;

    @Column(name = "USER_ID")
    private Long userId;

    public void updateEventDetails(EventUpdateRequestDto requestDto) {
        title = requestDto.getTitle();
        description = requestDto.getDescription();
        eventLocation = requestDto.getEventLocation();
        startAt = requestDto.getStartAt();
        endAt = requestDto.getEndAt();
        categories = requestDto.getInterestCategories().stream()
                .map(InterestCategory::convertToCategory)
                .collect(Collectors.toList());
    }
}
