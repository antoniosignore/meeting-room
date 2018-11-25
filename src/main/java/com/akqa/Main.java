package com.akqa;


import com.akqa.batch.factories.FileReaderFactory;
import com.akqa.batch.MeetingScheduler;
import com.akqa.batch.FileReader;
import com.akqa.batch.factories.MeetingSchedulerFactory;

public class Main {

    public static void main(String[] args) {

        final FileReader fileReader = FileReaderFactory.newInstance("StreamFileReader");
        final MeetingScheduler scheduler = MeetingSchedulerFactory.newInstance("TestMeetingScheduler");

        new CLI(System.out, System.in, fileReader, scheduler)
                .start();
    }
}
