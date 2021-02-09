package com.parkit.parkingsystem.service;

import java.util.Iterator;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;


public class FareCalculatorService {

	public double calculateFare(Ticket ticket){
		if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
			throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
		}
		// remplacer int par double ?! genre 1.5h => ca change rien il manque les minutes !!
		double inHour = ticket.getInTime().getTime(); // changer gethours ? => getTime en ms
		double outHour = ticket.getOutTime().getTime();


		//TODO: Some tests are failing here. Need to check if this logic is correct => tranformer les minutes en heure
		double duration = ((outHour - inHour) / (60 * 60 * 1000)) ;  
		System.out.println("la durée " + duration);

		if (duration > 50000) {
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
		} else {
			System.out.println("Less than 30min are free, have a nice day");
			return duration * Fare.PARK_LESS_THAN_HALF_HOUR;
		}} 
}