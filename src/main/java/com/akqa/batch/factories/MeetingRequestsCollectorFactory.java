package com.akqa.batch.factories;

import com.akqa.batch.MeetingRequestsCollector;
import com.akqa.batch.impl.MeetingRequestsCollectorImpl;

public class MeetingRequestsCollectorFactory {

    public static MeetingRequestsCollector newInstance(String fileReader) {

        if (fileReader.equals("TestMeetingRequestsCollector")) {
            return new MeetingRequestsCollectorImpl();
        }

        return null;
    }
}
