package org.example.floormanagement.Repository;

import org.example.floormanagement.Entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FloorRepository extends JpaRepository<Floor, Long> {
}
