import java.sql.*;


/**
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
            System.out.println("Worked!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // ceck if table exists
        try {
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String tableName = "WHEELS";

            if (!checkIfTableExists(tableName)) {
                statement.executeUpdate("CREATE TABLE " + tableName + " (diameter INTEGER, width DOUBLE, bp VARCHAR(7), offset INT");
            }
            statement.close();
        } catch (Throwable e) {
            System.out.println("error");
        }


        String tableCreate = "CREATE TABLE WHEELS (DIAMETER INT, WIDTH DOUBLE, BP VARCHAR(7), offset INT);";
        String insertData = "INSERT INTO WHEELS VALUES " +
                "(18, 8.5, '5x120.0;, 35), " +
                "(17, 9.0, '5x114.3', 25);";
    }

    public String select = "SELECT * FROM WHEELS;";

    public static void main(String[] args) {
        try {
            Test test = new Test();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
//        System.out.println(tableCreate);
//        System.out.println(insertData);
//        System.out.println(select);
    }

    // make this simpler
    private boolean checkIfTableExists(String newTableName) {
        boolean flag = false;
        try {
            System.out.println("check existing tables.... ");
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);
            //Statement dropStatement=null;
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
        } catch (SQLException ex) {
        }
        return flag;
    }

}
