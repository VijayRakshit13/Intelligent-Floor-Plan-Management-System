package org.example.floormanagement.Repository;

import org.example.floormanagement.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.room.roomNo = :roomNo AND "
            + "((b.startTime < :endTime AND b.endTime > :startTime))")
    List<Booking> findBookingsForRoomInTimeRange(@Param("roomNo") Long roomNo,
                                                 @Param("startTime") LocalDateTime startTime,
                                                 @Param("endTime") LocalDateTime endTime);

    @Query("SELECT b FROM Booking b WHERE b.bookedBy = :bookedBy ORDER BY b.startTime DESC")
    List<Booking> findLastBookingsByUser(@Param("bookedBy") String bookedBy);

    List<Booking> findBookingsByBookedBy(String bookedBy);

    void deleteBookingByRoomRoomNo(Long roomNo);
}
