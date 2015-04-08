package travelling.salesman.genetic;

import java.util.ArrayList;

/**
 * Created by arpit on 8/4/15.
 */
public class GeneticAlgo {

    private static final double mutationRate = 0.015;
    private static final int sampleSize = 5;
    private static int numberOfCities;

    public static Population evolvePopulation(Population p,ArrayList<City> allCities) {
        Population newPopulation = new Population(p.routes.size(), false);
        numberOfCities= allCities.size();

        int elitismOffset = 0;

        newPopulation.routes.set(0, p.getFittest());

        elitismOffset = 1;

        for (int i = elitismOffset; i < newPopulation.routes.size(); i++) {
            Route parent_first = tournamentSelection(p);
            Route parent_second = tournamentSelection(p);

            Route child = crossover(parent_first, parent_second);
            newPopulation.routes.set(i, child);
        }

        for (int i = elitismOffset; i < newPopulation.routes.size(); i++) {
            mutate(newPopulation.routes.get(i));
        }
        return newPopulation;
    }

    public static Route crossover(Route parent1, Route parent2) {
        Route child = new Route(numberOfCities);

        int startPos = (int) (Math.random() * parent1.routeCities.size());
        int endPos = (int) (Math.random() * parent1.routeCities.size());

        for (int i = 0; i < child.routeCities.size(); i++) {
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setCity(i, parent1.routeCities.get(i));
            }
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setCity(i, parent1.routeCities.get(i));
                }
            }
        }

        for (int i = 0; i < parent2.routeCities.size(); i++) {
            if (!child.routeCities.contains(parent2.routeCities.get(i))) {
                for (int j = 0; j < child.routeCities.size(); j++) {
                    if (child.routeCities.get(j) == null) {
                        child.setCity(j, parent2.routeCities.get(i));
                        break;
                    }
                }
            }
        }
        return child;
    }

    private static void mutate(Route route) {
        for(int tourPos1=0; tourPos1 < route.routeCities.size(); tourPos1++){
            if(Math.random() < mutationRate){
                int tourPos2 = (int) (route.routeCities.size() * Math.random());
                City city1 = route.routeCities.get(tourPos1);
                City city2 = route.routeCities.get(tourPos2);
                route.setCity(tourPos2, city1);
                route.setCity(tourPos1, city2);
            }
        }
    }

    private static Route tournamentSelection(Population pop) {
        Population sampleSpace = new Population(sampleSize, false);
        for (int i = 0; i < sampleSize; i++) {
            int randomId = (int) (Math.random() * pop.routes.size());
            sampleSpace.routes.set(i, pop.routes.get(randomId));
        }
        Route fittest = sampleSpace.getFittest();
        return fittest;
    }
}