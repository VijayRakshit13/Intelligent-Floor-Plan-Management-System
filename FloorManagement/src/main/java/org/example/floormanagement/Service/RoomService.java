package org.example.floormanagement.Service;

import org.example.floormanagement.Entity.Room;

import java.util.List;

public interface RoomService {

    Room createRoom(Room room);

    List<Room> getAllRooms();

    Room getRoomByRoomNo(long roomNo);

    Room updateRoom(Room room);

    void deleteRoom(long roomNo);

    List<Room> getRoomByFloor(long floor);
}
