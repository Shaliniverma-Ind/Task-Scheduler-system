import java.util.*;

public class Scheduler {

    public static void schedule(List<Project> projects) {

        projects.sort((a, b) -> b.getRevenue() - a.getRevenue());

        boolean[] slot = new boolean[5];
        Project[] result = new Project[5];

        for (Project p : projects) {

            for (int j = Math.min(5, p.getDeadline()) - 1; j >= 0; j--) {

                if (!slot[j]) {
                    slot[j] = true;
                    result[j] = p;
                    break;
                }
            }
        }

        int totalRevenue = 0;

        System.out.println("\nFinal Weekly Schedule:");

        for (int i = 0; i < 5; i++) {

            if (result[i] != null) {
                System.out.println("Day " + (i + 1) + " -> "
                        + result[i].getTitle()
                        + " | Revenue: "
                        + result[i].getRevenue());

                totalRevenue += result[i].getRevenue();
            } else {
                System.out.println("Day " + (i + 1) + " -> Free");
            }
        }

        System.out.println("\nTotal Revenue: " + totalRevenue);
    }
}
