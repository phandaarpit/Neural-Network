package graph.generator;


import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class GenerateScatterPlot extends ApplicationFrame{

	static XYSeriesCollection dataset;
	static int count = 1;
	public GenerateScatterPlot(String applicationTitle) {
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
	
	public static void addToSet(ArrayList<ArrayList<Double>> coordinates, String classOfData)
	{
		if(dataset == null)
		{
			dataset = new XYSeriesCollection();
		}
		
		XYSeries series = new XYSeries(classOfData);
		for (ArrayList<Double> arrayList : coordinates) {
			series.add(arrayList.get(0),arrayList.get(1));
		}
		
		dataset.addSeries(series);
	}

    public static void addToSetFor2DKmeans(HashMap<Double, ArrayList<ArrayList<Double>>> coordinates)
    {
        if(dataset == null)
        {
            dataset = new XYSeriesCollection();
        }

        for(double key: coordinates.keySet())
        {
            count = count+1;
            XYSeries series = new XYSeries(key+"");
            for(ArrayList<Double> list: coordinates.get(key))
            {
                series.add(list.get(0),list.get(1));
            }
            dataset.addSeries(series);
        }
    }

    public static void addToCurrent( ArrayList<ArrayList<Double>> data)
    {
        if(dataset == null)
        {
            dataset = new XYSeriesCollection();
        }

        XYSeries series = new XYSeries(count+"");
        count = count+1;
        for(ArrayList<Double> list: data)
        {
            series.add(list.get(0),list.get(1));
        }
        dataset.addSeries(series);
    }
}
