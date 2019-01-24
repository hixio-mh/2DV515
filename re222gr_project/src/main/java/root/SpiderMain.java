package root;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SpiderMain {
    public static final String url = "jdbc:sqlite:C:\\Users\\rjosi\\Documents\\workspace-sts-3.9.6.RELEASE\\re222gr_project\\myDB.db";

	public static void main(String[] args) {
		createNewDatabase();
		createNewTable();
		Spider spider = new Spider();
		spider.getURL("https://sv.wikipedia.org/wiki/Donald_Trump");
		System.out.println("finished!");
	}

	public static void createNewTable() {        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS links (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	body text \n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
    public static void insert(String name, String body) {
        String sql = "INSERT INTO links(name,body) VALUES(?,?)";
 
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, body);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

	public static void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
