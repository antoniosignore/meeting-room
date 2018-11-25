package com.akqa.model;

import org.junit.Assert;
import org.junit.Test;
import uk.co.datumedge.hamcrest.json.SameJSONAs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PrintableAsJsonTest {
    @Test
    public void toJsonString() throws Exception {

        Meeting meeting = new Meeting(
                LocalDateTime.of(2012,1,1,1,1,1),
                LocalDate.of(2012,1,2),
                LocalTime.of(1,1,1),
                1,new User("maradona"));

        String s = meeting.toJsonString();

        Assert.assertThat(
                "{\n" +
                        "  \"startDate\" : {\n" +
                        "    \"year\" : 2012,\n" +
                        "    \"month\" : \"JANUARY\",\n" +
                        "    \"chronology\" : {\n" +
                        "      \"calendarType\" : \"iso8601\",\n" +
                        "      \"id\" : \"ISO\"\n" +
                        "    },\n" +
                        "    \"era\" : \"CE\",\n" +
                        "    \"monthValue\" : 1,\n" +
                        "    \"dayOfMonth\" : 2,\n" +
                        "    \"dayOfYear\" : 2,\n" +
                        "    \"dayOfWeek\" : \"MONDAY\",\n" +
                        "    \"leapYear\" : true\n" +
                        "  },\n" +
                        "  \"startTime\" : {\n" +
                        "    \"hour\" : 1,\n" +
                        "    \"minute\" : 1,\n" +
                        "    \"second\" : 1,\n" +
                        "    \"nano\" : 0\n" +
                        "  },\n" +
                        "  \"duration\" : 1,\n" +
                        "  \"user\" : {\n" +
                        "    \"name\" : \"maradona\"\n" +
                        "  }\n" +
                        "}",
                SameJSONAs.sameJSONAs(s));
    }

}