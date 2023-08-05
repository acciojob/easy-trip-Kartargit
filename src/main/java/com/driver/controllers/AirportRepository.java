package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class AirportRepository {
    HashMap<String, Airport> airportHashMap = new HashMap<>();
    HashMap<Integer, Flight> flightHashMap = new HashMap<>();
    public void addAirport(String name,Airport airport){
        airportHashMap.put(name,airport);
    }
    public HashMap<String,Airport> getAirportHashMap(){
        return airportHashMap;
    }

    HashMap<City,List<Flight>> cityFlightHashMap = new HashMap<>();
    HashMap<Integer, Passenger> passengerHashMap = new HashMap<>();
    HashMap<Integer,List<Integer>> bookingDb = new HashMap<>();

    HashMap<Integer,Integer> bookingByPassenger = new HashMap<>();
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
