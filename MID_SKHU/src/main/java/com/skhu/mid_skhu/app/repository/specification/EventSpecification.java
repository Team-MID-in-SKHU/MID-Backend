package com.skhu.mid_skhu.app.repository.specification;

import com.skhu.mid_skhu.app.entity.event.Event;
import com.skhu.mid_skhu.app.entity.interest.InterestCategory;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventSpecification {

    public static Specification<Event> withDynamicQuery(String eventTitle, LocalDateTime startAt,
                                                        LocalDateTime endAt, List<InterestCategory> categories) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (eventTitle != null && !eventTitle.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + eventTitle + "%"));
            }

            if (startAt != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startAt"), startAt));
            }

            if (endAt != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endAt"), endAt));
            }

            if (categories != null && !categories.isEmpty()) {
                Join<Event, InterestCategory> categoryJoin = root.join("categories");
                predicates.add(categoryJoin.in(categories));
            }

            query.distinct(true);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
