package travelling.salesman.genetic;

import java.util.ArrayList;

/**
 * Created by arpit on 8/4/15.
 */
public class Population {

    public ArrayList<Route> routes;
    int sizeOfPopulation;

    public Population(int populationSize, boolean initialise, ArrayList<City>...allCities) {
        routes = new ArrayList<Route>();
        sizeOfPopulation = populationSize;

        if (initialise) {
            for (int i = 0; i < populationSize; i++) {
                Route route = new Route(allCities[0]);
                route.generateRoute(allCities[0]);
                routes.add(route);
            }
        }
    }

    public Route getFittest() {
        Route fittest = routes.get(0);

        for (int i = 1; i < sizeOfPopulation ; i++) {
            double candidateFitness = routes.get(i).getFitness();
            if (fittest.getFitness() <= candidateFitness) {
                fittest = routes.get(i);
            }
        }
        return fittest;
    }

}
