package graph.generator;

import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import travelling.salesman.genetic.City;

public class PlotCities extends ApplicationFrame{

    static XYSeriesCollection dataset;

    static int count = 1;

    public PlotCities(String applicationTitle) {
        super(applicationTitle);
        JFreeChart chart = ChartFactory.createScatterPlot("Coordinates", "X", "Y", setupData(), PlotOrientation.VERTICAL,true,true,false);
        NumberAxis domainAxis = (NumberAxis) chart.getXYPlot().getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        chartPanel.setVerticalAxisTrace(true);
        chartPanel.setHorizontalAxisTrace(true);
        setContentPane(chartPanel);
    }

    public static XYDataset setupData() {
        return dataset;
    }

    public static void addToSet(ArrayList<City> coordinates, String classOfData)
    {
        if(dataset == null)
        {
            dataset = new XYSeriesCollection();
        }

        XYSeries series = new XYSeries(classOfData);
        for (City city : coordinates) {
            series.add(city.XCoordinate,city.YCoordinate);
        }

        dataset.addSeries(series);
    }

}
