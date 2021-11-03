package com.reactlearning.RideSharingBackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RideDTO {

    private Integer id;

    private String name;

    private String startPoint;

    private String endPoint;

    private String car;

    private Integer seatsAvailable;

    @Override
    public String toString() {
        return "RideDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", car='" + car + '\'' +
                ", seatsAvailable=" + seatsAvailable +
                '}';
    }
}
