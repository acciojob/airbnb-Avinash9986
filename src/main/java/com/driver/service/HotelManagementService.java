package com.driver.service;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repository.HotelManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelManagementService {

    @Autowired
    HotelManagementRepository hotelManagementRepository ;

    public  int bookARoom(Booking booking) {
        return hotelManagementRepository.bookARoom(booking) ;
    }


    public String addHotel(Hotel hotel) {
        return hotelManagementRepository.addHotel(hotel) ;
    }

    public Integer addUser(User user) {
        return hotelManagementRepository.addUser(user) ;
    }

    public String getHotelWithMostFacilities() {
        return hotelManagementRepository.getHotelWithMostFacilities() ;
    }
}
