import java.util.*;

public class Main {

    public static void main(String[] args) {

        ProjectDAO dao = new ProjectDAO();

        List<Project> projects = dao.getAllProjects();
        List<Integer> history = dao.getPastRevenues();

        ForecastService forecast = new ForecastService();
        double predictedRevenue = forecast.predictNextWeekRevenue(history);

        System.out.println("Predicted Next Week Revenue: " + predictedRevenue);

        DecisionEngine engine = new DecisionEngine();
        List<Project> filteredProjects =
                engine.filterProjects(projects, predictedRevenue);

        Scheduler.schedule(filteredProjects);
    }
}
