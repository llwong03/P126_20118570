package gui.assignment.pkg2;

import java.sql.*;
import java.util.Scanner;

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
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String wheelsTable = "Wheels";
            String loginTable = "Logins";

            if (!checkTableExists(loginTable)) {
                String sql = "CREATE TABLE \"" + loginTable + "\"(username VARCHAR(12), password VARCHAR(20))";
                statement.executeUpdate(sql);
                statement.execute("INSERT INTO \"Logins\" (username, password) \nVALUES (admin, admin)");
                System.out.println(loginTable + " created");
            }

            if (!checkTableExists(wheelsTable)) {
                String sql = "CREATE TABLE \"" + wheelsTable + "\" (diameter INT, width DOUBLE, bp VARCHAR(8), offset INT, inStock BOOLEAN, productNo INT)";
                statement.executeUpdate(sql);
                System.out.println(wheelsTable + " created");
                populateWheels();
            }
            statement.close();
            System.out.println("Working!");
        } catch (SQLException e){
            e.printStackTrace();
        }
        String searchItems = "SELECT COUNT(*) FROM \"Wheels\"";

        System.out.println(searchItems);
        PreparedStatement ps = conn.prepareStatement(searchItems);

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        System.out.println(count);
        login();
        searchSpecific();
    }

    public void populateWheels(){
        String[][] data = {{"18", "8.5", "5x120.0", "35", "false", "1"}, {"17", "9.0", "5x114.3", "25", "false", "2"}, {"17", "9.0", "5x114.3", "25", "true", "3"}, {"17", "9.0", "5x114.3", "25", "true", "4"}, {"18", "9.0", "5x114.3", "28", "false", "5"}, {"16", "7.0", "4x100.0", "22", "false", "6"}, {"18", "9.0", "5x114.3", "28", "false", "7"}, {"18", "6.0", "5x100.0", "35", "true", "8"}, {"18", "9.0", "5x114.3", "28", "true", "9"}, {"18", "8.0", "5x114.3", "35", "true", "10"}, {"18", "9.0", "5x114.3", "28", "false", "11"}, {"20", "11.0", "5x114.3", "40", "false", "12"}, {"18", "9.0", "5x114.3", "28", "true", "13"}, {"19", "6.0", "4x100.0", "35", "true", "14"}, {"18", "9.0", "5x114.3", "28", "true", "15"}, {"22", "10.0", "5x114.3", "55", "false", "16"}, {"18", "9.0", "5x114.3", "28", "false", "17"}, {"18", "8.0", "5x120.0", "35", "false", "18"}, {"18", "8.0", "5x120.0", "35", "true", "19"}, {"18", "10.0", "5x114.3", "35", "true", "20"}, {"18", "8.5", "5x120.0", "35", "true", "21"}, {"20", "10.0", "5x114.3", "20", "true", "22"}, {"19", "9.0", "5x100.0", "40", "false", "23"}, {"17", "7.5", "5x114.3", "28", "true", "24"}, {"20", "10.0", "5x120.0", "15", "true", "25"}};

        try (Statement statement = conn.createStatement()){
            for (String[] s : data) {
                int diameter = Integer.parseInt(s[0]);
                double width = Double.parseDouble(s[1]);
                String bp = String.valueOf(s[2]);
                int offset = Integer.parseInt(s[3]);
                boolean inStock = s[4].equals("true");
                int productNo = Integer.parseInt(s[5]);

                String query = String.format("INSERT INTO \"Wheels\" (diameter, width, bp, offset, inStock, productNo) VALUES (%d, %f, '%s', %d, %b, %d)", diameter, width, bp, offset, inStock, productNo);
                statement.addBatch(query);
            }

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try{
            Test test = new Test();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void searchSpecific() {
        try (Statement statement = conn.createStatement()){
            Scanner scanner = new Scanner(System.in);
            System.out.println("enter diameter: ");
            int inputDiameter = scanner.nextInt();
            System.out.println("enter width: ");
            double inputWidth = scanner.nextDouble();
            System.out.println("enter bolt pattern: ");
            String inputBp = scanner.next();
            System.out.println("enter offset: ");
            int inputOffset = scanner.nextInt();


            String query = ("SELECT * FROM \"Wheels\"");
//            diameter = " + inputDiameter + "AND width = " + inputWidth
//
            if (inputDiameter != -1 || inputWidth != -1 || !inputBp.equalsIgnoreCase("-1") || inputOffset != 0) {
                query += (" WHERE");

                if (inputDiameter != -1) {
                    query += (" diameter = " + inputDiameter);
                }
                if (inputWidth != -1 && inputDiameter == -1){
                    query += (" width = " + inputWidth);
                } else if (inputWidth != -1) {
                    query += (" AND width = " + inputWidth);
                }
                if (!inputBp.equalsIgnoreCase("-1") && inputDiameter == -1 && inputWidth == -1) {
                    query += (" bp = \'" + inputBp + "\'");
                } else if (!inputBp.equalsIgnoreCase("-1")) {
                    query += (" AND bp = \'" + inputBp + "\'");
                }
                if (inputOffset != 0 && inputDiameter == -1 && inputWidth == -1 && inputBp.equals("-1")) {
                    query += (" offset = " + inputOffset);
                } else if(inputOffset != 0) {
                    query += (" AND offset = " + inputOffset);
                }
            } else {
                System.out.println("Please enter parameters");
                return;
            }


            System.out.println(query);
            PreparedStatement ps = conn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            int count = 0;
            while (rs.next()) {
                int diameter = rs.getInt("diameter");
                double width = rs.getDouble("width");
                String bp = rs.getString("bp");
                int offset = rs.getInt("offset");
                boolean inStock = rs.getBoolean("inStock");
                int productNo = rs.getInt("productNo");

                System.out.println("diameter: " + diameter + ", width: " + width + ", bolt pattern: " + bp + ", offset: " + offset + ", in stock: " + inStock + ", product number: " + productNo);
//                String searched = rs.getString("diameter");
//                ouput.add(searched);
                System.out.println(count);
                count += 1;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void login() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute("INSERT INTO \"Logins\" (username, password) \nVALUES ('admin', 'admin')");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Username: ");
            String username = scanner.next();
            System.out.println("Password: ");
            String password = scanner.next();

            String query = ("SELECT * FROM \"Logins\"");
            if (!username.equals(null) && !password.equals(null)) {
                query += " WHERE username = \'" + username + "\' AND password = \'" + password + "\'";
            }
            System.out.println(query);

            PreparedStatement ps = conn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            Boolean confirm = false;
            while (confirm = false) {
                while (rs.next()) {
                    String checkUser = rs.getString("username");
                    String checkPass = rs.getString("password");
                    if (checkUser.equals(username) && checkPass.equals(password)) {
                        confirm = true;
                        System.out.println("login success");
                    }else {
                        System.out.println("Incorrect username or password");
                    }
                }

            }
        }
    }
    private boolean checkTableExists(String newTableName) {
        boolean flag = false;
        try {
            System.out.println("check existing tables.... ");
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    System.out.println(tableName + "  is there");
                    flag = true;
                }
            }
            if (rsDBMeta != null) {
                rsDBMeta.close();
            }
        } catch (SQLException ex) {}
        return flag;
    }



}
