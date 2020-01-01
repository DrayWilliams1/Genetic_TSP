package geneticalgorithm;

/**
 * @author Drayton Williams 
 * The purpose of this class is to compute a shortest
 * path in the TSP file using a genetic algorithm with elitism, swap mutations,
 * uniform-order crossover (with bit mask) and ordered crossover.
 */
import java.util.*;
import java.io.*;

public class GeneticAlgorithm {

    private static File file;

    private static double mR;   // mutation rate
    private static double cR;   // crossover rate
    private static int k;       // tournament selection of 'k' individuals
    private static String xT;   // crossover type

    // for testing purposes, elitism always true
    private static final boolean ELITISM = true;

    /**
     * Initializes variables for use in the the genetic algorithm
     *
     * @param mRate
     * @param cRate
     * @param tSize
     * @param cType
     */
    public static void varSetup(double mRate, double cRate, int tSize, 
            String cType) {
        mR = mRate;
        cR = cRate;
        k = tSize;
        xT = cType;
    }

    /**
     * Loads the TSP file into the program and creates city objects based on
     * contained data
     *
     * @param fName the name of the file being read
     */
    public static void cityLoader(String fName) {
        try {
            file = new File(fName);
            Scanner in = new Scanner(file);

            String begin = "";
            // Scans through file until node coordinate section is found then 
            // breaks to begin creating city objects from data file
            for (;;) {
                begin = in.nextLine();
                if (begin.equalsIgnoreCase("NODE_COORD_SECTION")) {
                    break;
                }
            }
            String cData = "";
            while (in.hasNextLine()) {
                cData = in.nextLine();
                // Used to nullify the effect of TSP files with an EOF sentinal
                // Therefore, no extra cities will be created if and EOF line
                // is read in
                if (!cData.equalsIgnoreCase("EOF")) {
                    City city = new City(cData);
                    RoamPaths.addCity(city);
                } else {
                    break;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Error reading file");
        } catch (NoSuchElementException e) {
            System.out.println("Error reading file");
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file");
        }
    }

    /**
     * Evolves a population over a generation. If elitism is active, the most
     * fit individual is kept in the first slot of the population and the rest
     * of the population is altered
     *
     * @return SetupPop the population that has been evolved
     * @param p the population to be evolved
     */
    public static SetupPop popEvolve(SetupPop p) {
        SetupPop pop = new SetupPop(false, p.size());

        int eliteGene = 0;
        if (ELITISM) {
            pop.setRoam(p.obtainFittest(), 0);
            eliteGene = 1;
        }

        for (int i = eliteGene; i < pop.size(); i++) {
            CityRoam parentA = tournamentSelect(p);
            CityRoam parentB = tournamentSelect(p);
            
            // switch/case for crossover-type select
            CityRoam offspring = null;
            switch(xT) {
                case "UOX": {
                    offspring = uoCrossover(parentA, parentB);
                    break;
                }
                case "OX": {
                    offspring = oxCrossover(parentA, parentB);
                    break;
                }
            }
            pop.setRoam(offspring, i);
        }

        for (int i = eliteGene; i < pop.size(); i++) {
            mutator(pop.getRoam(i));
        }
        return pop;
    }

    /**
     * Performs a tournament selection on a subset of individuals in the
     * population and returns the most fit individual
     *
     * @return CityRoam the most fit individual (CityRoam)
     * @param p the population to be evolved
     */
    public static CityRoam tournamentSelect(SetupPop p) {
        CityRoam best = null;

        SetupPop tSelect = new SetupPop(false, k);

        for (int i = 0; i < k; i++) {
            int randRoam = (int) (Math.random() * p.size());
            tSelect.setRoam(p.getRoam(randRoam), i);
        }

        best = tSelect.obtainFittest();
        return best;
    }

    /**
     * Performs a swap mutation on an individual in the population
     *
     * @param cr the individual to be mutated
     */
    public static void mutator(CityRoam cr) {
        for (int oneIndex = 0; oneIndex < cr.roamLength(); oneIndex++) {
            double chance = Math.random();

            if (chance < mR) {
                int twoIndex = (int) (Math.random() * cr.roamLength());

                City swapCity1 = cr.getCity(oneIndex);
                City swapCity2 = cr.getCity(twoIndex);

                cr.setCity(oneIndex, swapCity2);
                cr.setCity(twoIndex, swapCity1);
            }
        }
    }

    /**
     * Creates an offspring from two parents using uniform-order crossover (with
     * bit mask)
     *
     * @return CityRoam the offspring created by crossover
     * @param p1 the first parent used for creation
     * @param p2 the second parent used for creation
     */
    public static CityRoam uoCrossover(CityRoam p1, CityRoam p2) {
        CityRoam offspring = new CityRoam();

        int[] bitMask = new int[p1.roamLength()];

        // initializes bit mask to 0 in each index
        for (int i = 0; i < bitMask.length; i++) {
            bitMask[i] = 0;
        }

        // use crossover rate to supply probability of 1's being put in bit mask
        for (int i = 0; i < bitMask.length; i++) {
            double chance = Math.random();

            if (chance < cR) {
                bitMask[i] = 1;
            }
        }

        // If a 1 value found within bit mask, city value from parent 1 copied
        // directly to offspring in the same location
        for (int i = 0; i < bitMask.length; i++) {
            if (bitMask[i] == 1) {
                offspring.setCity(i, p1.getCity(i));
            }
        }

        // Starts back at beginning of offspring and fills any empty spots with
        // remaining values from parent 2 in the order they are obtained from
        // said parent
        for (int i = 0; i < offspring.roamLength(); i++) {
            // if offspring does not yet contain value obtained from parent 2
            if (!offspring.containsCity(p2.getCity(i))) {
                for (int j = 0; j < offspring.roamLength(); j++) {
                    // all CityRoam objects initialized with 'null' values in
                    // each spot
                    if (offspring.getCity(j) == null) {
                        offspring.setCity(j, p2.getCity(i));
                        //System.out.println("p2 value placed");
                        break;
                    }
                }
            }
        }
        return offspring;
    }

    /**
     * Creates an offspring from two parents using ordered crossover
     *
     * @return CityRoam the offspring created by crossover
     * @param p1 the first parent used for creation
     * @param p2 the second parent used for creation
     */
    public static CityRoam oxCrossover(CityRoam p1, CityRoam p2) {
        CityRoam offspring = new CityRoam();

        int startPoint = (int) (Math.random() * p1.roamLength());
        int endPoint = (int) (Math.random() * p1.roamLength());

        for (int i = 0; i < offspring.roamLength(); i++) {
            if (startPoint < endPoint && i > startPoint && endPoint > i) {
                offspring.setCity(i, p1.getCity(i));
            } else if (startPoint > endPoint) {
                if (!(i < startPoint && endPoint > i)) {
                    offspring.setCity(i, p1.getCity(i));
                }
            }
        }

        for (int i = 0; i < p2.roamLength(); i++) {
            if (!offspring.containsCity(p2.getCity(i))) {
                for (int j = 0; j < offspring.roamLength(); j++) {
                    if (offspring.getCity(j) == null) {
                        offspring.setCity(j, p2.getCity(i));
                        break;
                    }
                }
            }
        }
        return offspring;
    }
}