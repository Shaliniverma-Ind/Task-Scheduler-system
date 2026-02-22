import java.sql.Connection;
import java.sql.Statement;

public class HistoryTable {
    public static void main(String[] args) {
        String sql = "CREATE TABLE IF NOT EXISTS historical_projects (" +
                "id SERIAL PRIMARY KEY, " +
                "title VARCHAR(100), " +
                "deadline INT, " +
                "revenue INT, " +
                "completion_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement()) {

            if (con != null) {
                stmt.execute(sql);
                System.out.println("Historical projects table created successfully.");

                // Add some dummy data for predictive analysis
                String dumpData = "INSERT INTO historical_projects (title, deadline, revenue) VALUES " +
                        "('Small UI Fix', 2, 5000), " +
                        "('E-commerce Backend', 5, 80000), " +
                        "('Mobile App Design', 3, 40000), " +
                        "('Legacy Migration', 10, 120000), " +
                        "('API Integration', 4, 30000), " +
                        "('Database Optimization', 2, 25000), " +
                        "('Security Audit', 1, 50000), " +
                        "('Cloud Deployment', 7, 70000), " +
                        "('Frontend Refactor', 5, 15000), " +
                        "('Unit Test Suite', 3, 10000);";
                stmt.execute(dumpData);
                System.out.println("Mock history data populated.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}