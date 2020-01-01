package geneticalgorithm;

/**
 * @author Drayton Williams 
 * The purpose of this class is to create population of
 * CityRoam objects
 */
public class SetupPop {

    CityRoam[] roams; // Contains all individuals
    private double avgFit; // average population fitness per generation
    private double totFit;

    public SetupPop(boolean init, int size) {
        avgFit = 0;
        totFit = 0;
        roams = new CityRoam[size];

        if (init) { // if true, creates new population with random individuals
            for (int i = 0; i < roams.length; i++) {
                CityRoam newIndividual = new CityRoam();
                newIndividual.createIndivdual();
                roams[i] = newIndividual;
            }
        }
    }

    /**
     * Obtains the size of the population
     *
     * @return int the size of the population
     */
    public int size() {
        return roams.length;
    }

    /**
     * Obtains the fittest individual from the population
     *
     * @return CityRoam the fittest individual
     */
    public CityRoam obtainFittest() {
        CityRoam curFit = roams[0]; // current best fitness

        totFit += roams[0].getDist();
        for (int i = 1; i < size(); i++) {
            totFit += roams[i].getDist();
            if (curFit.getFit() <= getRoam(i).getFit()) {
                curFit = getRoam(i); // update the current best fitness
            }
        }
        return curFit;
    }

    /**
     * Obtains CityRoam object from specific index in population
     *
     * @return CityRoam the CityRoam object at the specified index
     * @param a the index at which to obtain a CityRoam object
     */
    public CityRoam getRoam(int a) {
        return roams[a];
    }

    /**
     * Sets population at specific index to a new CityRoam object
     *
     * @param r the CityRoam object to be added to population
     * @param a the index at which to add the CityRoam object
     */
    public void setRoam(CityRoam r, int a) {
        roams[a] = r;
    }

    /**
     * Returns the average population fitness per generation (as a distance)
     *
     * @return double the average fitness
     */
    public double getAvg() {
        avgFit = totFit / size();
        return avgFit;
    }
}