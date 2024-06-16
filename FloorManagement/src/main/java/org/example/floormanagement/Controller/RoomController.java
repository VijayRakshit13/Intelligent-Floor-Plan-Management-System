package org.example.floormanagement.Controller;

import org.example.floormanagement.Entity.Room;
import org.example.floormanagement.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room createdRoom = roomService.createRoom(room);
        return ResponseEntity.ok(createdRoom);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{roomNo}")
    public ResponseEntity<Room> getRoomById(@PathVariable long roomNo) {
        Room room = roomService.getRoomByRoomNo(roomNo);
        return room != null ? ResponseEntity.ok(room) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{roomNo}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Room> updateRoom(@PathVariable long roomNo, @RequestBody Room room) {
        room.setRoomNo(roomNo);
        Room updatedRoom = roomService.updateRoom(room);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{roomNo}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> deleteRoom(@PathVariable long roomNo) {
        roomService.deleteRoom(roomNo);
        return ResponseEntity.ok("Room deleted successfully!!");
    }

    @GetMapping("/{floorNo}/rooms")
    public ResponseEntity<List<Room>> getRoomsByFloor(@PathVariable int floorNo) {
        List<Room> rooms = roomService.getRoomByFloor(floorNo);
        return ResponseEntity.ok(rooms);
    }
}
