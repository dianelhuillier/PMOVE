package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;


public class FareCalculatorService {

	public double calculateFare(Ticket ticket){
		if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
			throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
		}
		
		double inHour = ticket.getInTime().getTime(); 
		double outHour = ticket.getOutTime().getTime(); 
		//TODO: Some tests are failing here. Need to check if this logic is correct => transformer les minutes en heure
		double duration = ((outHour - inHour) / ( 60 * 60 * 1000));  
		System.out.println("la durée en heures " + duration);

		if (duration > 0.5) {  
			switch (ticket.getParkingSpot().getParkingType()){
			case CAR: {
				//  ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
				return duration * Fare.CAR_RATE_PER_HOUR;
				//break; 
			}
			case BIKE: {
				//     ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
				return duration * Fare.BIKE_RATE_PER_HOUR;
				//      break;
			}
			default: throw new IllegalArgumentException("Unkown Parking Type");
			}
		}else {
			return duration * Fare.PARK_LESS_THAN_HALF_HOUR;
		}
	}  
	
	
}