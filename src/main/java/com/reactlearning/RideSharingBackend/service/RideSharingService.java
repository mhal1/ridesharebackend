package com.reactlearning.RideSharingBackend.service;

import com.reactlearning.RideSharingBackend.dto.*;
import com.reactlearning.RideSharingBackend.entity.Bookings;
import com.reactlearning.RideSharingBackend.entity.CurrentUserLogged;
import com.reactlearning.RideSharingBackend.entity.Rides;
import com.reactlearning.RideSharingBackend.entity.ValidUsers;
import com.reactlearning.RideSharingBackend.repository.BookingRepository;
import com.reactlearning.RideSharingBackend.repository.CurrentUserRepository;
import com.reactlearning.RideSharingBackend.repository.RideSharingRepository;
import com.reactlearning.RideSharingBackend.repository.RidesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RideSharingService {

    private final RideSharingRepository rideSharingRepository;

    private final RidesRepository ridesRepository;

    private final BookingRepository bookingRepository;

    private final CurrentUserRepository currentUserRepository;

    public UserResponseDTO validateLogin(UserDTO userDTO) {
        ValidUsers user = rideSharingRepository.findByUserName(userDTO.getUserName());
        UserResponseDTO nullUserResponseDTO = new UserResponseDTO();
        if (user != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(userDTO.getPassword(),user.getPassword())) {
                UserResponseDTO userResponseDTO = new UserResponseDTO();
                userResponseDTO.setId(user.getId());
                userResponseDTO.setUserName(user.getUserName());
                userResponseDTO.setValid("true");
                return userResponseDTO;
            } else {
                nullUserResponseDTO.setValid("false");
                return nullUserResponseDTO;
            }
        }
        nullUserResponseDTO.setValid("false");
        return nullUserResponseDTO;
    }

    public List<RideDTO> getAllRides() {
        List<RideDTO> rideList = new ArrayList<>();
        Iterable<Rides> rides = ridesRepository.findAll();
        rides.forEach(ride -> {
            RideDTO rideDTO = new RideDTO();
            rideDTO.setId(ride.getRide_id());
            rideDTO.setName(ride.getName());
            rideDTO.setStartPoint(ride.getStartPoint());
            rideDTO.setEndPoint(ride.getEndPoint());
            rideDTO.setCar(ride.getCar());
            rideDTO.setSeatsAvailable(ride.getSeatsAvailable());
            rideList.add(rideDTO);
        });
        return rideList;
    }

    public BookingResponseDTO createBooking(BookingDTO bookingDTO) {
        Bookings bookings = new Bookings();

        RideDTO rideDTO = bookingDTO.getRide();

        Rides rides = new Rides();
        rides.setRide_id(rideDTO.getId());
        rides.setName(rideDTO.getName());
        rides.setStartPoint(rideDTO.getStartPoint());
        rides.setEndPoint(rideDTO.getEndPoint());
        rides.setCar(rideDTO.getCar());
        rides.setSeatsAvailable(rideDTO.getSeatsAvailable() - 1);

        bookings.setRides(rides);
        bookings.setStatus(bookingDTO.getStatus());
        bookings.setUser(bookingDTO.getUser());

        Bookings booked = bookingRepository.save(bookings);
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setId(booked.getId());
        bookingResponseDTO.setUser(booked.getUser());
        rideDTO.setSeatsAvailable(rides.getSeatsAvailable());
        bookingResponseDTO.setRide(rideDTO);

        Integer rideUpdate = ridesRepository.updateRide(rides.getRide_id(), rides.getSeatsAvailable());

        List<BookingDTO> bookingList = getAllBookings(bookingDTO.getUser());
        Integer numberOfBookings = bookingList.size();
        bookingResponseDTO.setNumberOfBookings(numberOfBookings);

        return bookingResponseDTO;
    }

    public DeleteResponseDTO deleteBooking(Integer id,String userName) {
        Optional<Bookings> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            Bookings bookingReceived = booking.get();
            Integer bookingId = bookingReceived.getId();
            Integer rideId = bookingReceived.getRides().getRide_id();
            Integer seats = bookingReceived.getRides().getSeatsAvailable() + 1;
            //find ride and add one to seats available
            ridesRepository.updateRide(rideId, seats);
            //delete booking from booking table
            bookingRepository.delete(bookingReceived);
            List<BookingDTO> bookingList = getAllBookings(userName);
            Integer numberOfBookings = bookingList.size();
            DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
            deleteResponseDTO.setNumberOfBookings(numberOfBookings);
            return deleteResponseDTO;
        }
        return null;
    }

    public RideDTO createRide(RideDTO rideDTO) {
        Rides rides = new Rides();
        rides.setName(rideDTO.getName());
        rides.setStartPoint(rideDTO.getStartPoint());
        rides.setEndPoint(rideDTO.getEndPoint());
        rides.setCar(rideDTO.getCar());
        rides.setSeatsAvailable(rideDTO.getSeatsAvailable());
        Rides newRide = ridesRepository.save(rides);
        rideDTO.setId(newRide.getRide_id());
        return rideDTO;
    }

    public List<BookingDTO> getAllBookings(String user) {
        List<BookingDTO> bookingList = new ArrayList<>();
        Iterable<Bookings> bookings = bookingRepository.findBookingsByUserName(user);
        bookings.forEach(booking -> {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setId(booking.getId());
            bookingDTO.setStatus(booking.getStatus());

            RideDTO rideDTO = new RideDTO();
            rideDTO.setId(booking.getRides().getRide_id());
            rideDTO.setName(booking.getRides().getName());
            rideDTO.setStartPoint(booking.getRides().getStartPoint());
            rideDTO.setEndPoint(booking.getRides().getEndPoint());
            rideDTO.setCar(booking.getRides().getCar());
            rideDTO.setSeatsAvailable(booking.getRides().getSeatsAvailable());

            bookingDTO.setRide(rideDTO);

            bookingList.add(bookingDTO);
        });
        return bookingList;
    }

    public UserDTO createUser(UserDTO userDTO) {
        System.out.println(userDTO.toString());
        ValidUsers user = new ValidUsers();
        user.setName(userDTO.getName());
        user.setUserName(userDTO.getUserName());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encoded = encoder.encode(userDTO.getPassword());
        System.out.println(encoded);
        user.setPassword(encoded);
        // check whether it already in db
        ValidUsers checkUserExist = rideSharingRepository.findByUserName(userDTO.getUserName());
        if (checkUserExist == null) {
            ValidUsers newUser = rideSharingRepository.save(user);
            userDTO.setId(newUser.getId());
            return userDTO;
        }
        return null;
    }

    public UserDTO createCurrentUser(UserDTO userDTO) {
        System.out.println(userDTO.toString());
        CurrentUserLogged user = new CurrentUserLogged();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        //delete all entries in db
        currentUserRepository.deleteAll();
        //save current user
        CurrentUserLogged newUser = currentUserRepository.save(user);
        return userDTO;
    }

    public UserDTO getCurrentUser() {
        UserDTO userDTO = new UserDTO();
        Iterable<CurrentUserLogged> users = currentUserRepository.findAll();
        users.forEach(user -> {
            userDTO.setId(user.getId());
            userDTO.setUserName(user.getUserName());
            userDTO.setPassword(user.getPassword());
            userDTO.setName(user.getName());
            userDTO.setLoggedIn("true");
        });
        return userDTO;
    }

}