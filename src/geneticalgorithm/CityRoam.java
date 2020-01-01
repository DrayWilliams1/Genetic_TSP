package geneticalgorithm;

/**
 * @author Drayton Williams 
 * The purpose of this class is to create a randomized
 * individual roam through all the cities from the TSP file.
 */
import java.util.*;

public class CityRoam {

    private ArrayList<City> roam = new ArrayList<>();
    private double fitness = 0;
    private double dist = 0;

    public CityRoam() {
        for (int i = 0; i < RoamPaths.cityCount(); i++) {
            roam.add(null);
        }
    }

    public CityRoam(ArrayList r) {
        this.roam = r;
    }

    /**
     * Returns the city at the provided index in the individual
     *
     * @return City the city at provided index in the individual
     * @param roamIndex the index at which to obtain a specific city
     *
     */
    public City getCity(int roamIndex) {
        return (City) roam.get(roamIndex);
    }

    /**
     * Sets a City object to the specified index in the individual
     *
     * @param pos the position at which to insert a City object
     * @param c the City object to be set
     */
    public void setCity(int pos, City c) {
        roam.set(pos, c);
        dist = 0;
        fitness = 0;
    }

    /**
     * Gets the total distance traveled in this roam configuration
     *
     * @return double distance traveled
     */
    public double getDist() {
        if (dist == 0) {
            int roamDist = 0;
            for (int i = 0; i < roamLength(); i++) {
                City cityStart = getCity(i); // City being travelled from
                City cityEnd;

                if (i + 1 < roamLength()) {
                    cityEnd = getCity(i + 1);
                } else {
                    cityEnd = getCity(0);
                }
                roamDist += cityStart.eucDistanceTo(cityEnd);
            }
            dist = roamDist;
        }
        return dist;
    }

    /**
     * Gets the fitness of this individual
     *
     * @return double the fitness
     */
    public double getFit() {
        if (fitness == 0) {
            fitness = 1 / getDist();
        }
        return fitness;
    }

    /**
     * Sets a City object to the specified index in the individual
     *
     * @return int the number of City objects in the individual
     */
    public int roamLength() {
        return roam.size();
    }

    /**
     * Initializes the individual with a random order of City objects
     */
    public void createIndivdual() {
        for (int i = 0; i < RoamPaths.cityCount(); i++) {
            setCity(i, RoamPaths.getCity(i));
        }
        Collections.shuffle(roam); // randomizes the order
    }

    /**
     * Checks if the roam already contains the specified city
     *
     * @return boolean returns true if City object is already contained within
     * individual or false if not
     * @param c the City object to be checked for
     */
    public boolean containsCity(City c) {
        return roam.contains(c);
    }

    @Override
    public String toString() {
        String geneListing = "||";

        for (int a = 0; a < roamLength(); a++) {
            geneListing += getCity(a) + "||";
        }
        return geneListing;
    }
}
