package com.akqa;

import com.akqa.model.MeetingRequest;
import com.akqa.batch.MeetingRequestsCollector;
import com.akqa.batch.factories.FileReaderFactory;
import com.akqa.batch.FileReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class FileReaderTest {

    FileReader reader;

    @Before
    public void setUp() throws Exception {
        reader = FileReaderFactory.newInstance("StreamFileReader");
    }

    @Test
    public void shouldDetectEmptyFileAndNoMeetingRequestsGenerated() throws
            Exception {

        MeetingRequestsCollector collector = reader.process("src/test/resources/meetings-empty.txt");
        Assert.assertNotNull(collector);

        Set<MeetingRequest> meetingRequests = collector.getMeetingRequests();
        Assert.assertEquals(0, meetingRequests.size());
    }

    @Test
    public void shouldSkipEmptyLines() throws
            Exception {

        MeetingRequestsCollector collector = reader.process("src/test/resources/meetings-blanklines.txt");
        Assert.assertNotNull(collector);

        Set<MeetingRequest> meetingRequests = collector.getMeetingRequests();
        Assert.assertEquals(4, meetingRequests.size());
    }

    @Test
    public void shouldDetectBadDateFormatAndDropTheMeetingRequest() throws
            Exception {

        MeetingRequestsCollector collector = reader.process("src/test/resources/meetings-baddateformat.txt");
        Assert.assertNotNull(collector);

        Set<MeetingRequest> meetingRequests = collector.getMeetingRequests();
        Assert.assertEquals(3, meetingRequests.size());

        final boolean[] isEmp01present = {false};
        meetingRequests.forEach(meetingRequest -> {

            if (meetingRequest.getUser().getName().equals("EMP001"))
                isEmp01present[0] = true;
        });

        Assert.assertFalse(isEmp01present[0]);
    }

    @Test
    public void shouldDetectBadDurationFormatOnSecondLineAndDropTheMeetingRequest() throws
            Exception {

        MeetingRequestsCollector collector = reader.process("src/test/resources/meetings-badduration.txt");
        Assert.assertNotNull(collector);

        Set<MeetingRequest> meetingRequests = collector.getMeetingRequests();
       // Assert.assertEquals(3, meetingRequests.size());

        final boolean[] isEmp01present = {false};
        meetingRequests.forEach(meetingRequest -> {
            if (meetingRequest.getUser().getName().equals("EMP001"))
                isEmp01present[0] = true;
        });

        Assert.assertFalse(isEmp01present[0]);
    }

    @Test
    public void shouldCorrectlyProcessTheInputFile() throws
            Exception {

        MeetingRequestsCollector collector = reader.process("src/test/resources/meetings.txt");
        Assert.assertNotNull(collector);

        Set<MeetingRequest> meetingRequests = collector.getMeetingRequests();

        Assert.assertEquals(4, meetingRequests.size());

        MeetingRequest[] objects = meetingRequests.toArray(new MeetingRequest[meetingRequests.size()]);

        MeetingRequest request1 = objects[0];

        MeetingRequest request2 = objects[1];
        MeetingRequest request3 = objects[2];
        MeetingRequest request4 = objects[3];

        Assert.assertEquals(request1.getDay(), LocalDate.of(2011, 3,22));
        Assert.assertEquals(request1.getUser().getName(), "EMP003");
        Assert.assertEquals(request1.getRequestDate(),
                LocalDateTime.of(2011, 3, 16, 9, 28, 23 ));
        Assert.assertEquals(request1.getDuration(),new Integer(2));

        Assert.assertEquals(request2.getDay(), LocalDate.of(2011, 3,21));
        Assert.assertEquals(request2.getUser().getName(), "EMP002");
        Assert.assertEquals(request2.getRequestDate(),
                LocalDateTime.of(2011, 3, 16, 12, 34, 56 ));
        Assert.assertEquals(request2.getDuration(),new Integer(2));

        Assert.assertEquals(request3.getDay(), LocalDate.of(2011, 3,21));
        Assert.assertEquals(request3.getUser().getName(), "EMP001");
        Assert.assertEquals(request3.getRequestDate(),
                LocalDateTime.of(2011, 3, 17, 10, 17, 06 ));
        Assert.assertEquals(request3.getDuration(),new Integer(2));

        Assert.assertEquals(request4.getDay(), LocalDate.of(2011, 3,22));
        Assert.assertEquals(request4.getUser().getName(), "EMP004");
        Assert.assertEquals(request4.getRequestDate(),
                LocalDateTime.of(2011, 3, 17, 11, 23, 45 ));
        Assert.assertEquals(request4.getDuration(),new Integer(1));
    }



}