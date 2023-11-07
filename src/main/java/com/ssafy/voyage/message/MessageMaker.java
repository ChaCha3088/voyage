package com.ssafy.voyage.message;

import org.springframework.stereotype.Component;

@Component
public class MessageMaker {
    private final static StringBuilder sb = new StringBuilder();
    private final static MessageMaker messageMaker = new MessageMaker();

    public static MessageMaker getMessageMaker() {
        return messageMaker;
    }

    @Override
    public String toString() {
        String result = sb.toString();

        // StringBuilder 초기화
        sb.setLength(0);

        return result;
    }

    public MessageMaker add(String string) {
        this.sb.append(string);
        return this;
    }
}
