package com.example.seg2105project;

import org.junit.Test;
import static org.junit.Assert.*;

//Testing some of the methods of the class "Clinic"

public class ClinicClassTest {

    @Test
    public void test_getName(){
        Clinic c = new Clinic("Test Clinic", "test address", "test num", true, true, true, true, true, true, 0 );
        assertEquals("Testing getName", c.getName(),"Test Clinic");
    }

    @Test
    public void test_setName(){
        Clinic c = new Clinic("Test Clinic", "test address", "111", true, true, true, true, true, true,0 );
        c.setName("NewName");
        assertNotEquals("Testing setName to see that the name can be changed", c.getName(),"Test Clinic");
        assertEquals("Testing to see if name has changed", c.getName(), "NewName");
    }

    @Test
    public void test_getAddress(){
        Clinic c = new Clinic("Test Clinic", "test address", "111", true, true, true, true, true, true,0 );
        assertEquals("Testing getAddress", c.getAddress(),"test address");
    }

    @Test
    public void test_setAddress(){
        Clinic c = new Clinic("Test Clinic", "test address", "111", true, true, true, true, true, true,0 );
        c.setAddress("NewAddress");
        assertNotEquals("Testing setAddress to see that the address can be changed", c.getAddress(),"test address");
        assertEquals("Testing to see if address has changed", c.getAddress(), "NewAddress");
    }


    @Test
    public void test_getPhoneNum(){
        Clinic c = new Clinic("Test Clinic", "test address", "111", true, true, true, true, true, true ,0 );
        assertEquals("Testing getName", c.getPhoneNum(),"111");
    }

    @Test
    public void test_setPhoneNum(){
        Clinic c = new Clinic("TestClinic", "test address", "111", true, true, true, true, true, true ,0);
        c.setPhoneNum("222");
        assertNotEquals("Testing setPhoneNum to see that the phone number can be changed", c.getName(),"111");
        assertEquals("Testing to see if phone number has changed", c.getPhoneNum(), "222");
    }


    @Test //Test added for Deliverable 4
    public void test_getCredit(){
        Clinic c = new Clinic("TestClinic", "test address", "111", true, true, true, true, true, true,0 );
        assertEquals(c.getCredit(), true);
    }


    @Test //Test added for Deliverable 4
    public void test_setCredit(){
        Clinic c = new Clinic("TestClinic", "test address", "111", true, true, true, true, true, true,0 );
        c.setCredit(false);
        assertNotEquals(c.getCredit(), true);
        assertEquals(c.getCredit(), false);

    }

    @Test //Test added for Deliverable 4
    public void test_getDebit(){
        Clinic c = new Clinic("TestClinic", "test address", "111", true, true, true, true, true, true ,0);
        assertEquals(c.getDebit(), true);
    }

    @Test //Test added for Deliverable 4
    public void test_setDebit(){
        Clinic c = new Clinic("TestClinic", "test address", "111", true, true, true, true, true, true,0 );
        c.setDebit(false);
        assertNotEquals(c.getDebit(), true);
        assertEquals(c.getDebit(), false);

    }

    @Test //Test added for Deliverable 4
    public void test_getCash(){
        Clinic c = new Clinic("TestClinic", "test address", "111", true, true, true, true, true, true ,0);
        assertEquals(c.getCash(), true);
    }

    @Test //Test added for Deliverable 4
    public void test_setCash(){
        Clinic c = new Clinic("TestClinic", "test address", "111", true, true, true, true, true, true ,0);
        c.setCash(false);
        assertNotEquals(c.getCash(), true);
        assertEquals(c.getCash(), false);

    }

    @Test //Test added for Deliverable 4
    public void test_getOHIP(){
        Clinic c = new Clinic("TestClinic", "test address", "111", true, true, true, true, true, true ,0);
        assertEquals(c.getOHIP(), true);
    }

    @Test //Test added for Deliverable 4
    public void test_setOHIP(){
        Clinic c = new Clinic("TestClinic", "test address", "111", true, true, true, true, true, true ,0);
        c.setOHIP(false);
        assertNotEquals(c.getOHIP(), true);
        assertEquals(c.getOHIP(), false);

    }

    @Test //Test added for Deliverable 4
    public void test_getPubIns(){
        Clinic c = new Clinic("TestClinic", "test address", "111", true, true, true, true, true, true,0 );
        assertEquals(c.getPubIns(), true);
    }

    @Test //Test added for Deliverable 4
    public void test_setPubIns(){
        Clinic c = new Clinic("TestClinic", "test address", "111", true, true, true, true, true, true ,0);
        c.setPubIns(false);
        assertNotEquals(c.getPubIns(), true);
        assertEquals(c.getPubIns(), false);

    }

    @Test //Test added for Deliverable 4
    public void test_getPriIns(){
        Clinic c = new Clinic("TestClinic", "test address", "111", true, true, true, true, true, true,0 );
        assertEquals(c.getPriIns(), true);
    }

    @Test //Test added for Deliverable 4
    public void test_setPriIns(){
        Clinic c = new Clinic("TestClinic", "test address", "111", true, true, true, true, true, true,0 );
        c.setPriIns(false);
        assertNotEquals(c.getPriIns(), true);
        assertEquals(c.getPriIns(), false);

    }


    @Test  //Test added for Deliverable 4
    public void test_addService(){
        Service s = new Service("12", "check", "doctor", 30.0);
        Clinic c = new Clinic("Test Clinic", "test address", "test num", true, true, true, true, true, true ,0);
        c.addService(s);
        Service testService = c.getServicesOffered().get(0); //Obtains the service that was added
        assertEquals(testService, s); //Compares the services
    }

    @Test //Test added for Deliverable 4
    public void test_removeService(){
        Service s = new Service("12", "check", "doctor", 30.0);
        Clinic c = new Clinic("Test Clinic", "test address", "test num", true, true, true, true, true, true, 0 );
        c.addService(s);
        c.removeService(s);
        assertEquals(c.getServicesOffered().size(), 0); //Compares size of the arraylist with the expected size
    }

    @Test  //Test added for Deliverable 4
    public void test_getWait(){
        Service s = new Service("12", "check", "doctor", 30.0);
        Clinic c = new Clinic("Test Clinic", "test address", "test num", true, true, true, true, true, true ,0);
        assertEquals(c.getWait(), 0); //Compares the expected wait time with the actual value obtained using getter
    }

    @Test //Test added for Deliverable 4
    public void test_setWait(){
        Service s = new Service("12", "check", "doctor", 30.0);
        Clinic c = new Clinic("Test Clinic", "test address", "test num", true, true, true, true, true, true, 0 );
        c.setWait(5); // Testing setter method
        assertEquals(c.getWait(), 5); //Compares the expected wait time with the actual value obtained using getter
    }


}
