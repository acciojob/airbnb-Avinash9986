package com.driver.controllers;


import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class HotelManagementRepository {

    Map<String,Hotel> HotelDB = new HashMap<>() ;
    Map<Integer,User> UserDB = new HashMap<>() ;

    Map<String,Booking> BookingDB = new HashMap<>() ;

    public String addHotel(Hotel hotel) {
        if(hotel.getHotelName() == null || hotel == null){
            return "FAILURE" ;
        }
        for (String hotell : HotelDB.keySet()){
            if(hotell == hotel.getHotelName()){
                return "FAILURE" ;
            }
        }
        HotelDB.put(hotel.getHotelName(),hotel) ;
        return "SUCCESS" ;

    }

    public Integer addUser(User user) {
        for (Integer usr : UserDB.keySet()) {
            if (usr == user.getaadharCardNo()){
                return usr ;
            }
        }
        UserDB.put(user.getaadharCardNo(),user) ;
        return user.getaadharCardNo() ;
    }

    public String getHotelWithMostFacilities() {
        int NoOfFacilities = 0 ;
        String HotelName = null ;
        for (String hotelName : HotelDB.keySet()) {
            Hotel hotel = HotelDB.get(hotelName) ;
            List<Facility> facilities = hotel.getFacilities() ;
            if (facilities.size() > NoOfFacilities){
                HotelName = hotel.getHotelName() ;
                NoOfFacilities = facilities.size() ;
            }
            if (facilities.size() == NoOfFacilities){
                HotelName = Lexo(HotelName,hotel.getHotelName()) ;
            }
        }
        if (NoOfFacilities == 0){
            return "" ;
        }
        return HotelName ;
    }

    public String Lexo(String str1 , String str2 ){
        int comparisonResult = str1.compareTo(str2);

        if (comparisonResult < 0) {
            // str1 comes before str2 in lexicographic order
            return str1;
        } else if (comparisonResult > 0) {
            // str2 comes before str1 in lexicographic order
            return str2;
        } else {
            // The two strings are equal
            return str1 ;
        }
    }

    public int bookARoom(Booking booking) {
        UUID uuid = UUID.randomUUID();

        // Convert the UUID to a string
        String bookingID = uuid.toString();
        booking.setBookingId(bookingID);

        int totalAmount = -1 ;
        for (String hotelName : HotelDB.keySet()){
            if (Objects.equals(hotelName, booking.getHotelName())){
                Hotel tempHotel = HotelDB.get(hotelName) ;
                if(tempHotel.getAvailableRooms() < booking.getNoOfRooms()){
                    return totalAmount ;
                }
                else {
                    booking.setAmountToBePaid(booking.getNoOfRooms() * tempHotel.getPricePerNight());
                    BookingDB.put(bookingID,booking) ;
                    totalAmount =  booking.getNoOfRooms() * tempHotel.getPricePerNight() ;
                }
            }
        }
        return totalAmount ;
    }

    public int getBookings(Integer aadharCard) {
        int count = 0 ;
        for (String bookingId : BookingDB.keySet()){
            Booking booking = BookingDB.get(bookingId) ;
            if(booking.getBookingAadharCard() == aadharCard){
                count++ ;
            }
        }
        return count ;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        for (String hotel_name : HotelDB.keySet()){
            if (hotel_name == hotelName) {
                Hotel hotel = HotelDB.get(hotelName) ;
                List<Facility> list = hotel.getFacilities();
                for (int i = 0 ; i < newFacilities.size() ; i++){
                    if (!list.contains(newFacilities.get(i))){
                        list.add(newFacilities.get(i)) ;
                    }
                }
            }
        }
        return HotelDB.get(hotelName) ;
    }
}
