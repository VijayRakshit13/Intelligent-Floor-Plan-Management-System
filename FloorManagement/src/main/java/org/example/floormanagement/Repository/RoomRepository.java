package org.example.floormanagement.Repository;

import org.example.floormanagement.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByRoomNo(long roomNo);

    void deleteByFloorFloorNo(Long floorNo);

    List<Room> findByFloorFloorNo(long floorNo);
}
