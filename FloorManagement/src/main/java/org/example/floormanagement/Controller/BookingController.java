package org.example.floormanagement.Controller;

import org.example.floormanagement.BookingParameters.*;
import org.example.floormanagement.Entity.Booking;
import org.example.floormanagement.Entity.Room;
import org.example.floormanagement.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<Booking> bookRoom(@RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.bookRoom(
                bookingRequest.getRoomNo(),
                bookingRequest.getParsedStartTime(),
                bookingRequest.getParsedEndTime()
        );
        return ResponseEntity.ok(booking);
    }


    @PostMapping("/available-by-capacity")
    public ResponseEntity<List<Room>> findAvailableRoomsByCapacity(@RequestBody AvailableRoomByCapacity availableRoomByCapacity) {
        List<Room> rooms = bookingService.findAvailableRoomsByCapacity(
                availableRoomByCapacity.getCapacity(),
                availableRoomByCapacity.getParsedStartTime(),
                availableRoomByCapacity.getParsedEndTime()
        );
        return ResponseEntity.ok(rooms);
    }

    @PostMapping("/available-by-roomNo")
    public ResponseEntity<Room> findAvailableRoomByRoomNo(@RequestBody AvailableRoomByRoomNo availableRoomByRoomNo) {
        Room room = bookingService.findAvailableRoomByRoomNo(
                availableRoomByRoomNo.getRoomNo(),
                availableRoomByRoomNo.getParsedStartTime(),
                availableRoomByRoomNo.getParsedEndTime()
        );
        return ResponseEntity.ok(room);
    }

    @PostMapping("/suggested-rooms")
    public ResponseEntity<List<Room>> suggestMeetingRooms(@RequestBody SuggestMeetingRooms suggestMeetingRoomRequest) {
        List<Room> rooms = bookingService.suggestMeetingRooms(
                suggestMeetingRoomRequest.getCapacity(),
                suggestMeetingRoomRequest.getParsedStartTime(),
                suggestMeetingRoomRequest.getParsedEndTime()
        );
        return ResponseEntity.ok(rooms);
    }

    @PostMapping("/preferred-room")
    public ResponseEntity<Room> getPreferredRoomByLastBooking(@RequestBody PreferredRoom preferredRoomRequest) {

        Room room = bookingService.getPreferredRoomByLastBooking(
                preferredRoomRequest.getCapacity()
        );
        // handle null response
        return ResponseEntity.ok(room);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/user/{bookedBy}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable String bookedBy) {
        List<Booking> bookings = bookingService.getBookingsByUser(bookedBy);
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long bookingId, @RequestBody UpdateBooking updateBookingRequest) {
        Booking booking = bookingService.updateBooking(
                bookingId,
                updateBookingRequest.getRoomNo(),
                updateBookingRequest.getParsedStartTime(),
                updateBookingRequest.getParsedEndTime()
        );
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking Deleted Successfully!!");
    }

}
