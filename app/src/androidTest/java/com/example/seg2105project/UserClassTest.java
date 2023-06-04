package com.example.seg2105project;


import org.junit.Test;
import static org.junit.Assert.*;

//Testing all the methods of the class "User"

public class UserClassTest {

    @Test
    public void test_getFirstName(){
        User u = new User("Ahmad", "El Mokahal", "amokahal","testpass", "Employee" );
        assertEquals("Testing getFirstName", u.getFirstName(),"Ahmad");
    }

    @Test
    public void test_setFirstName(){
        User u = new User("Ahmad", "El Mokahal", "amokahal","testpass", "Employee" );
        u.setFirstName("NewName");
        assertNotEquals("Testing setFirstName to see that the name can be changed", u.getFirstName(),"Ahmad");
        assertEquals("Testing to see if name has changed", u.getFirstName(), "NewName");
    }

    @Test
    public void test_getLastName(){
        User u = new User("Ahmad", "El Mokahal", "amokahal","testpass", "Employee" );
        assertEquals("Testing getLastName", u.getLastName(),"El Mokahal");
    }

    @Test
    public void test_setLastName(){
        User u = new User("Ahmad", "El Mokahal", "amokahal","testpass", "Employee" );
        u.setLastName("NewName");
        assertNotEquals("Testing setLastName to see that the name can be changed", u.getLastName(),"Ahmad");
        assertEquals("Testing to see if last name has changed", u.getLastName(), "NewName");
    }

    @Test
    public void test_getUserName(){
        User u = new User("Ahmad", "El Mokahal", "amokahal","testpass", "Employee" );
        assertEquals("Testing getUserName", u.getUserName(),"amokahal");
    }

    @Test
    public void test_setUserName(){
        User u = new User("Ahmad", "El Mokahal", "amokahal","testpass", "Employee" );
        u.setUserName("NewUserName");
        assertNotEquals("Testing setUserName to see that the username can be changed", u.getUserName(),"Ahmad");
        assertEquals("Testing to see if username has changed", u.getUserName(), "NewUserName");
    }

    @Test
    public void test_getPassword(){
        User u = new User("Ahmad", "El Mokahal", "amokahal","testpass", "Employee" );
        assertEquals("Testing getPassword", u.getPassword(),"testpass");
    }

    @Test
    public void test_setPassword(){
        User u = new User("Ahmad", "El Mokahal", "amokahal","testpass", "Employee" );
        u.setPassword("NewPass");
        assertNotEquals("Testing setPassword to see that the password can be changed", u.getPassword(),"Ahmad");
        assertEquals("Testing to see if password has changed", u.getPassword(), "NewPass");
    }

    @Test
    public void test_getType(){
        User u = new User("Ahmad", "El Mokahal", "amokahal","testpass", "Employee" );
        assertEquals("Testing getType", u.getType(),"Employee");
    }

    @Test
    public void test_setType(){
        User u = new User("Ahmad", "El Mokahal", "amokahal","testpass", "Employee" );
        u.setType("Patient");
        assertNotEquals("Testing setType to see that the user type can be changed", u.getType(),"Ahmad");
        assertEquals("Testing to see if user type has changed", u.getType(), "Patient");
    }


}
