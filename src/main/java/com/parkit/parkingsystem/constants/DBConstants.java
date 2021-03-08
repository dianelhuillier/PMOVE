package com.parkit.parkingsystem.constants;

public class DBConstants {

    public static final String GET_NEXT_PARKING_SPOT = "select min(PARKING_NUMBER) from parking where AVAILABLE = true and TYPE = ?";
    public static final String UPDATE_PARKING_SPOT = "update parking set available = ? where PARKING_NUMBER = ?";

    public static final String SAVE_TICKET = "insert into ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME, VIP) values(?,?,?,?,?,?)";
    public static final String UPDATE_TICKET = "update ticket set PRICE=?, OUT_TIME=? where ID=?";
    public static final String GET_TICKET = "select t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, t.VIP, p.TYPE from ticket t,parking p where p.parking_number = t.parking_number and t.VEHICLE_REG_NUMBER=? order by t.IN_TIME  limit 1";

    
    
    
    public static final String TEST_UPDATE_TICKET = "update test.ticket set PRICE=?, OUT_TIME=? where ID=1";

    public static final String TEST_TICKET = "select * from ticket where VEHICLE_REG_NUMBER = ?";
    public static final String TEST_SPOT = "select * from parking where PARKING_NUMBER = ?";
    public static final String TEST_FARE = "select * from ticket where PRICE = ?";
 //   public static final String OUT_TIMING = "UPDATE test.ticket SET OUT_TIME= '2021-03-24 14:59:59' WHERE OUT_TIME=null";

    
    public static final String VIP = "SELECT COUNT(*) FROM ticket where VEHICULE_REG_NUMBER = ?";
    //SELECT COUNT(*) FROM prod.ticket WHERE VEHICLE_REG_NUMBER = "azer";
  

	}

// creer la constante puis dao
