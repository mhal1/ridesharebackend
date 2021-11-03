package com.reactlearning.RideSharingBackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Rides {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ride_id;

    private String name;

    private String startPoint;

    private String endPoint;

    private String car;

    private Integer seatsAvailable;

}
