package com.akqa.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MeetingRequest
        implements
        Comparable<MeetingRequest>,
        Overlap<MeetingRequest>,
        PrintableAsJson {

    private LocalDateTime requestDate;
    private LocalDate day;
    private LocalTime startTime;
    private Integer duration;
    private User user;

    public MeetingRequest() {
        // needed for Jackson
    }

    public MeetingRequest(MeetingRequestBuilder builder) {
        this.requestDate = builder.requestDate;
        this.day = builder.day;
        this.startTime = builder.startTime;
        this.duration = builder.duration;
        this.user = builder.user;
    }

    public LocalDate getDay() {
        return day;
    }

    public Integer getDuration() {
        return duration;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public User getUser() {
        return this.user;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    @Override
    public int compareTo(MeetingRequest o) {
        return this.getRequestDate().compareTo(o.getRequestDate()
        );
    }

    @Override
    public boolean overlapWith(MeetingRequest o) {
        LocalTime start1 = this.startTime;
        LocalTime end1 = this.startTime.plusHours(duration);
        LocalTime start2 = o.startTime;
        LocalTime end2 = o.startTime.plusHours(o.duration);
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    public static class MeetingRequestBuilder {

        private LocalDateTime requestDate;
        private LocalDate day;
        private LocalTime startTime;
        private Integer duration;
        private User user;

        public MeetingRequestBuilder setRequestDate(LocalDateTime requestDate) {
            this.requestDate = requestDate;
            return this;
        }

        public MeetingRequestBuilder setDay(LocalDate day) {
            this.day = day;
            return this;
        }

        public MeetingRequestBuilder setStartTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public MeetingRequestBuilder setDuration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public MeetingRequestBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public MeetingRequest build() {
            return new MeetingRequest(this);
        }


    }
}
