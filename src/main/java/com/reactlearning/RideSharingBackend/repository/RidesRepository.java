package com.reactlearning.RideSharingBackend.repository;

import com.reactlearning.RideSharingBackend.entity.Rides;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RidesRepository extends CrudRepository<Rides,Integer> {

    @Query("UPDATE Rides SET seatsAvailable =?2 WHERE ride_id = ?1")
    @Modifying
    Integer updateRide(Integer rideId,Integer seats);
}
