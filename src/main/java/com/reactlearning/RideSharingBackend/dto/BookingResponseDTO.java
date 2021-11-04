package com.reactlearning.RideSharingBackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponseDTO {

    private Integer id;

    private String user;

    private RideDTO ride;

    private StatusEnum status;

    private Integer numberOfBookings;

    @Override
    public String toString() {
        return "BookingDTO{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", ride=" + ride +
                ", status=" + status +
                '}';
    }
}
