package org.example.floormanagement.Service;

import org.example.floormanagement.Entity.Booking;
import org.example.floormanagement.Entity.Room;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    Booking bookRoom(Long roomNo, LocalDateTime startTime, LocalDateTime endTime);

    List<Room> findAvailableRoomsByCapacity(int capacity, LocalDateTime startTime, LocalDateTime endTime);

    Room findAvailableRoomByRoomNo(Long roomNo, LocalDateTime startTime, LocalDateTime endTime);

    List<Room> suggestMeetingRooms(int capacity, LocalDateTime startTime, LocalDateTime endTime);

    Room getPreferredRoomByLastBooking(int capacity);

    List<Booking> getAllBookings();

    List<Booking> getBookingsByUser(String bookedBy);

    Booking updateBooking(Long bookingId, Long roomNo, LocalDateTime startTime, LocalDateTime endTime);

    void cancelBooking(Long bookingId);
}

