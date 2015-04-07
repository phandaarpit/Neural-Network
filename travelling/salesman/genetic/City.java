package travelling.salesman.genetic;

public class City {

    public double XCoordinate;
    public double YCoordinate;

    public City(double XCoordinate, double YCoordinate)
    {
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
    }

    public double distanceFromCity(City city)
    {
        double diffX = Math.abs(this.XCoordinate-city.XCoordinate);
        double diffY = Math.abs(this.YCoordinate-city.YCoordinate);
        double distance = Math.sqrt(Math.pow(diffX,2)+Math.pow(diffY,2));
        return distance;
    }

    @Override
    public String toString()
    {
        return this.XCoordinate+","+this.YCoordinate;
    }
}

