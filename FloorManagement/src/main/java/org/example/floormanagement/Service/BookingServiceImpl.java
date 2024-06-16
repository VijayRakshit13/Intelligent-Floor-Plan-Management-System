package org.example.floormanagement.Service;

import org.example.floormanagement.Entity.Booking;
import org.example.floormanagement.Entity.Room;
import org.example.floormanagement.Repository.BookingRepository;
import org.example.floormanagement.Repository.RoomRepository;
import org.example.floormanagement.Security.Entities.Role;
import org.example.floormanagement.Security.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Booking bookRoom(Long roomNo, LocalDateTime startTime, LocalDateTime endTime) {

        LocalDateTime currentTime = LocalDateTime.now();
        if (startTime.isBefore(currentTime)) {
            throw new IllegalArgumentException("Start time must be in the future");
        }

        if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        String bookedBy = getCurrentUserEmail();

        List<Booking> conflictingBookings = bookingRepository.findBookingsForRoomInTimeRange(roomNo, startTime, endTime);
        if (!conflictingBookings.isEmpty()) {
            throw new IllegalArgumentException("Room is already booked for the given time range");
        }

        Optional<Room> roomOpt = roomRepository.findById(roomNo);
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("Room does not exist");
        }

        Booking booking = new Booking();
        booking.setRoom(roomOpt.get());
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setBookedBy(bookedBy); // Automatically set the current user's email

        return bookingRepository.save(booking);
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @Override
    public List<Room> findAvailableRoomsByCapacity(int capacity, LocalDateTime startTime, LocalDateTime endTime) {

        if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        
        List<Room> allRooms = roomRepository.findAll();
        return allRooms.stream()
                .filter(room -> room.getCapacity() >= capacity)
                .filter(room -> bookingRepository.findBookingsForRoomInTimeRange(room.getRoomNo(), startTime, endTime).isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public Room findAvailableRoomByRoomNo(Long roomNo, LocalDateTime startTime, LocalDateTime endTime) {

        if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        Optional<Room> roomOpt = roomRepository.findById(roomNo);
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("Room does not exist");
        }

        Room room = roomOpt.get();
        List<Booking> conflictingBookings = bookingRepository.findBookingsForRoomInTimeRange(roomNo, startTime, endTime);
        if (!conflictingBookings.isEmpty()) {
            throw new IllegalArgumentException("Room is already booked for the given time range");
        }

        return room;
    }

    @Override
    public List<Room> suggestMeetingRooms(int capacity, LocalDateTime startTime, LocalDateTime endTime) {

        if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        List<Room> availableRooms = roomRepository.findAll();

        return availableRooms.stream()
                .filter(room -> room.getCapacity() > capacity)
                .filter(room -> bookingRepository.findBookingsForRoomInTimeRange(room.getRoomNo(), startTime, endTime).isEmpty())
                .sorted(Comparator.comparingInt(Room::getCapacity))
                .collect(Collectors.toList());
    }

    @Override
    public Room getPreferredRoomByLastBooking(int capacity) {
        String bookedBy = getCurrentUserEmail();

        List<Booking> userBookings = bookingRepository.findBookingsByBookedBy(bookedBy);

        if (userBookings.isEmpty()) {
            return null;
        }

        Booking lastBooking = userBookings.get(userBookings.size() - 1);
        Room lastBookedRoom = lastBooking.getRoom();

        if (lastBookedRoom.getCapacity() >= capacity) {
            return lastBookedRoom;
        }

        List<Room> suggestedRooms = roomRepository.findAll().stream()
                .filter(room -> room.getCapacity() >= capacity)
                .sorted(Comparator.comparingInt(Room::getCapacity))
                .collect(Collectors.toList());

        return suggestedRooms.isEmpty() ? null : suggestedRooms.get(0);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getBookingsByUser(String bookedBy) {
        return bookingRepository.findBookingsByBookedBy(bookedBy);
    }

    @Override
    public Booking updateBooking(Long bookingId, Long roomNo, LocalDateTime startTime, LocalDateTime endTime) {
        String currentUserEmail = getCurrentUserEmail();
        
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (!bookingOpt.isPresent()) {
            throw new IllegalArgumentException("Booking does not exist");
        }

        if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        List<Booking> conflictingBookings = bookingRepository.findBookingsForRoomInTimeRange(roomNo, startTime, endTime);
        if (!conflictingBookings.isEmpty()) {
            throw new IllegalArgumentException("Room is already booked for the given time range");
        }

        Optional<Room> roomOpt = roomRepository.findById(roomNo);
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("Room does not exist");
        }

        Booking booking = bookingOpt.get();
        
        if (!isAdmin(currentUserEmail) && !currentUserEmail.equals(booking.getBookedBy())) {
            throw new IllegalArgumentException("You are not authorized to update this booking");
        }
        
        booking.setRoom(roomOpt.get());
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);

        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long bookingId) {

        String currentUserEmail = getCurrentUserEmail();

        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new IllegalArgumentException("Booking does not exist");
        }
        Booking booking = bookingOpt.get();

        if (!isAdmin(currentUserEmail) && !currentUserEmail.equals(booking.getBookedBy())) {
            throw new IllegalArgumentException("You are not authorized to cancel this booking");
        }

        bookingRepository.deleteById(bookingId);
    }

    private boolean isAdmin(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .map(user -> user.getRole() == Role.ADMIN)
                .orElse(false);
    }
}
