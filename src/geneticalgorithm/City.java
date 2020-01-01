package geneticalgorithm;

/**
 * @author Drayton Williams 
 * The purpose of this class is to create city objects
 * from data obtained from an inputed traveling salesman problem (TSP) file
 */
import java.util.regex.*;

public class City {

    String cityNum;
    double x; // x-coordinate
    double y; // y-coordinate

    public City(String input) {
        String in = input;

        // Regex allows for the extraction of the city number from the inputted 
        // string
        Pattern p1 = Pattern.compile("\\d+");
        Matcher m1 = p1.matcher(in);
        if (m1.find()) {
            cityNum = String.valueOf(m1.group());
        }
        // Allows for the extraction of the coordinates (as doubles) from the
        // inputted string
        Pattern p2 = Pattern.compile("(\\d+(?:\\.\\d+))");
        Matcher m2 = p2.matcher(in);
        if (m2.find()) {
            x = Double.parseDouble(m2.group());
        }
        if (m2.find()) {
            y = Double.parseDouble(m2.group());
        }
    }

    /**
     * Returns the city number
     *
     * @return String the city number
     */
    public String getCityNum() {
        return cityNum;
    }

    /**
     * Returns the x-coordinate of the city
     *
     * @return double the x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the city
     *
     * @return double the y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Computes the Euclidean distance to the provided city
     *
     * @return double the Euclidean distance to the inputted city
     * @param c the city to find the distance to
     *
     */
    public double eucDistanceTo(City c) {
        double xDist = Math.abs(getX() - c.getX());
        double yDist = Math.abs(getY() - c.getY());

        double distance = Math.sqrt((xDist * xDist) + (yDist * yDist));

        return distance;
    }

    @Override
    public String toString() {
        return getX() + ", " + getY();
    }
}
