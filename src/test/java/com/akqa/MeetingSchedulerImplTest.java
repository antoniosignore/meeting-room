package com.akqa;

import com.akqa.model.MeetingCalendar;
import com.akqa.batch.FileReader;
import com.akqa.batch.MeetingRequestsCollector;
import com.akqa.batch.MeetingScheduler;
import com.akqa.batch.factories.FileReaderFactory;
import com.akqa.batch.factories.MeetingSchedulerFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class MeetingSchedulerImplTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldScheduleThreeMeetingsOutOfFourRequests() throws IOException {

        FileReader reader = FileReaderFactory.newInstance("StreamFileReader");
        MeetingRequestsCollector collector = reader.process("src/test/resources/meetings.txt");

        Assert.assertNotNull(collector);
        MeetingScheduler scheduler = MeetingSchedulerFactory.newInstance("TestMeetingScheduler");
        Assert.assertNotNull(scheduler);

        collector.addObserver(scheduler);
        collector.notifyObservers();


//        scheduler.processMeetingRequestsFromCollector(collector);

        Assert.assertEquals(3,scheduler.getCalendar().noMeetings());

        MeetingCalendar calendar = scheduler.getCalendar();

    }

}