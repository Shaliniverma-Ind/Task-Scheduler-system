import java.util.ArrayList;
import java.util.List;

public class SchedulerPredictiveTest {
    public static void main(String[] args) {
        List<Project> projects = new ArrayList<>();
        // High revenue projects in the current pool to make history look "worse"
        projects.add(new Project(1, "Premium Website", 1, 250000));
        projects.add(new Project(2, "Enterprise App", 5, 300000));
        // High revenue long deadline project that SHOULD be deferred
        projects.add(new Project(3, "Future Expansion", 10, 200000));
        // Urgent low-value projects to fill the week
        projects.add(new Project(4, "Urgent Bug 1", 3, 1000));
        projects.add(new Project(5, "Urgent Bug 2", 4, 1000));
        projects.add(new Project(6, "Urgent Bug 3", 5, 1000));

        System.out.println("Running Predictive Test: Next Week is likely SLOWER...");
        Scheduler.generateSchedule(projects);
    }
}
