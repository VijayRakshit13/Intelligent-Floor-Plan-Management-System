package org.example.floormanagement.BookingParameters;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SuggestMeetingRooms {
    private int capacity;
    private String startTime;
    private String endTime;

    public LocalDateTime getParsedStartTime() {
        return LocalDateTime.parse(startTime);
    }

    public LocalDateTime getParsedEndTime() {
        return LocalDateTime.parse(endTime);
    }
}
