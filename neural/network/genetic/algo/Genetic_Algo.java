package neural.network.genetic.algo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
* Created by arpit on 11/4/15.
*/
public class Genetic_Algo {

    private static final double mutationRate = 0.015;
    private static final int sampleSize = 5;

    public static WeightPopulation evolvePopulation(WeightPopulation p) {
        WeightPopulation newPopulation = new WeightPopulation(false,p.population.size());

        newPopulation.population.set(0, p.getFittest());

        Collections.sort(p.population);

        int targetSize = p.population.size();

        boolean done = false;
        int curr = 1;

        for(int i=0; i<5; i++)
        {
            WeightChromo parent_first = p.population.get(i);
            for(int j=i+1; j<i+11;j++) {

                WeightChromo parent_second = p.population.get(j);
                WeightChromo child = crossover(parent_first, parent_second);
                newPopulation.population.set(curr, child);
                curr+=1;

                if(curr==targetSize) {
                    done = true;
                    break;
                }
            }
            if(done)
            {
                break;
            }
        }

        for (int i = 1; i < newPopulation.population.size(); i++) {
            mutate(newPopulation.population.get(i));
        }
        return newPopulation;
    }

    public static WeightChromo crossover(WeightChromo parent1, WeightChromo parent2) {
        WeightChromo child = new WeightChromo();

        int startPos = (int) (Math.random() * parent1.getWeightArray().size());
        int endPos = (int) (Math.random() * parent1.getWeightArray().size());

        for (int i = 0; i < child.getWeightArray().size(); i++) {
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setGene(i, parent1.getWeightArray().get(i));
            }
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setGene(i, parent1.getWeightArray().get(i));
                }
            }
        }

        for (int i = 0; i < parent2.getWeightArray().size(); i++) {
            for (int j = 0; j < child.getWeightArray().size(); j++) {
                if (child.getWeightArray().get(j) == null) {
                    child.setGene(j, parent2.getWeightArray().get(i));
                    break;
                }
            }
        }
        return child;
    }

    private static void mutate(WeightChromo chromo) {
        for(int tourPos1=0; tourPos1 < chromo.getWeightArray().size(); tourPos1++){
            if(Math.random() < mutationRate){
                int tourPos2 = (int) (chromo.getWeightArray().size() * Math.random());
                double v1 = chromo.getWeightArray().get(tourPos1);
                double v2 = chromo.getWeightArray().get(tourPos2);

                chromo.setGene(tourPos2, v1);
                chromo.setGene(tourPos1, v2);
            }
        }
    }
}
