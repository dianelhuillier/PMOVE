package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

	private static ParkingService parkingService;

	@Mock
	private static InputReaderUtil inputReaderUtil;
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static TicketDAO ticketDAO;

	@BeforeEach
	private void setUpPerTest() {
		try {
			when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");

			ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
			Ticket ticket = new Ticket();
			ticket.setInTime(new Date(System.currentTimeMillis() - (60*60*1000)));
			ticket.setParkingSpot(parkingSpot);
			ticket.setVehicleRegNumber("ABCDEF");
			ticket.setOutTime(null);
			when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
			when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);

			when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

			parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		} catch (Exception e) {
			e.printStackTrace();
			throw  new RuntimeException("Failed to set up test mock objects");
		}
	}


	//TODO imposer outtime ?
	@Test
	public void processExitingVehicleTest(){
		parkingService.processExitingVehicle();
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
//		String vehicleRegNumber = "ABCDEF";
//
//		try {
//			ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
//			
//			Ticket ticket = ticketDAO.getTicket(vehicleRegNumber);
//
////			Ticket ticket = new Ticket();
//
//	        Date inTime = new Date();
//	        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
//	        Date outTime = new Date();
//
//		//	Date outTime = new Date(System.currentTimeMillis());
//
//			System.out.println("1er outtime" + outTime);
//
//			ticket.setInTime(new Date(System.currentTimeMillis() - (60*60*1000)));
//			ticket.setOutTime(outTime);
//			System.out.println("2eme outtime" + outTime);
//
//
//
//			double inHour = ticket.getInTime().getTime(); 
//			double outHour = ticket.getOutTime().getTime(); 
//			double duration = ((outHour - inHour) / ( 60 * 60 * 1000));  
//
//
//			System.out.println("dao test duration " + duration);
//			//      when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
//			when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
//			when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
//			parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
//
//			System.out.println("Recorded out-time for vehicle number TEST DAO:" + ticket.getVehicleRegNumber() + " is:" + outTime + "ID : " + ticket.getId());
//
//
//			if(ticketDAO.updateTicket(ticket)) {
//
//				parkingSpot.setAvailable(true);
//				parkingSpotDAO.updateParking(parkingSpot);
//
//			}else{
//				System.out.println("Unable to update test ticket information. Error occurred");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw  new RuntimeException("Failed to set up test mock objects");
//		}
		String vehicleRegNumber = "ABCDEF";
		Ticket ticket = ticketDAO.getTicket(vehicleRegNumber);
		Date outTime = new Date(System.currentTimeMillis());
		ticket.setOutTime(outTime);
		//ticket.setPrice(FareCalculatorServiceTest.calculateFare(ticket));
		//  fareCalculatorService.calculateFare(ticket);
		System.out.println("in time"  + ticket.getInTime().toString());
		System.out.println("out time" + ticket.getOutTime().toString());

		if(ticketDAO.updateTicket(ticket)) {
			ParkingSpot parkingSpot = ticket.getParkingSpot();
			parkingSpot.setAvailable(true);
			parkingSpotDAO.updateParking(parkingSpot);
			System.out.println("Please pay the parking fare:" + ticket.getPrice());
			System.out.println("Recorded out-time for vehicle number:" + ticket.getVehicleRegNumber() + " is:" + outTime + "ID : " + ticket.getId());
		}else{
			System.out.println("Unable to update ticket information. Error occurred");
		}
	}
}
