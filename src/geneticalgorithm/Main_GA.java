package geneticalgorithm;

/**
 * @author Drayton Williams 
 * The purpose of this class is to perform a genetic algorithm on the TSP
 */
import java.util.*;

public class Main_GA {

    public static String file;      // tsp file
    public static double mRate;     // mutation rate
    public static double cRate;     // crosover rate
    public static int    popSize;   // population size
    public static int    maxGen;    // maximum generation span
    public static int    tSize;     // tournament selection size
    public static String cType;     // crossover type
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Possible file names - berlin52.txt -- dj38.txt
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter tsp file name: ");
        file = scanner.next();
        
        GeneticAlgorithm.cityLoader(file);
        
        System.out.print("Please enter mutation rate(%): ");
        mRate = scanner.nextDouble()/100;
        System.out.print("Please enter crossover rate(%): ");
        cRate = scanner.nextDouble()/100;
        System.out.print("Please specify crossover type (UOX or OX): ");
        cType = scanner.next();
        System.out.print("Please enter population size: ");
        popSize = scanner.nextInt();
        System.out.print("Please enter tournament selection size: ");
        tSize = scanner.nextInt();
        System.out.print("Please enter maximum generation span: ");
        maxGen = scanner.nextInt();
        
        GeneticAlgorithm.varSetup(mRate, cRate, tSize, cType);
        
        SetupPop population = new SetupPop(true, popSize);
        
        /*System.out.println(""+mRate+","+cRate+","+popSize+","+tSize+","+
                    maxGen+",");*/
        
        for(int i = 0; i < maxGen; i++) {
            population = GeneticAlgorithm.popEvolve(population);
            System.out.print("Generation: "+(i+1));
            System.out.print("\tBest Fitness: "+population.obtainFittest().getDist());
            System.out.print("\t\tAverage Fitness: " + population.getAvg());
            
/////////////// USED TO CREATE CSV FILES (FOR EXPERIMENTAL ANALYSIS)
            /*System.out.print(""+(i+1)+","+population.obtainFittest().getDist()+
                    ","+population.getAvg()+",");*/
            
            System.out.println();
        }
        System.out.println("Done Test");
        System.out.println("Final distance: "+population.obtainFittest().getDist());
        System.out.println("Solution--");
        System.out.println(population.obtainFittest());
    }
    
}