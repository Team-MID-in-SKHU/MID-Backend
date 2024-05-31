package com.skhu.mid_skhu.app.service.fcm;

import com.skhu.mid_skhu.app.entity.event.Event;
import com.skhu.mid_skhu.app.entity.interest.InterestCategory;
import com.skhu.mid_skhu.app.entity.student.Student;
import com.skhu.mid_skhu.app.repository.EventRepository;
import com.skhu.mid_skhu.app.repository.StudentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentTodayTodoListIsExitService {

    private final EventRepository eventRepository;
    private final StudentRepository studentRepository;

    public List<Student> getStudentListExistTodayTodoList() {

        LocalDateTime todayAt = LocalDateTime.now().toLocalDate().atStartOfDay();
        List<Event> todayEvents = eventRepository.findAllByStartAtBetween(todayAt, todayAt.plusDays(1).minusNanos(1));

        Set<InterestCategory> todayInterestCategorySet = new HashSet<>();
        todayEvents.forEach(event -> todayInterestCategorySet.addAll(event.getCategories()));

        return studentRepository.findByCategoryIn(todayInterestCategorySet);
    }
}
