package com.akqa.batch.factories;

import com.akqa.batch.MeetingScheduler;
import com.akqa.batch.impl.MeetingSchedulerImpl;

public class MeetingSchedulerFactory {

    public static MeetingScheduler newInstance(String fileReader) {

        if (fileReader.equals("TestMeetingScheduler")) {
            return new MeetingSchedulerImpl();
        }

        return null;
    }
}
