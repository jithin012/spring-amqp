package com.mclabs.learn.aopExample;

import java.util.HashMap;
import java.util.Map;

public class PassengerDaoImpl implements PassengerDao {
    private static Map<Integer, Passenger> passengerMap = new HashMap<>();


    public Passenger getPassenger(int id) {
        if (passengerMap.get(id) != null) {
            return passengerMap.get(id);
        }
        Passenger passenger = new Passenger(id);
        passengerMap.put(id, passenger);
        return passenger;
    }
}
