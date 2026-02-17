import java.util.List;

public class ForecastService {

    public double predictNextWeekRevenue(List<Integer> pastRevenues) {

        if (pastRevenues.size() == 0)
            return 0;

        double sum = 0;

        for (int r : pastRevenues) {
            sum += r;
        }

        double average = sum / pastRevenues.size();

        // Simulated AI trend growth
        return average * 1.1;
    }
}
