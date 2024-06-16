package org.example.floormanagement.Controller;

import org.example.floormanagement.Entity.Floor;
import org.example.floormanagement.Service.FloorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/floors")
public class FloorController {

    private final FloorService floorService;

    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Floor> addFloor(@RequestBody Floor floor) {
        Floor addedFloor = floorService.addFloor(floor);
        return ResponseEntity.ok(addedFloor);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Floor>> getAllFloors() {
        List<Floor> floors = floorService.getAllFloors();
        return ResponseEntity.ok(floors);
    }

    @GetMapping("/{floorNo}")
    public ResponseEntity<Floor> getFloorByFloorNo(@PathVariable int floorNo) {
        Floor floor = floorService.getFloorByFloorNo(floorNo);
        return floor != null ? ResponseEntity.ok(floor) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{floorNo}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Floor> updateFloor(@PathVariable int floorNo, @RequestBody Floor floor) {
        floor.setFloorNo((long) floorNo);
        Floor updatedFloor = floorService.updateFloor(floor);
        return ResponseEntity.ok(updatedFloor);
    }

    @DeleteMapping("/{floorNo}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> deleteFloor(@PathVariable int floorNo) {
        floorService.deleteFloor(floorNo);
        return ResponseEntity.ok("Floor deleted successfully!!");
    }
}