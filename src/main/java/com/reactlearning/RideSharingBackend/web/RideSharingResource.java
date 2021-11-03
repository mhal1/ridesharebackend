package com.reactlearning.RideSharingBackend.web;

import com.reactlearning.RideSharingBackend.dto.BookingDTO;
import com.reactlearning.RideSharingBackend.dto.RideDTO;
import com.reactlearning.RideSharingBackend.dto.UserDTO;
import com.reactlearning.RideSharingBackend.dto.UserResponseDTO;
import com.reactlearning.RideSharingBackend.service.RideSharingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin(value = "*")
public class RideSharingResource {

    private final RideSharingService rideSharingService;

    @GetMapping("/rides")
    public ResponseEntity<List<RideDTO>> getAllRides(){
        List<RideDTO> rideDTOList = rideSharingService.getAllRides();
        return new ResponseEntity<>(rideDTOList,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> validateLogin(@RequestBody UserDTO userDTO){
        UserResponseDTO loginResult = rideSharingService.validateLogin(userDTO);
        return new ResponseEntity<>(loginResult, HttpStatus.ACCEPTED);
    }

    @PostMapping("/book_ride")
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO){
        BookingDTO bookingDTO1 = rideSharingService.createBooking(bookingDTO);
        return new ResponseEntity<>(bookingDTO1,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete_booking/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Integer id){
        String deletedBooking = rideSharingService.deleteBooking(id);
        return new ResponseEntity<>(deletedBooking,HttpStatus.OK);
    }

    @PostMapping("/create_ride")
    public ResponseEntity<RideDTO> createRide(@RequestBody RideDTO rideDTO){
        System.out.println(rideDTO.toString());
        RideDTO rideDTO1 = rideSharingService.createRide(rideDTO);
        return new ResponseEntity<>(rideDTO1,HttpStatus.CREATED);
    }

    @GetMapping("/bookings/{userName}")
    public ResponseEntity<List<BookingDTO>> getAllBookings(@PathVariable String userName){
        List<BookingDTO> bookingDTOList = rideSharingService.getAllBookings(userName);
        return new ResponseEntity<>(bookingDTOList,HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        UserDTO userDTO1 = rideSharingService.createUser(userDTO);
        System.out.println(userDTO1.toString());
        return new ResponseEntity<>(userDTO1,HttpStatus.CREATED);
    }

    @PostMapping("/create-current-user")
    public ResponseEntity<UserDTO> createCurrentUser(@RequestBody UserDTO userDTO){
        System.out.println(userDTO.toString());
        UserDTO userDTO1 = rideSharingService.createCurrentUser(userDTO);
        return new ResponseEntity<>(userDTO1,HttpStatus.CREATED);
    }

    @GetMapping("/get-user")
    public ResponseEntity<UserDTO> getCurrentUser(){
        UserDTO userDTO = rideSharingService.getCurrentUser();
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }
}