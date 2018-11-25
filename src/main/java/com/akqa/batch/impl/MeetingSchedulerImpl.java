package com.akqa.batch.impl;

import com.akqa.model.Meeting;
import com.akqa.model.MeetingCalendar;
import com.akqa.batch.MeetingRequestsCollector;
import com.akqa.batch.MeetingScheduler;

import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;

public class MeetingSchedulerImpl extends Observable implements Observer, MeetingScheduler {

    MeetingCalendar calendar = new MeetingCalendar();

    public MeetingSchedulerImpl() {
        // needed by Jackson
    }

    @Override
    public MeetingCalendar getCalendar() {
        return calendar;
    }

    @Override
    public void update(Observable collector, Object arg) {
        MeetingRequestsCollector collector1 = ((MeetingRequestsCollector) collector);

        processMeetingRequestsFromCollector(collector1);
    }

    @Override
    public void processMeetingRequestsFromCollector(MeetingRequestsCollector collector) {

        if (collector != null)
            collector.getMeetingRequests().forEach(meetingRequest -> {
                LocalDate meetingDay = meetingRequest.getDay();

                Meeting meeting =
                        new Meeting(meetingRequest.getRequestDate(),
                                meetingRequest.getDay(),
                                meetingRequest.getStartTime(),
                                meetingRequest.getDuration(),
                                meetingRequest.getUser());

                calendar.addMeetingToCalendar(meetingDay, meeting);

                // notify User observing the scheduler
                hasChanged();
                notifyObservers();
            });
    }


}
