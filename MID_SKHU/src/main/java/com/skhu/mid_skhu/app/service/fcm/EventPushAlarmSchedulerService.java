package com.skhu.mid_skhu.app.service.fcm;

import com.skhu.mid_skhu.app.dto.user.responseDto.UserTodoListResponseDto;
import com.skhu.mid_skhu.app.dto.user.responseDto.UserTodoListWrapperResponseDto;
import com.skhu.mid_skhu.app.entity.event.Event;
import com.skhu.mid_skhu.app.entity.interest.InterestCategory;
import com.skhu.mid_skhu.app.entity.student.Student;
import com.skhu.mid_skhu.app.repository.EventRepository;
import com.skhu.mid_skhu.app.repository.StudentRepository;
import com.skhu.mid_skhu.app.service.todo.UserTodayTodoListCheckService;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EventPushAlarmSchedulerService {

    private final StudentRepository studentRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final UserTodayTodoListCheckService userTodayTodoListCheckService;

    @Scheduled(cron = "0 0 1 * * *")
    public void sendEventPushAlarm() throws IOException {
        List<Student> students = studentRepository.findAll();

        for (Student student : students) {
            Principal principal = () -> String.valueOf(student.getUserId());
            ApiResponseTemplate<UserTodoListWrapperResponseDto> response  = userTodayTodoListCheckService.checkTodayTodoList(principal);

            if (response.isSuccess() && response.getData() != null) {
                List<UserTodoListResponseDto> eventList = response.getData().getResponseDto();

                for (UserTodoListResponseDto eventDto : eventList) {
                    LocalDateTime eventStartAt = eventDto.getStartAt();
                    LocalDateTime threeHoursBefore = eventStartAt.minusHours(3);
                    LocalDateTime now = LocalDateTime.now();

                    if (now.isAfter(threeHoursBefore) && now.isBefore(eventStartAt)) {
                        String fcmToken = student.getFcmToken();

                        if (fcmToken != null) {
                            String title = "오늘 하루 일정 알림";
                            String body = "[" + eventDto.getEventTitle() + "]" + " 일정이 3시간 뒤에 관심사에 맞는 일정이 있어요!";

                            firebaseCloudMessageService.sendMessageTo(fcmToken, title, body);
                        }
                    }
                }
            }
        }
    }
}
