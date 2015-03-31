package graph.generator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;

public class GenerateGraph extends ApplicationFrame{

	static DefaultCategoryDataset dataset;
	
	public GenerateGraph(String applicationTitle) {
		super(applicationTitle);
		
		JFreeChart errorChart = ChartFactory.createLineChart("Error Rate", "Training Set Number", "Error", setupData(),PlotOrientation.VERTICAL,true,true,false);
		ChartPanel panel = new ChartPanel(errorChart);
		panel.setPreferredSize(new Dimension(500,400));
		setContentPane(panel);
	}

	public static CategoryDataset setupData() {
		return dataset;
	}
	
	public static void addToSet(Double error, String neuron, int count )
	{
		if(dataset == null)
		{
			dataset = new DefaultCategoryDataset();
		}
		dataset.addValue(error, neuron, ""+count);
	}
	
	
}
