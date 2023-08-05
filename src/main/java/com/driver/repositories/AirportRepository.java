package com.driver.repositories;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Repository
public class AirportRepository {
    public HashMap<String, Airport> airportHashMap = new HashMap<>();
    public HashMap<Integer, Flight> flightHashMap = new HashMap<>();
    public void addAirport(String name,Airport airport){
        airportHashMap.put(name,airport);
    }
    public HashMap<String,Airport> getAirportHashMap(){
        return airportHashMap;
    }

    public HashMap<City, List<Flight>> cityFlightHashMap = new HashMap<>();
    public HashMap<Integer, Passenger> passengerHashMap = new HashMap<>();
    public HashMap<Integer,List<Integer>> bookingDb = new HashMap<>();

    public HashMap<Integer,Integer> bookingByPassenger = new HashMap<>();
    public void addFlights(Flight flight,Integer id){

        flightHashMap.put(id,flight);
    }

    public void addPassengers(Integer id,Passenger passenger){
        passengerHashMap.put(id,passenger);
    }
//    public List<Flight> getCityFlights(City fromCity){
//        return cityFlightHashMap.get(fromCity);
//    }

    public String addBooking(Integer flightId,List<Integer> bookingList){
        Flight flight = flightHashMap.get(flightId);
        City fromCity = flight.getFromCity();
        List<Flight> flights;
        if(cityFlightHashMap.containsKey(fromCity)){
            flights = cityFlightHashMap.get(fromCity);
        }
        else {
            flights = new ArrayList<>();
        }
        flights.add(flight);
        cityFlightHashMap.put(fromCity,flights);
        bookingDb.put(flightId,bookingList);

        return "SUCCESS";
    }
    public void addBookingByPassenger(Integer passengerId,Integer in){
        bookingByPassenger.put(passengerId,bookingByPassenger.getOrDefault(passengerId,0)+in);
    }
}
