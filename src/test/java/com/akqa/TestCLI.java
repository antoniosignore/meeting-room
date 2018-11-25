package com.akqa;


import com.akqa.model.MeetingCalendar;
import com.akqa.batch.FileReader;
import com.akqa.batch.MeetingRequestsCollector;
import com.akqa.batch.MeetingScheduler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCLI {

    @Mock
    private PrintStream out;

    @Mock
    private FileReader fileReader;

    @Mock
    private MeetingRequestsCollector collector;

    @Mock
    private MeetingScheduler scheduler;

    @Mock
    private MeetingCalendar calendar;

    private InputStream in;

    @Before
    public void init()  {
        List<String> t = new ArrayList<>();
        try {
            when(fileReader.process(anyString())).thenReturn(collector);
        } catch (IOException e) {
            e.printStackTrace();
        }

        when(scheduler.getCalendar()).thenReturn(calendar);
    }

    private ByteArrayInputStream inputString(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    @Test
    public void shouldReadTheFile() throws Exception {

        in = inputString(("meetings.txt\n").getBytes());

        CLI client = new CLI(out, in, fileReader, scheduler);
        client.start();

        InOrder inOrder = Mockito.inOrder(out, fileReader);
        inOrder.verify(out).print("Enter meetings reservation filename : ");
        inOrder.verify(fileReader).process("meetings.txt");
        inOrder.verifyNoMoreInteractions();
    }

}
