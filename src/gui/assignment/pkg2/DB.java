package gui.assignment.pkg2;

import java.sql.*;

public class DB {
    public static String url = "jdbc:derby:GUIDB;create=true;";
    public static String user = "user";
    public static String password = "password";
    private Connection conn;

    public DB() throws SQLException {
        //on start checks database and tables
        //if not already created, some hardcoded options will run
        //logins by default is admin, admin
        //25 wheels pre-made, will populate the wheels table
        try {
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String tableName = "Wheels";
            String loginTable = "Logins";

            if (!checkTableExists(loginTable)) {
                String sql = "CREATE TABLE \"" + loginTable + "\"(username VARCHAR(12), password VARCHAR(20))";
                statement.executeUpdate(sql);
                statement.execute("INSERT INTO \"Logins\" (username, password) \nVALUES (\'admin\', \'admin\')");
                System.out.println(loginTable + " created");
            }

            if (!checkTableExists(tableName)) {
                String sql = "CREATE TABLE \"" + tableName + "\" (diameter INT, width DOUBLE, bp VARCHAR(8), offset INT, inStock BOOLEAN, productNo INT)";
                statement.executeUpdate(sql);
                populate();
                System.out.println(tableName + " created");

            }
            statement.close();
            System.out.println("Working!");
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void populate(){
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


    private boolean checkTableExists(String newTableName) {
        boolean flag = false;
        try {
            System.out.println("check existing tables.... ");
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
//                System.out.println("Table: " + tableName);
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

    public Connection getConn() {
        return conn;
    }
}
