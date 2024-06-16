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
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Floor addFloor(Floor floor) {
        if (floorRepository.existsById(floor.getFloorNo())) {
            throw new IllegalArgumentException("Floor already exists");
        }
        return floorRepository.save(floor);
    }

    @Override
    public List<Floor> getAllFloors() {
        return floorRepository.findAll();
    }

    @Override
    public Floor getFloorByFloorNo(long floorNo) {
        Floor floor = floorRepository.findById((long) floorNo)
                .orElseThrow(() -> new RuntimeException("Floor does not exist"));
        return floor;
    }

    @Override
    public Floor updateFloor(Floor floor) {
        if (!floorRepository.existsById(floor.getFloorNo())) {
            throw new IllegalArgumentException("Floor does not exist");
        }
        return floorRepository.save(floor);
    }


    @Override
    @Transactional
    public void deleteFloor(long floorNo) {
        Optional<Floor> floorOpt = floorRepository.findById(floorNo);
        if (!floorOpt.isPresent()) {
            throw new IllegalArgumentException("Floor does not exist");
        }

        Floor floor = floorOpt.get();

        for (Room room : floor.getRooms()) {
            bookingRepository.deleteBookingByRoomRoomNo(room.getRoomNo());
        }

        roomRepository.deleteByFloorFloorNo(floor.getFloorNo());

        floorRepository.delete(floor);
    }

}
