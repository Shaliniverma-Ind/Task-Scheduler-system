import java.util.*;

public class DecisionEngine {

    public List<Project> filterProjects(List<Project> projects, double predictedRevenue) {

        List<Project> accepted = new ArrayList<>();

        double threshold = predictedRevenue / 5; // expected per day revenue

        for (Project p : projects) {
            if (p.getRevenue() >= threshold) {
                accepted.add(p);
            }
        }

        return accepted;
    }
}
