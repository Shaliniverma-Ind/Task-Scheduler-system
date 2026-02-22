import java.util.*;

public class Scheduler {

    private static final int MAX_DAYS = 5;

    public static void generateSchedule(List<Project> projects) {
        List<Project> history = Predict.getHistoryFromDB();

        // Filter invalid projects
        projects.removeIf(p -> p.deadline <= 0 || p.revenue <= 0);

        // Predictive Analysis: Is next week likely better?
        boolean nextWeekLikelyBetter = Predict.isNextWeekLikelyBetter(projects, history);

        List<Project> scheduledProjects = new ArrayList<>();
        Set<Integer> assignedIds = new HashSet<>();

        System.out.println("\n PREDICTIVE WEEKLY PROJECT SCHEDULE");
        System.out.println("------------------------------------");
        System.out.println("Predictive Signal: Next Week is likely " + (nextWeekLikelyBetter ? "BETTER" : "SLOWER")
                + " than this week.");

        String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
        int totalRevenue = 0;

        for (int day = 1; day <= MAX_DAYS; day++) {
            Project bestProject = null;
            double bestScore = -Double.MAX_VALUE;

            for (Project p : projects) {
                if (assignedIds.contains(p.id))
                    continue;

                // Rule 1: Project must be completable by this day or later (deadline >= day)
                if (p.deadline < day)
                    continue;

                double score = Predict.calculateStrategicValue(p, history, day, nextWeekLikelyBetter);

                if (score > bestScore) {
                    bestScore = score;
                    bestProject = p;
                }
            }

            if (bestProject != null) {
                assignedIds.add(bestProject.id);
                scheduledProjects.add(bestProject);

                String tag = (bestProject.deadline > MAX_DAYS) ? " [Deferred Potential]" : " [Urgent]";
                System.out.println(days[day - 1] + " \u2192 " + bestProject.title +
                        " (Deadline: " + bestProject.deadline + ", \u20B9" + bestProject.revenue + ") [Score: "
                        + String.format("%.2f", bestScore)
                        + "]" + tag);
                totalRevenue += bestProject.revenue;
            } else {
                System.out.println(
                        days[day - 1] + " \u2192 No Project available for this slot");
            }
        }

        System.out.println("\n\uD83D\uDCB0 Total Estimated Revenue: \u20B9" + totalRevenue);
        System.out.println(
                "\uD83D\uDCC8 Predictive Analysis: Adjusted for deadline constraints and historical opportunity costs.");
    }
}
