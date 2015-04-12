package neural.network.genetic.algo;

/**
* Created by arpit on 11/4/15.
*/
public class Genetic_Algo {

    private static final double mutationRate = 0.015;
    private static final int sampleSize = 5;

    public static WeightPopulation evolvePopulation(WeightPopulation p) {
        WeightPopulation newPopulation = new WeightPopulation(false,p.population.size());

        int elitismOffset = 1;
        newPopulation.population.set(0, p.getFittest());

        for (int i = elitismOffset; i < newPopulation.population.size(); i++) {
            WeightChromo parent_first = selection(p);
            WeightChromo parent_second = selection(p);

            WeightChromo child = crossover(parent_first, parent_second);
            newPopulation.population.set(i, child);
        }

        for (int i = elitismOffset; i < newPopulation.population.size(); i++) {
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

    private static WeightChromo selection(WeightPopulation pop) {
        WeightPopulation sampleSpace = new WeightPopulation(false,sampleSize);

        for (int i = 0; i < sampleSize; i++) {
            int randomId = (int) (Math.random() * pop.population.size());
            sampleSpace.population.set(i, pop.population.get(randomId));
        }

        WeightChromo fittest = sampleSpace.getFittest();

        return fittest;
    }
}
