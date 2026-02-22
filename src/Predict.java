import java.sql.*;
import java.util.*;

public class Predict {

    /**
     * Predictive Model: Calculates a "Strategic Value" for a project.
     * Strategic Value prioritizes projects that expire soon and have high density.
     */
    /**
     * Predictive Model: Calculates a "Strategic Value" for a project.
     * Strategic Value prioritizes projects that expire soon and have high density.
     */
    public static double calculateStrategicValue(Project p, List<Project> history, int currentDay,
            boolean nextWeekLikelyBetter) {
        if (history.isEmpty()) {
            return p.revenue * (1.0 + (6.0 - Math.min(6, p.deadline)) * 0.2);
        }

        double currentDensity = (double) p.revenue / Math.max(1, p.deadline);

        double urgencyFactor = 1.0;

        // If it MUST be done today or it's gone
        if (p.deadline == currentDay) {
            urgencyFactor += 2.0;
        } else if (p.deadline <= 5) {
            urgencyFactor += (6 - p.deadline) * 0.3;
        }

        // Density boost: If it's more efficient than average, prioritize it
        double avgDensity = history.stream()
                .mapToDouble(hp -> (double) hp.revenue / Math.max(1, hp.deadline))
                .average()
                .orElse(0.0);

        if (currentDensity > avgDensity) {
            urgencyFactor += 0.5;
        }

        // User Requirement:
        // If Next Week IS likely better -> Maximize revenue THIS week (Boost
        // long-deadline projects)
        // If Next Week IS NOT likely better -> Postpone long-deadline high-revenue
        // projects (Penalize them this week)
        if (p.deadline > 5) {
            if (nextWeekLikelyBetter) {
                urgencyFactor += 0.4; // Boost to do it now
            } else {
                urgencyFactor -= 1.0; // Heavy penalty to postpone it
            }
        }

        return p.revenue * urgencyFactor;
    }

    /**
     * Predictive Analysis: Compares the current project pool with historical
     * averages
     * to determine if next week is likely to bring even better offers.
     */
    public static boolean isNextWeekLikelyBetter(List<Project> currentPool, List<Project> history) {
        if (history.isEmpty() || currentPool.isEmpty())
            return false;

        double currentPoolAvgRevenue = currentPool.stream()
                .mapToDouble(p -> p.revenue)
                .average()
                .orElse(0.0);

        double historicalAvgRevenue = history.stream()
                .mapToDouble(hp -> hp.revenue)
                .average()
                .orElse(0.0);

        // If history significantly exceeds current pool profile, next week is likely
        // "Better"
        return historicalAvgRevenue > currentPoolAvgRevenue * 1.1;
    }

    public static List<Project> getHistoryFromDB() {
        List<Project> history = new ArrayList<>();
        String sql = "SELECT name, deadline, revenue FROM historical_projects";

        try (Connection con = DBConnection.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                history.add(new Project(0, rs.getString("name"), rs.getInt("deadline"), rs.getInt("revenue")));
            }
        } catch (Exception e) {
            // Fallback mock history to demonstrate predictive logic if DB is not available
            history.add(new Project(0, "Mock Project High", 1, 100000));
            history.add(new Project(0, "Mock Project Mid", 3, 50000));
            history.add(new Project(0, "Mock Project Low", 5, 20000));
        }
        return history;
    }
}
