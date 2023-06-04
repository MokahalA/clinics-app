package com.example.seg2105project;

import org.junit.Test;
import static org.junit.Assert.*;

public class ServiceClassTest {

    //Testing the methods of the the class "Service"

    @Test
    public void test_getId(){
        Service s = new Service("10","Physiotherapy", "Doctor", 2.0 );

        assertEquals("10", s.getId());
    }

    @Test
    public void test_setId(){
        Service s = new Service("10","Physiotherapy", "Doctor", 2.0);
        s.setId("11");
        assertEquals("11", s.getId());
        assertNotEquals("10", s.getId() );
    }

    @Test
    public void test_getName(){
        Service s = new Service("10","Physiotherapy", "Doctor",2.0 );

        assertEquals("Physiotherapy", s.getName());
    }

    @Test
    public void test_setName(){
        Service s = new Service("10","Physiotherapy", "Doctor",2.0 );
        s.setName("Blood test");
        assertEquals("Blood test", s.getName());
        assertNotEquals("Physiotherapy", s.getName() );
    }

    @Test
    public void test_getRole(){
        Service s = new Service("10","Physiotherapy", "Doctor" ,2.0);

        assertEquals("Doctor", s.getRole());
    }

    @Test
    public void test_setRole(){
        Service s = new Service("10","Physiotherapy", "Doctor",2.0 );
        s.setRole("Nurse");
        assertEquals("Nurse", s.getRole());
        assertNotEquals("Doctor", s.getRole());
    }

    @Test //Test added for Deliverable 4
    public void test_getRate(){
        Service s = new Service("10","Physiotherapy", "Doctor",2.0 );
        assertEquals(s.getRate(), 2.0, 0);
    }


    @Test //Test added for Deliverable 4
    public void test_setRate(){
        Service s = new Service("10","Physiotherapy", "Doctor",2.0 );
        s.setRate(5.0);
        assertEquals(5.0, s.getRate(), 3.0);
        assertNotEquals(2.0, s.getRate());
    }

}
