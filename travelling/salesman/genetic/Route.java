package travelling.salesman.genetic;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by arpit on 8/4/15.
 */
public class Route {

    private int numberOfCities ;
    private double distance = 0;
    private double fitness = 0;

    public ArrayList<City> routeCities;

    public Route(int numberOfCities)
    {
        this.numberOfCities = numberOfCities;

        routeCities = new ArrayList<City>();
        for(int i=0;i < numberOfCities; i++)
        {
            routeCities.add(null);
        }
    }

    public Route(ArrayList<City> route)
    {
        this.routeCities = route;
    }

    public double calculateDistance()
    {
        double m_distance=0;

        for(int i=0; i<numberOfCities; i++)
        {
            City nextCity;
            City city = routeCities.get(i);

            if((i+1)==numberOfCities)
                nextCity = routeCities.get(0);
            else
                nextCity = routeCities.get(i+1);

            m_distance = m_distance+city.distanceFromCity(nextCity);
        }
        distance = m_distance;
        System.out.println("Distance = "+distance);
        return distance;
    }


    public void generateRoute(ArrayList<City> allCities)
    {
        for(int i=0; i<allCities.size(); i++)
        {
            routeCities.set(i, allCities.get(i));
        }
        System.out.println("Before: "+routeCities);
        Collections.shuffle(routeCities);
        System.out.println("After: "+routeCities);
    }

    public double calculateFitness()
    {
        if(fitness==0)
        {
            fitness = 1.0/calculateDistance();
        }
        return fitness;
    }

    public ArrayList<City> getRoute()
    {
        return this.routeCities;
    }

    public double getDistance()
    {
        calculateDistance();
        return distance;
    }

    public double getFitness()
    {
        calculateFitness();
        return fitness;
    }

    public void setCity(int index, City city)
    {
        routeCities.set(index,city);
        fitness = 0;
        distance = 0;
    }

    @Override
    public String toString()
    {
        String str = "";
        for(City city: routeCities)
        {
            str = str+city+"->";
        }
        return  str;
    }
}
