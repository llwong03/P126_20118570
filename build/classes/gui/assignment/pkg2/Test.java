/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.assignment.pkg2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



/**
 *
 * @author lachl
 */
public class Test {
    
    public static String url = "jdbc:derby:GUIDB;create=true;";
    public static String user = "user";
    public static String password = "password";
    
    private Connection conn;
    
    public Test() throws SQLException {
        try {
            conn = DriverManager.getConnection(url, user, passord);
            System.out.println("Worked!");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        // ceck if table exists
        try {
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement;
            String tableName = "WHEELS";
            
            if (!checkTableExists(tableName)) {
                statement.executeUpdate("CREATE TABLE " + tableName + " (diameter INTEGER, width DOUBLE, bp VARCHAR(7), offset INT");
            }
            statement.close();
        } catch (Throwable e){
            System.out.println("error");
        }


        public static String tableCreate = "CREATE TABLE WHEELS (DIAMETER INT, WIDTH DOUBLE, BP VARCHAR(7), offset INT);";
        public static String insertData = "INSERT INTO WHEELS VALUES " +
                "(18, 8.5, '5x120.0;, 35), " +
                "(17, 9.0, '5x114.3', 25);";
    }
    public String select = "SELECT * FROM WHEELS;";
    
    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(tableCreate);
        System.out.println(insertData);
        System.out.println(select);
    }
}
