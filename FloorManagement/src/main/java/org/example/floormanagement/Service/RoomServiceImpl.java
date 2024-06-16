package org.example.floormanagement.Service;

import jakarta.transaction.Transactional;
import org.example.floormanagement.Entity.Floor;
import org.example.floormanagement.Entity.Room;
import org.example.floormanagement.Repository.BookingRepository;
import org.example.floormanagement.Repository.FloorRepository;
import org.example.floormanagement.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Room createRoom(Room room) {
        Optional<Room> existingRoom = roomRepository.findById(room.getRoomNo());
        if (existingRoom.isPresent()) {
            throw new RuntimeException("Room already exists");
        }

        Optional<Floor> floorOpt = floorRepository.findById(room.getFloor().getFloorNo());
        if (!floorOpt.isPresent()) {
            throw new RuntimeException("Floor does not exist");
        }

        Room savedRoom = roomRepository.save(room);
        return savedRoom;
    }

    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms;
    }

    @Override
    public Room getRoomByRoomNo(long roomNo) {
        Room room = roomRepository.findById(roomNo)
                .orElseThrow(() -> new RuntimeException("Room does not exist"));
        return room;
    }

    @Override
    @Transactional
    public Room updateRoom(Room room) {
        if (!roomRepository.existsById(room.getRoomNo())) {
            throw new IllegalArgumentException("Room does not exist");
        }
        Optional<Floor> floorOpt = floorRepository.findById(room.getFloor().getFloorNo());
        if (!floorOpt.isPresent()) {
            throw new IllegalArgumentException("Floor does not exist");
        }
        return roomRepository.save(room);
    }

    @Override
    @Transactional
    public void deleteRoom(long roomNo) {
        Optional<Room> roomOpt = roomRepository.findById(roomNo);
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("Room does not exist");
        }
        Room room = roomOpt.get();
        bookingRepository.deleteBookingByRoomRoomNo(room.getRoomNo());
        roomRepository.delete(room);
    }

    @Override
    public List<Room> getRoomByFloor(long floorNo) {
        return roomRepository.findByFloorFloorNo(floorNo);
    }
}
