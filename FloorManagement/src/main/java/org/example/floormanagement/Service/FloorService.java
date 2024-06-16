package org.example.floormanagement.Service;


import org.example.floormanagement.Entity.Floor;

import java.util.List;

public interface FloorService {
    Floor addFloor(Floor floor);

    List<Floor> getAllFloors();

    Floor getFloorByFloorNo(long floorNo);

    Floor updateFloor(Floor floor);

    void deleteFloor(long floorNo);


}
