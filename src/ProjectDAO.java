import java.sql.*;
import java.util.*;

public class ProjectDAO {

    public List<Project> getAllProjects() {

        List<Project> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM projects")) {

            while (rs.next()) {
                list.add(new Project(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("deadline"),
                        rs.getInt("revenue")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Integer> getPastRevenues() {

        List<Integer> revenues = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT total_revenue FROM weekly_history")) {

            while (rs.next()) {
                revenues.add(rs.getInt("total_revenue"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return revenues;
    }
}
