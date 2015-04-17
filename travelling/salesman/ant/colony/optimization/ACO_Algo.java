package travelling.salesman.ant.colony.optimization;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by arpit on 17/4/15.
 */
public class ACO_Algo {

    private double initialTrail = 1.0;

    private double alpha = 1;
    private double beta = 5;
    private double evaporation = 0.5;

    private double Q = 500; //trail deposit

    private double probabilityOfSelection = 0.01;

    public int numberOfTowns;
    public int antsCount = 30;

    private double graph[][] = null;
    private double trails[][] = null;

    private ArrayList<Ant> ants = null;

    //new random
    private Random rand = new Random();
    private ArrayList<Double> probsOfTrails;

    private int currentIndex = 0;

    public ArrayList<Integer> bestTour;
    public double bestTourLength;

    ArrayList<City_ACO> list_cities;

    public ACO_Algo(ArrayList<City_ACO> list_cities) {
        numberOfTowns = list_cities.size();
        this.list_cities = list_cities;

        bestTour = new ArrayList<Integer>(numberOfTowns);
        ants = new ArrayList<Ant>(antsCount);
        probsOfTrails = new ArrayList<Double>(numberOfTowns);

    }

    public ArrayList<Integer> solve() {
        //initialize each trail with a constant value
        for (int i = 0; i < numberOfTowns; i++)
            for (int j = 0; j < numberOfTowns; j++)
                trails[i][j] = initialTrail;

        int runCount = 0;

        while (runCount < 2000) {
            setupAnts();
            moveAnts();
            updateTrails();
            updateBestTour();

            runCount++;
        }

        System.out.println("Best tour:");
        for (int i=0; i<numberOfTowns; i++)
        {
            System.out.println(list_cities.get(bestTour.get(i))+"->");
        }
        System.out.println(list_cities.get(0));
        return bestTour;
    }

    private void probTo(Ant ant) {
        int i = ant.tour.get(currentIndex);

        double denom = 0.0;
        for (int l = 0; l < numberOfTowns; l++)
            if (!ant.visited(l))
                denom += Math.pow(trails[i][l], alpha) * Math.pow(1.0 / graph[i][l], beta);


        for (int j = 0; j < numberOfTowns; j++) {
            if (ant.visited(j)) {
                probsOfTrails.set(j, 0.0);
            } else {
                double numerator = Math.pow(trails[i][j], alpha) * Math.pow(1.0 / graph[i][j], beta);
                probsOfTrails.set(j, numerator / denom);
            }
        }

    }

    private int selectNextTown(Ant ant) {
        if (rand.nextDouble() < probabilityOfSelection) {
            int t = rand.nextInt(numberOfTowns - currentIndex); // random town
            int j = -1;
            for (int i = 0; i < numberOfTowns; i++) {
                if (!ant.visited(i))
                    j++;
                if (j == t)
                    return i;
            }

        }
        probTo(ant);
        double r = rand.nextDouble();
        double tot = 0;
        for (int i = 0; i < numberOfTowns; i++) {
            tot += probsOfTrails.get(i);
            if (tot >= r)
                return i;
        }

        throw new RuntimeException("Not supposed to get here.");
    }

    private void updateTrails() {
        // evaporation
        for (int i = 0; i < numberOfTowns; i++)
            for (int j = 0; j < numberOfTowns; j++)
                trails[i][j] *= evaporation;

        for (Ant a : ants) {
            double contribution = Q / a.tourLength(list_cities);
            for (int i = 0; i < numberOfTowns - 1; i++) {
                trails[a.tour.get(i)][a.tour.get(i + 1)] += contribution;
            }
            trails[a.tour.get(numberOfTowns - 1)][a.tour.get(0)] += contribution;
        }
    }

    // Choose the next town for all ants
    private void moveAnts() {
        // each ant follows trails...
        while (currentIndex < numberOfTowns - 1) {
            for (Ant a : ants)
                a.visitTown(selectNextTown(a),currentIndex);
            currentIndex++;
        }
    }

    private void setupAnts() {
        currentIndex = -1;
        for (int i = 0; i < antsCount; i++) {
            ants.get(i).clear();
            ants.get(i).visitTown(rand.nextInt(numberOfTowns),currentIndex);
        }
        currentIndex++;

    }

    private void updateBestTour() {
        if (bestTour == null) {
            bestTour = ants.get(0).tour;
            bestTourLength = ants.get(0).tourLength(list_cities);
        }
        for (Ant a : ants) {
            if (a.tourLength(list_cities) < bestTourLength) {
                bestTourLength = a.tourLength(list_cities);
                bestTour = a.tour;
            }
        }
    }

}
