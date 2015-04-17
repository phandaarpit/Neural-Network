package travelling.salesman.ant.colony.optimization;

import java.util.ArrayList;

/**
 * Created by arpit on 17/4/15.
 */
public class Ant {
    public ArrayList<Integer> tour;
    public ArrayList<Boolean> visitedOrNot;
    public int sizeOfTheTour;

    public Ant(int sizeOfTheTour)
    {
        this.sizeOfTheTour= sizeOfTheTour;
        tour = new ArrayList<Integer>(sizeOfTheTour);
        visitedOrNot = new ArrayList<Boolean>(sizeOfTheTour);
    }

    public void visitTown(int town, int currentIndex) {
        tour.set(currentIndex + 1,town);
        visitedOrNot.set(town,true);
    }

    public boolean visited(int i) {
        return visitedOrNot.get(i);
    }

    public double tourLength(ArrayList<City_ACO> cities) {
        //distance between the last destination and starting point
        double length = distance(cities.get(tour.get(cities.size() - 1)), cities.get(0));

        //rest of the distance
        for (int i = 0; i < sizeOfTheTour - 1; i++) {
            length += distance(cities.get(tour.get(i)),cities.get(tour.get(i+1)));
        }

        return length;
    }

    public void clear() {
        for (int i = 0; i < sizeOfTheTour; i++)
            visitedOrNot.set(i,false);
    }

    public double distance(City_ACO home,City_ACO dest)
    {
        double diffX = Math.abs(dest.x - home.x);
        double diffY = Math.abs(dest.y - home.y);
        double distance = Math.sqrt(Math.pow(diffX,2)+Math.pow(diffY,2));
        return distance;
    }
}
