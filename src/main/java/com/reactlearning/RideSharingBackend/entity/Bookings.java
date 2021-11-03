package com.reactlearning.RideSharingBackend.entity;

import com.reactlearning.RideSharingBackend.dto.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private String user;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Rides rides;

    @Override
    public String toString() {
        return "Bookings{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
