package geneticalgorithm;

/**
 * @author Drayton Williams 
 * The purpose of this class is to create a list of all
 * possible destinations from the provided TSP file
 */
import java.util.ArrayList;

public class RoamPaths {

    protected static ArrayList<City> roamPaths = new ArrayList<>();

    /**
     * Returns the city at the provided index
     *
     * @return City the city at provided index
     * @param index the index at which to obtain a specific city
     *
     */
    public static City getCity(int index) {
        return roamPaths.get(index);
    }

    /**
     * Adds a city to the full list of possible cities
     *
     * @param city the city object to be added
     */
    public static void addCity(City city) {
        roamPaths.add(city);
    }

    /**
     * Returns total number of cities in the TSP
     *
     * @return int the total number of cities
     */
    public static int cityCount() {
        return roamPaths.size();
    }
}
