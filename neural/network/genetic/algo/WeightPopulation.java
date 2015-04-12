package neural.network.genetic.algo;

import java.util.ArrayList;

/**
 * Created by arpit on 11/4/15.
 */
public class WeightPopulation {

    int sizeOfPopulation;
    public ArrayList<WeightChromo> population;

    public WeightPopulation(boolean ifInitialise,int sizeOfPopulation, Integer...sizeOfEachChromo)
    {
        this.sizeOfPopulation = sizeOfPopulation;
        population = new ArrayList<WeightChromo>(sizeOfPopulation);

        if(!ifInitialise)
        {
            for(int i=0; i<sizeOfPopulation; i++)
            {
                population.add(null);
            }
        }
        if(ifInitialise)
        {
            for(int i=0; i<sizeOfPopulation; i++)
            {
                WeightChromo new_chromo = new WeightChromo(sizeOfEachChromo[0]);
                population.add(new_chromo);
            }
        }
    }

    public WeightChromo getFittest()
    {
        WeightChromo fittest = population.get(0);
        double fittest_fitness = fittest.getFitness();
        int index = 0;

        for(int i=1; i<sizeOfPopulation; i++)
        {
            double fitness = population.get(i).getFitness();
            if(fitness > fittest_fitness)
            {
                fittest_fitness = fitness;
                index = i;
            }
        }
        return population.get(index);
    }
}
