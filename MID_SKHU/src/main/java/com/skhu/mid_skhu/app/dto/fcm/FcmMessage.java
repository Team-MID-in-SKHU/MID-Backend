package com.skhu.mid_skhu.app.dto.fcm;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FcmMessage {

    private boolean validateOnly;
    private Message message;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Message {
        private Notification notification;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }
}
