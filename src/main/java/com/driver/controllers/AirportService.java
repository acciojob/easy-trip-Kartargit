package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AirportService {
    @Autowired
    AirportRepository repositoryObj = new AirportRepository();

    public String addAirport(Airport airport){
        String name = airport.getAirportName();
        repositoryObj.addAirport(name,airport);
        return "SUCCESS";
    }
    public String getLargestAirportName() {
        HashMap<String, Airport> airportHashMap = repositoryObj.getAirportHashMap();
        String largest = "";
        int count = 0;
        for (String name : airportHashMap.keySet()) {
            Airport airport = airportHashMap.get(name);
            if (airport.getNoOfTerminals() > count) {
                count = airport.getNoOfTerminals();
                largest = name;
            } else if (airport.getNoOfTerminals() == count) {
                if (largest.compareTo(name) > 0) {
                    largest = name;
                }
            }
        }
        return largest;
    }
    public String addFlights(Flight flight){
        Integer id = flight.getFlightId();
        repositoryObj.addFlights(flight,id);
        return "SUCCESS";
    }
    public String addPassengers(Passenger passenger){
        Integer id = passenger.getPassengerId();
        repositoryObj.addPassengers(id,passenger);
        return "SUCCESS";
    }
    public double shortestTimeDuration(City fromCity,City toCity){
        List<Flight> flights = repositoryObj.cityFlightHashMap.get(fromCity);
        double timeDuration = Double.MAX_VALUE;
        for (Flight flight:flights){
            if(flight.getToCity().equals(toCity)&&flight.getDuration()<timeDuration){
                timeDuration = flight.getDuration();
            }
        }
        if(timeDuration == Double.MAX_VALUE)return -1;
        return timeDuration;
    }
    public String ticketBooking(Integer flightId,Integer passengerId){
        List<Integer> bookingList ;
        if(repositoryObj.bookingDb.containsKey(flightId)){
            bookingList = repositoryObj.bookingDb.get(flightId);
            if(bookingList.contains(passengerId))return "FAILURE";
            Flight flight = repositoryObj.flightHashMap.get(flightId);
            int capacity = flight.getMaxCapacity();
            if(bookingList.size()<capacity){
                bookingList.add(passengerId);
                repositoryObj.addBookingByPassenger(passengerId,1);
                return repositoryObj.addBooking(flightId,bookingList);
            }

        }
        else {
            bookingList = new ArrayList<>();

            bookingList.add(passengerId);

            repositoryObj.addBookingByPassenger(passengerId,1);
            return repositoryObj.addBooking(flightId,bookingList);
        }


            return "FAILURE";

    }
    public String cancelTicket(Integer flightId,Integer passengerId){
        if(repositoryObj.bookingDb.containsKey(flightId)){
            List<Integer> bookingList = repositoryObj.bookingDb.get(flightId);
            if(bookingList.contains(passengerId)){
                bookingList.remove(passengerId);
                repositoryObj.addBookingByPassenger(passengerId,-1);
                return repositoryObj.addBooking(flightId,bookingList);
            }
        }
         return "FAILURE";
    }
    public int getCountOfBooking(Integer passengerId){

        int n = repositoryObj.bookingByPassenger.get(passengerId);
        return n;
    }
    public String getFromCityAirportName(Integer flightId){
        Flight flight = repositoryObj.flightHashMap.get(flightId);
        City fromCity = flight.getFromCity();

        for(String airPortName:repositoryObj.airportHashMap.keySet()){
            Airport airport = repositoryObj.airportHashMap.get(airPortName);
            if(airport.getCity().equals(fromCity)){
                return airPortName;
            }
        }
        return null;
    }
    public int getFlightFare(Integer flightId){
        int fare = 3000;
        if(repositoryObj.bookingDb.containsKey(flightId)){
            int noOfPassenger = repositoryObj.bookingDb.get(flightId).size();
             fare += + (noOfPassenger*50);
        }
        return fare;
    }
    public int calculateRevenue(Integer flightId){
        int noOfPassenger = repositoryObj.bookingDb.get(flightId).size();
        if(noOfPassenger==0)return 0;
        int revenue = 3000;
        int fare = 3000;
        for(int i=1;i<noOfPassenger;i++){

            revenue+= fare+(i*50);
        }
        return revenue;
    }
}
