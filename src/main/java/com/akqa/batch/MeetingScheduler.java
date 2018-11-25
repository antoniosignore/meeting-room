package com.akqa.batch;

import com.akqa.model.MeetingCalendar;

import java.util.Observer;

public interface MeetingScheduler extends Observer {

    MeetingCalendar getCalendar();

    void processMeetingRequestsFromCollector(MeetingRequestsCollector collector);
}
