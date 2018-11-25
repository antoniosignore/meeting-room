package com.akqa.model;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *  The Calendar is implemented as a matrix TreeSet of TreeSet
 *  The rows have as key the Day and the columns have the
 *  Meeting as Key and the user as Value
 *  This first we can select by the Da the tree
 *  of the meeting of the day ensring the check of the overlap of a meeting with
 *  the existing meetings is done only with the meeting of the day
 */
public class MeetingCalendar extends TreeMap<LocalDate, Map<Meeting, User>>
        implements PrintableAsJson {

    public MeetingCalendar() {
    }

    public void addMeetingToCalendar(LocalDate meetingDay, Meeting meeting) {

        if (get(meetingDay) == null) {
            Map<Meeting, User> meetings = new TreeMap<>();
            meetings.put(meeting, meeting.getUser());
            put(meetingDay, meetings);

        } else {
            Map<Meeting, User> meetengRow = get(meetingDay);
            final boolean[] add = {true};

            meetengRow.keySet().forEach(m ->{
                if (m.overlapWith(meeting))
                    add[0] = false;
            });

            if (add[0]) {
                Map<Meeting, User> meetingUserMap1 = get(meetingDay);
                meetingUserMap1.put(meeting, meeting.getUser());
            }
        }
    }

    public int noMeetings() {
        final int[] i = {0};
        Set<LocalDate> localDates = this.keySet();
        localDates.forEach(localDate -> {
            Map<Meeting, User> meetingUserMap = this.get(localDate);
            meetingUserMap.forEach((meeting, user) -> {
                i[0]++;
            });
        });
        return i[0];
    }

    public void print(PrintStream out) {
        Set<LocalDate> localDates = this.keySet();
        localDates.forEach(localDate -> {
            Map<Meeting, User> meetingUserMap = this.get(localDate);
            meetingUserMap.forEach((meeting, user) -> {
                out.println("Meeting requested by user :" + meeting.getUser().getName());
                out.println("\tDate :" + meeting.getStartDate());
                out.println("\tTime :" + meeting.getStartTime());
                out.println("\tDuration :" + meeting.getDuration());
            });
        });
    }
}
