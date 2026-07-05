import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class FixDB {
    public static void main(String[] args) throws Exception {
        System.out.println("Connecting to database...");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/placement_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "root", "root");
        Statement stmt = conn.createStatement();
        int rows = stmt.executeUpdate("DELETE FROM flyway_schema_history WHERE success = 0");
        System.out.println("Deleted " + rows + " failed flyway migrations.");
    }
}
