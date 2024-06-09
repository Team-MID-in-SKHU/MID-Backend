package com.skhu.mid_skhu.app.repository;

import com.skhu.mid_skhu.app.entity.event.Event;
import com.skhu.mid_skhu.app.entity.interest.InterestCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findByCategoriesIn(List<InterestCategory> categories);

    @Query("SELECT e from Event e join e.categories c where c = :category")
    List<Event> findByCategories(String category);

    List<Event> findByStartAtBetween(LocalDateTime startAt, LocalDateTime endAt);

    List<Event> findByCategoriesInAndStartAtBetween(List<InterestCategory> categories, LocalDateTime startAt, LocalDateTime endAt);

    @Query("SELECT e FROM Event e JOIN e.categories c WHERE c IN :categories AND e.startAt BETWEEN :startAt AND :endAt")
    List<Event> findTodayEventsByCategories(List<InterestCategory> categories, LocalDateTime startAt, LocalDateTime endAt);

    List<Event> findAllByStartAtBetween(LocalDateTime startAt, LocalDateTime endAt);

    @Query("SELECT e FROM Event e WHERE e.startAt < :currentTime AND e.endAt > :currentTime ORDER BY e.startAt ASC")
    List<Event> findOngoingEvents(LocalDateTime currentTime);

    @Query("SELECT e FROM Event e WHERE e.endAt > :currentTime ORDER BY e.endAt ASC")
    List<Event> findEventsWithNearestEndAt(LocalDateTime currentTime);

    List<Event> findByTitleContaining(String partialTitle);
}
