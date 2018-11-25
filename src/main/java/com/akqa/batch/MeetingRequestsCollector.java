package com.akqa.batch;

import com.akqa.model.MeetingRequest;
import com.akqa.model.OfficeHours;

import java.util.Set;

public interface MeetingRequestsCollector {

    void accept(String line);

    OfficeHours toTimeInterval(String start, String end);

    Set<MeetingRequest> getMeetingRequests();

    void combine(MeetingRequestsCollector other);

    OfficeHours getOfficeHours();

    void addObserver(MeetingScheduler scheduler);

    void notifyObservers();
}
