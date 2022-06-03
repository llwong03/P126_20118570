package gui.assignment.pkg2;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author lachl
 */
public class Test {
    private Test test;
    public Test(){

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Test = new Test();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLogin1() {
        System.out.println("test user login");
        String username = "admin";
        String password = "admin";
        if (username.equals("") || password.equals("")) {
            System.out.println("invalid to compare with database");
        } else {
            System.out.println("valid to check with database");
        }
    }

    @Test
    public void testLogin2() {
        System.out.println("test user login");
        String username = "admin";
        String password = "";
        if (username.equals("") || password.equals("")) {
            System.out.println("invalid to compare with database");
        } else {
            System.out.println("valid to check with database");
        }
    }

    @Test
    public void testAddInput1() {
        System.out.println("test add wheel");
        String diameter = "";
        String width = "";
        String boltPattern = "";
        String offset = "";
        String instock = "";
        if (diameter.equals("") || width.equals("") || boltPattern.equals("") || offset.equals("") || instock.equals("")){
            System.out.println("invalid to add to database");
        } else {
            System.out.println("valid to add to database");
        }

    }

    @Test
    public void testAddInput2() {
        System.out.println("test add wheel");
        String diameter = "17";
        String width = "7.5";
        String boltPattern = "5x114.3";
        String offset = "22";
        String instock = "false";
        if (diameter.equals("") || width.equals("") || boltPattern.equals("") || offset.equals("") || instock.equals("")){
            System.out.println("invalid to add to database");
        } else {
            System.out.println("valid to add to database");
        }

    }
}

