package com.akqa;

import com.akqa.model.OfficeHours;
import com.akqa.batch.MeetingRequestsCollector;
import com.akqa.batch.factories.MeetingRequestsCollectorFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

public class MeetingRequestsCollectorImplTest {

    MeetingRequestsCollector collector;

    @Before
    public void setUp() throws Exception {
        collector = MeetingRequestsCollectorFactory.newInstance("TestMeetingRequestsCollector");
    }

    @Test
    public void getOfficeHours() throws Exception {

        collector.accept("0900 1730");
        Assert.assertNotNull(collector.getOfficeHours());
        collector.accept("0900 1730");
        Assert.assertNotNull(collector.getOfficeHours());
        Assert.assertEquals(LocalTime.of(9,0), collector.getOfficeHours().getStart());
        Assert.assertEquals(LocalTime.of(17,30), collector.getOfficeHours().getEnd());

    }

    @Test
    public void toTimeInterval() throws Exception {

        String start = "0900";
        String end = "1730";
        OfficeHours interval = collector.toTimeInterval (start, end);
        Assert.assertNotNull(interval);
        Assert.assertTrue(interval.getStart().equals(LocalTime.of(9, 0)));
        Assert.assertTrue(interval.getEnd().equals(LocalTime.of(17, 30)));

    }

    @Test
    public void setMeetingRequest() throws Exception {

        collector.accept("0900 1730");

    }

    @Test
    public void setMeetingDurations() throws Exception {
    }

}