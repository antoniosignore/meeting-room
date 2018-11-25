package com.akqa.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class Meeting implements
        Comparable<Meeting>,Overlap<Meeting>,PrintableAsJson {

    private final LocalDateTime requestDate;
    private final LocalDate startDate;
    private final LocalTime startTime;
    private final int duration;
    private final User user;

    public Meeting(LocalDateTime requestDate, LocalDate startDate, LocalTime time, int duration, User user) {
        this.requestDate = requestDate;
        this.startDate = startDate;
        this.startTime = time;
        this.duration = duration;
        this.user = user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public User getUser() {
        return user;
    }

    @Override
    public int compareTo(Meeting meeting) {
        return this.startTime.compareTo(meeting.startTime);
    }

    @Override
    public boolean overlapWith(Meeting o) {
        LocalTime start1 = this.startTime;
        LocalTime end1 = this.startTime.plusHours(duration);
        LocalTime start2 = o.startTime;
        LocalTime end2 = o.startTime.plusHours(o.duration);
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
}
