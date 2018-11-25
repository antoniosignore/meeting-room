package com.akqa;

import com.akqa.model.MeetingCalendar;
import com.akqa.batch.FileReader;
import com.akqa.batch.MeetingRequestsCollector;
import com.akqa.batch.MeetingScheduler;

import java.io.*;

public class CLI {

    private PrintStream out;
    private BufferedReader in;

    private FileReader fileReader;
    private MeetingScheduler scheduler;

    public CLI(PrintStream out,
               InputStream in,
               FileReader meetingFileReader,
               MeetingScheduler scheduler) {
        this.out = out;
        this.in = new BufferedReader(new InputStreamReader(in));
        this.fileReader = meetingFileReader;
        this.scheduler = scheduler;
    }

    void start() {

        try {
            out.print("Enter meetings reservation filename : ");
            final String filename = in.readLine();

            MeetingRequestsCollector collector = fileReader.process(filename.trim());

            collector.addObserver(scheduler);
            collector.notifyObservers();

            MeetingCalendar calendar = scheduler.getCalendar();

            calendar.print(out);

        } catch (IOException e) {
            out.println("An error occurred. Please start over.");
        }
    }
}
