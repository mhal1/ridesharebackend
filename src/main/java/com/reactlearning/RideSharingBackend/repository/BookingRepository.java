package com.reactlearning.RideSharingBackend.repository;

import com.reactlearning.RideSharingBackend.entity.Bookings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository<Bookings,Integer> {

    @Query("SELECT b FROM Bookings b where b.user =?1")
    List<Bookings> findBookingsByUserName(String userName);
}
