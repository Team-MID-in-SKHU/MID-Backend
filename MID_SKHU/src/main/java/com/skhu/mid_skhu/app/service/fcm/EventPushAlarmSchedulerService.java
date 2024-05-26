package com.skhu.mid_skhu.app.service.fcm;

import com.skhu.mid_skhu.app.entity.event.Event;
import com.skhu.mid_skhu.app.entity.interest.InterestCategory;
import com.skhu.mid_skhu.app.entity.student.Student;
import com.skhu.mid_skhu.app.repository.EventRepository;
import com.skhu.mid_skhu.app.repository.StudentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EventPushAlarmSchedulerService {

    private final EventRepository eventRepository;
    private final StudentRepository studentRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @Scheduled(cron = "0 0 1 * * *")
    public void sendEventPushAlarm() throws IOException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeHoursLater = now.plusHours(3);

        List<Event> events = eventRepository.findByStartAtBetween(now, threeHoursLater);

        for (Event event : events) {
            List<InterestCategory> eventCategories = event.getCategories();
            List<Student> students = studentRepository.findAll();

            for (Student student : students) {
                List<InterestCategory> studentCategories = student.getCategory();

                boolean isMatchInterested = studentCategories.stream()
                        .anyMatch(eventCategories::contains);

                if (isMatchInterested) {
                    String fcmToken = student.getFcmToken();

                    if (fcmToken != null) {
                        String title = "일정 알림";
                        String body = event.getTitle() + "일정이 3시간 뒤에 있어요! 일정을 확인해주세요";

                        firebaseCloudMessageService.sendMessageTo(fcmToken, title, body);
                    }
                }
            }
        }
    }
}
