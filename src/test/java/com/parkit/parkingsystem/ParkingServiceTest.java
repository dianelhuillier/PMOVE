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

        try {
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
            
            Date outTime = new Date(System.currentTimeMillis());
            
//		String pattern = "MM/dd/yyyy HH:mm:ss";
//		DateFormat df = new SimpleDateFormat(pattern);
//		Date today = Calendar.getInstance().getTime();        
//		String todayAsString = df.format(today);
//		when(inputReaderUtil.readOutTime()).thenReturn(todayAsString);
            System.out.println("1er outtime" + outTime);

        Ticket ticket = new Ticket();
  //      ticket.setOutTime(new Date(todayAsString));
        ticket.setInTime(new Date(System.currentTimeMillis() - (60*60*1000)));

        ticket.setOutTime(outTime);
                System.out.println("2eme outtime" + outTime);
                
                
                
        		double inHour = ticket.getInTime().getTime(); 
        		double outHour = ticket.getOutTime().getTime(); 
        		double duration = ((outHour - inHour) / ( 60 * 60 * 1000));  
        		
        		
System.out.println("dao test duration " + duration);
  //      when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
        when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
        when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
        parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        
    
        
		if(ticketDAO.updateTicket(ticket)) {

			parkingSpot.setAvailable(true);
			parkingSpotDAO.updateParking(parkingSpot);

		}else{
			System.out.println("Unable to update test ticket information. Error occurred");
		}

        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
		}
    }
}
