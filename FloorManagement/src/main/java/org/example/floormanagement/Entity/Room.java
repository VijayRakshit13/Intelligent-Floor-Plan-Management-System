package org.example.floormanagement.Entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "rooms")
@Entity
public class Room {
    @Id
    private Long roomNo;
    private String roomName;
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "floorNo")
    @JsonBackReference
    private Floor floor;
}

