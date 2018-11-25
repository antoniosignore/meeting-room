package com.akqa.batch.impl;

import com.akqa.model.MeetingRequest;
import com.akqa.model.OfficeHours;
import com.akqa.model.User;
import com.akqa.batch.MeetingRequestsCollector;
import com.akqa.batch.MeetingScheduler;
import com.akqa.utils.NaturalNumberChecker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Observable;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;

public class MeetingRequestsCollectorImpl extends Observable implements MeetingRequestsCollector {

    static final Logger logger = Logger.getLogger(MeetingRequestsCollectorImpl.class);

    private OfficeHours officeHours;
    private Set<MeetingRequest> meetingRequests = new TreeSet<>();

    private LocalDateTime requestDate;
    private User user;
    private LocalDate meetingDate;
    private LocalTime meetingTime;
    private Integer duration;

    public MeetingRequestsCollectorImpl() {

        // needed for Jackson json
    }

    @Override
    public void accept(String line) {

        String[] tokens = getTokens(line);
        if (tokens.length == 2)
            officeHours = setOfficeHours(tokens);

        else if (tokens.length == 3)

            if (ifParamIsNumber(tokens[2])) {

                processMeetingRequestLine(tokens);

            } else {

                processSecondInputLine(tokens);

                MeetingRequest request =
                        new MeetingRequest.MeetingRequestBuilder()
                                .setRequestDate(requestDate)
                                .setDay(meetingDate)
                                .setStartTime(meetingTime)
                                .setDuration(duration)
                                .setUser(user)
                                .build();
                if (meetingDate != null && duration != null) {
                    LocalTime endTime = meetingTime.plusHours(duration);

                    if (isValid(request) && isRange(endTime)) {
                        meetingRequests.add(request);
                        setChanged();
                    }
                }

            }
    }

    private boolean isValid(MeetingRequest request) {

        return (request.getUser() != null) &&
                (request.getDay() != null) &&
                (request.getRequestDate() != null) &&
                (request.getDuration() != null) &&
                (request.getStartTime() != null);
    }

    private void processSecondInputLine(String[] tokens) {
        try {
            InnerDateTime innerDateTime = new InnerDateTime(tokens).invoke();
            meetingDate = innerDateTime.getLocalDate();
            meetingTime = innerDateTime.getLocalTime();
            duration = Integer.parseInt(tokens[2]);
        } catch (Exception e) {
            logger.error("e.getMessage() = " + e.getMessage());
        }
    }

    private void processMeetingRequestLine(String[] tokens) {
        try {
            DateTimeFormatter format = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern("yyyy-MM-dd HH:mm:ss")
                    .toFormatter();

            requestDate = LocalDateTime.parse(tokens[0] + " " + tokens[1], format);
            user = new User(tokens[2]);
        } catch (Exception e) {
            logger.error("e.getMessage() = " + e.getMessage());
        }
    }

    private boolean ifParamIsNumber(String token) {
        return !isNumber(token);
    }

    private boolean isRange(LocalTime newTime) {
        return newTime.isAfter(officeHours.getStart()) &&
                newTime.isBefore(officeHours.getEnd());
    }

    @Override
    public OfficeHours toTimeInterval(String start, String end) {
        LocalTime startTime = LocalTime.of(Integer.parseInt(start.substring(0, 2)),
                Integer.parseInt(start.substring(2, 4)));
        LocalTime endTime = LocalTime.of(Integer.parseInt(end.substring(0, 2)),
                Integer.parseInt(end.substring(2, 4)));
        return new OfficeHours(startTime, endTime);
    }

    @Override
    public Set<MeetingRequest> getMeetingRequests() {
        return meetingRequests;
    }

    private String[] getTokens(String line) {
        return line.split(" ");
    }

    private boolean isNumber(String input) {
        return NaturalNumberChecker.isNaturalNumber(input);
    }

    private OfficeHours setOfficeHours(String[] split) {
        return toTimeInterval(split[0], split[1]);
    }

    @Override
    public void combine(MeetingRequestsCollector other) {
        //no parallel processing
    }

    @Override
    public OfficeHours getOfficeHours() {
        return officeHours;
    }

    @Override
    public void addObserver(MeetingScheduler scheduler) {
        super.addObserver(scheduler);
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

    private class InnerDateTime {
        private String[] tokens;
        private LocalDate localDate;
        private LocalTime localTime;

        InnerDateTime(String... tokens) {
            this.tokens = tokens;
        }

        LocalDate getLocalDate() {
            return localDate;
        }

        LocalTime getLocalTime() {
            return localTime;
        }

        InnerDateTime invoke() {
            localDate = LocalDate.parse(tokens[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            localTime = LocalTime.of(
                    Integer.parseInt(tokens[1].substring(0, 2)),
                    Integer.parseInt(tokens[1].substring(3, 5)));
            return this;
        }
    }
}