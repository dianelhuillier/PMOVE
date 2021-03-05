package com.parkit.parkingsystem.integration;

import com.mysql.cj.exceptions.AssertionFailedException;
import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

import junit.framework.Assert;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Iterator;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO;
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;

	@Mock
	private static InputReaderUtil inputReaderUtil;

	@BeforeAll
	private static void setUp() throws Exception{
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();


	}

	@BeforeEach
	private void setUpPerTest() throws Exception {
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");	

		dataBasePrepareService.clearDataBaseEntries();
	}

	@AfterAll
	private static void tearDown(){

	}

	@Test
	public void testParkingACar(){
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processIncomingVehicle();
		//TODO: check that a ticket is actually saved in DB and Parking table is updated with availability
		int parkingNumber = 0;

		try {
			Connection con = null;
			con = dataBaseTestConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.TEST_TICKET);
			ps.setString(1,"ABCDEF");
			ResultSet rs = ps.executeQuery();
			Assert.assertTrue(rs.next());
			if(rs.next()){
				parkingNumber = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Connection con = null;
			con = dataBaseTestConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.TEST_SPOT);
			ps.setInt(1, parkingNumber);
			ResultSet rsparking = ps.executeQuery();
			if(rsparking.next()) {
				Assert.assertEquals(rsparking.getInt(2), 0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//refaire  il n'y a pas d'enregistrement outtime
	@Test
	public void testParkingLotExit(){
		testParkingACar();
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processExitingVehicle();
	//TODO: check that the fare generated and out time are populated correctly in the database
		
		
		
		int fare = 0;
		try {
			Connection con = null;
			con = dataBaseTestConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.TEST_FARE);
            ps.setInt(1, fare);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return;
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		try {
			Connection con = null;
			con = dataBaseTestConfig.getConnection();
			PreparedStatement psupdate = con.prepareStatement(DBConstants.TEST_SPOT);
	//		Boolean spot = false;
		//	psupdate.setBoolean(spot, false);
			ResultSet rsupdate = psupdate.executeQuery();
			
			
			
			if (rsupdate.next()) {
				Assert.assertEquals(1.5, 0);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
