package com.skhu.mid_skhu.app.service.fcm.alarmInterface;

import com.skhu.mid_skhu.app.entity.student.Student;

import java.util.List;

public interface StudentTodayTodoListCheckService {
    List<Student> getStudentListExistTodayTodoList();
}
