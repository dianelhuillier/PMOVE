package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;


public class FareCalculatorService {

    public double calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }
        // remplacer int par double ?! genre 1.5h => ca change rien il manque les minutes !!
        // inHour = 21 au lieu de 17 alors que outHour = 17 :/ et les autres tests fonctionnent puisque basés sur une DUREE
        // bon faut ajouter un minutage et ensuite gerer le 21
		double inHour = ticket.getInTime().getTime(); // changer gethours ?
		double outHour = ticket.getOutTime().getTime();


System.out.println("heure d'arrivée " + inHour + "heure de sortie " + outHour);


        //TODO: Some tests are failing here. Need to check if this logic is correct => tranformer les minutes en heure
        double duration = (outHour - inHour) / (60 * 60 * 1000);
        System.out.println("total" + duration);
        System.out.println("transfo " + duration);

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
    }
}