package com.akqa.model;

import java.time.LocalTime;

public final class OfficeHours implements PrintableAsJson {

    private final LocalTime start;
    private final LocalTime end;

    public OfficeHours(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

}
