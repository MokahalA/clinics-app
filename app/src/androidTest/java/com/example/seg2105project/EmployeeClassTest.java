package com.example.seg2105project;

import org.junit.Test;
import static org.junit.Assert.*;

//Testing some of the methods of the class "Employee"

public class EmployeeClassTest {

    @Test
    public void test_isInitialized(){
        Employee e = new Employee("Ahmad", "Mokahal", "test", "testpass");
        assertEquals("Testing getName", e.isInitialized(),false);
        //Employee Accounts are have their initialized attribute set to false by default because their
        // profile is incomplete when the profile is first created.
    }

    @Test
    public void test_setInitialized(){
        Employee e = new Employee("Ahmad", "Mokahal", "test", "testpass");
        e.setInitialized(true);
        assertNotEquals("Testing setInitialized to see that the attribute can be changed", e.isInitialized(),false);
        assertEquals("Testing to see if initialized attribute has changed", e.isInitialized(), true);
    }


}
