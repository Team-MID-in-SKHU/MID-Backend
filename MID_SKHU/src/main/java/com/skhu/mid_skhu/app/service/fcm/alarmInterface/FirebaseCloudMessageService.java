package com.skhu.mid_skhu.app.service.fcm.alarmInterface;


import java.io.IOException;

public interface FirebaseCloudMessageService {
    void sendMessageTo(String targetToken, String title, String body) throws IOException;
}

