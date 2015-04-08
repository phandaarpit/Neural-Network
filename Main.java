import graph.generator.GenerateGraph;
import graph.generator.GenerateScatterPlot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import graph.generator.PlotCities;
import org.jfree.ui.RefineryUtilities;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import test.generator.TestGenerator;
import travelling.salesman.genetic.City;
import travelling.salesman.genetic.GeneticAlgo;
import travelling.salesman.genetic.Population;


public class Main {

	public static void main(String[] args) {

		ArrayList<Integer> structureOfNN = new ArrayList<Integer>();
		ArrayList<ArrayList<Double>> targetValues = new ArrayList<ArrayList<Double>>();

		ArrayList<ArrayList<Double>> inputs = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> input = new ArrayList<Double>();
	
		int option = 8;

		String bufferForChoice;
		
		Scanner getInput = new Scanner(System.in);
		
		System.out.println("\t\t\t\tNeural Network");
		System.out.println("\t\t\t\t--------------\n");
		System.out.println("\tThis is a demonstration of Neural Networks with different Learning Algorithms");
		
		while(true)
		{
			
			System.out.println("\nChoose from the following options:");
			System.out.println("----------------------------------");
			System.out.println("1. Generate Training Data");
			System.out.println("2. Back-propagation Neural Network - Sigmoid");
			System.out.println("3. Radial Basis Function Neural Network");
			System.out.println("4. Neural Network Learning with genetic algorithm");
			System.out.println("5. Single Layer Perceptron");
			System.out.println("6. K-means one dimensional");
			System.out.println("7. K-means two dimensional");
            System.out.println("8. Travelling Salesman Problem using Genetic Algorithm");
			System.out.println("9. Exit");
			
			System.out.print("\nEnter your choice: \t");
			bufferForChoice = getInput.next();
			try
			{
				option = Integer.parseInt(bufferForChoice);
			}catch(Exception e)
			{
				System.out.println("It seems you entered a character");
			}
			switch(option)
			{
				case 1:	generateTrainingData(getInput);
						break;
				case 2: backpropagateNeuralNetwork(getInput);
						break;
				case 3:	rbfNeuralNetwork(getInput);
                        System.out.println("Work in progress!");
						break;
				case 4: System.out.println("Work in progress!");
						break;
				case 5: singleLayerPerceptron(getInput);
						break;
				case 6: oneD_Kmeans(getInput);
						break;
				case 7:	twoD_Kmeans(getInput);
						break;
                case 8: travelling_salesman_genetic(getInput);
                        break;
				case 9: System.exit(0);

				default: System.out.println("Enter a valid choice");
				
			}
			
		}
		
	}

    private static void travelling_salesman_genetic(Scanner getInput) {
        String fileName = "";
        System.out.print("Name of the File: \t");
        fileName = getInput.next();

        List<String> citiesCoordinates = null;
        try {
            citiesCoordinates = Files.readLines(new File("Cities.txt"),Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<City> list_cities = new ArrayList<City>();
        for(String coordinate: citiesCoordinates)
        {
            StringTokenizer token = new StringTokenizer(coordinate,",");
            double x = Double.parseDouble(token.nextToken());
            double y = Double.parseDouble(token.nextToken());

            list_cities.add(new City(x,y));
        }

        int totalCities = list_cities.size();

        PlotCities.addToSet(list_cities,"1");
        PlotCities scatter = new PlotCities("Scatter");
        scatter.pack();
        RefineryUtilities.centerFrameOnScreen(scatter);
        scatter.setVisible(true);

        Population pop = new Population(50, true, list_cities);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());
        pop = GeneticAlgo.evolvePopulation(pop,list_cities);

        for (int i = 0; i < 100; i++) {
            pop = GeneticAlgo.evolvePopulation(pop,list_cities);
        }

        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(pop.getFittest());

    }

    private static void rbfNeuralNetwork(Scanner getInput) {

        String fileName = "";
        System.out.print("File name: \t");
        fileName = getInput.next();

        List<String> entries = new ArrayList<String>();
        try {
            entries = Files.readLines(new File(fileName),Charsets.UTF_8);
        }catch(Exception e)
        {
            System.out.println("Couldn't find the file passed");
        }

        HashMap<Double,ArrayList<ArrayList<Double>>> completeData = new HashMap<Double, ArrayList<ArrayList<Double>>>();

        for(int j=0; j<entries.size(); j++)
        {
            ArrayList<Double> newData = new ArrayList<Double>();
            StringTokenizer readCSV = new StringTokenizer(entries.get(j),",");
            while(readCSV.hasMoreTokens())
            {
                String feed = readCSV.nextToken();
                newData.add(Double.parseDouble(feed));
            }
            double switcher = newData.get(2);
            if(completeData.get(switcher)==null)
            {
                completeData.put(switcher,new ArrayList<ArrayList<Double>>());
            }
            else
            {
                completeData.get(switcher).add(newData);
            }
        }

        GenerateScatterPlot.addToSetFor2DKmeans(completeData);

        displayGraph();

        int numberOfClusters = 15;
        int numberOfClasses = completeData.keySet().size();

        HashMap<Double,ArrayList<ArrayList<Double>>> centroids = new HashMap<Double, ArrayList<ArrayList<Double>>>();
        ArrayList<RBFNeuron> layerOfNeurons = new ArrayList<RBFNeuron>();

        for(Double key:completeData.keySet())
        {
            ArrayList<ArrayList<Double>> centroidsForClass = new ArrayList<ArrayList<Double>>(numberOfClusters);
            HashMap<Double,ArrayList<ArrayList<Double>>> coordinateSet = new HashMap<Double, ArrayList<ArrayList<Double>>>();// to store data as per cluster
            ArrayList<ArrayList<Double>> dataByClassFromComplete = completeData.get(key);

            for(int i=0; i<numberOfClusters; i++)
            {
                centroidsForClass.add(dataByClassFromComplete.get(0));
                coordinateSet.put((double) i, new ArrayList<ArrayList<Double>>());
            }

            int sizeOfListOfCoordinatesLeft = dataByClassFromComplete.size();

            for(int i=0; i<sizeOfListOfCoordinatesLeft; i++)
            {
                double min= Double.MAX_VALUE;
                double distance=0;
                int index = -1;
                for(int j=0; j<numberOfClusters; j++)
                {
                    distance = distanceBetweenCoordinates(dataByClassFromComplete.get(i),centroidsForClass.get(j));
                    if(distance<min)
                    {
                        min = distance;
                        index = j;
                    }
                }

                if(index!=-1)
                {
                    coordinateSet.get((double) index).add(dataByClassFromComplete.get(i));
                    ArrayList<Double> newCentroid = updateCentroidCoordinate(coordinateSet.get((double)index));
                    centroidsForClass.set(index,newCentroid);
                }
            }
            int numberOfSwitches = -1;

            while(numberOfSwitches!=0)
            {
                numberOfSwitches = 0;
                for(int i=0; i<numberOfClusters; i++)
                {

                    ArrayList<Double> currentCentroid = centroidsForClass.get(i);

                    for(int j=0; j<coordinateSet.get((double)i).size(); j++)
                    {
                        double distanceFromHome = distanceBetweenCoordinates(coordinateSet.get((double)i).get(j),currentCentroid);
                        double min = distanceFromHome;
                        int index = -1;
                        for(int k=0; k< numberOfClusters; k++)
                        {
                            if(i==k)
                            {
                                continue;
                            }
                            else
                            {
                                double distanceFromNeighbors = distanceBetweenCoordinates(coordinateSet.get((double)i).get(j),centroidsForClass.get(k));
                                if(distanceFromNeighbors < min)
                                {
                                    min = distanceFromNeighbors;
                                    index = k;
                                }
                            }
                        }
                        if(index!=-1)
                        {
                            coordinateSet.get((double)index).add(coordinateSet.get((double)i).get(j));
                            coordinateSet.get((double)i).remove(j);
                            j--;
                            numberOfSwitches+=1;
                        }
                    }
                }

                for(int i=0; i<numberOfClusters; i++)
                {
                    if(centroidsForClass.get(i).size()!=0)
                    {
                        ArrayList<Double> newCentroid = updateCentroidCoordinate(coordinateSet.get((double)i));
                        centroidsForClass.set(i,newCentroid);
                    }
                }
            }

            int clusterCount = 0;

            for(ArrayList<Double> cent: centroidsForClass)
            {
                if (coordinateSet.get((double) clusterCount).size()!=0)
                {
                    RBFNeuron neuron = new RBFNeuron(numberOfClasses);
                    neuron.setMean(cent);
                    neuron.setBetaVal(coordinateSet.get((double) clusterCount));
                    layerOfNeurons.add(neuron);
                }
                clusterCount += 1;
            }

            GenerateScatterPlot.addToCurrent(centroidsForClass);

        }

        RBFNeuron n = new RBFNeuron(numberOfClasses);
        n.setOutputVal(1);
        layerOfNeurons.add(n);

        ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
        for(int i=0; i<numberOfClasses; i++)
        {
            outputLayer.add(new Neuron(0,i));
        }

        int runNumber = 0;

        while(runNumber < 1000000)
        {
            int randomClass = (int)(Math.random()*1000)%numberOfClasses;
            randomClass+=1;

            int sizeOfSet = completeData.get((double)randomClass).size();
            int randomInSet = (int)(Math.random()*100000)%sizeOfSet;

            ArrayList<Double> coordinate = completeData.get((double)randomClass).get(randomInSet);

            for(int i=0; i<layerOfNeurons.size()-1; i++)
            {
                layerOfNeurons.get(i).rbfActivation(coordinate);
            }

            String binary =  Integer.toBinaryString(randomClass);

            while (binary.length()!=numberOfClasses)
            {
                binary = "0"+binary;
            }

            for(int i=0; i<outputLayer.size(); i++)
            {
                outputLayer.get(i).feedForward(layerOfNeurons);
                outputLayer.get(i).setGradient(Double.parseDouble("" +binary.charAt(i)),runNumber);
            }

            for(int i=0; i<outputLayer.size(); i++)
            {
                outputLayer.get(i).updateWeights(layerOfNeurons);
            }
            runNumber+=1;
        }

        GenerateGraph chart = new GenerateGraph("Error");
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }

    private static void twoD_Kmeans(Scanner getInput) {
        List<String> entries = new ArrayList<String>();
        try {
            entries = Files.readLines(new File("dataset.csv"),Charsets.UTF_8);
        }catch(Exception e)
        {
            System.out.println("Couldn't find the file passed");
        }

        HashMap<Double,ArrayList<ArrayList<Double>>> completeData = new HashMap<Double, ArrayList<ArrayList<Double>>>();

        for(int j=0; j<entries.size(); j++)
        {
            ArrayList<Double> newData = new ArrayList<Double>();
            StringTokenizer readCSV = new StringTokenizer(entries.get(j),",");
            while(readCSV.hasMoreTokens())
            {
                String feed = readCSV.nextToken();
                newData.add(Double.parseDouble(feed));
            }
            double switcher = newData.get(2);
            if(completeData.get(switcher)==null)
            {
                completeData.put(switcher,new ArrayList<ArrayList<Double>>());
            }
            else
            {
                completeData.get(switcher).add(newData);
            }
        }
        GenerateScatterPlot.addToSetFor2DKmeans(completeData);
        displayGraph();

        int numberOfClusters = 15;
        int numberOfClasses = completeData.keySet().size();

        HashMap<Double,ArrayList<ArrayList<Double>>> centroids = new HashMap<Double, ArrayList<ArrayList<Double>>>();

        for(Double key:completeData.keySet())
        {
            ArrayList<ArrayList<Double>> centroidsForClass = new ArrayList<ArrayList<Double>>(numberOfClusters);
            HashMap<Double,ArrayList<ArrayList<Double>>> coordinateSet = new HashMap<Double, ArrayList<ArrayList<Double>>>();// to store data as per cluster
            ArrayList<ArrayList<Double>> dataByClassFromComplete = completeData.get(key);

            for(int i=0; i<numberOfClusters; i++)
            {
                centroidsForClass.add(dataByClassFromComplete.get(0));
                coordinateSet.put((double) i, new ArrayList<ArrayList<Double>>());
            }
            System.out.println(coordinateSet);

            int sizeOfListOfCoordinatesLeft = dataByClassFromComplete.size();

            for(int i=0; i<sizeOfListOfCoordinatesLeft; i++)
            {
                double min= Double.MAX_VALUE;
                double distance=0;
                int index = -1;
                for(int j=0; j<numberOfClusters; j++)
                {
                    distance = distanceBetweenCoordinates(dataByClassFromComplete.get(0),centroidsForClass.get(j));
                    if(distance<min)
                    {
                        min = distance;
                        index = j;
                    }
                }

                if(index!=-1)
                {
                    coordinateSet.get((double) index).add(dataByClassFromComplete.get(0));
                    ArrayList<Double> newCentroid = updateCentroidCoordinate(coordinateSet.get((double)index));
                    centroidsForClass.set(index,newCentroid);
                }
                dataByClassFromComplete.remove(0);
            }
            int numberOfSwitches = -1;

            while(numberOfSwitches!=0)
            {
                numberOfSwitches = 0;
                for(int i=0; i<numberOfClusters; i++)
                {

                    ArrayList<Double> currentCentroid = centroidsForClass.get(i);

                    for(int j=0; j<coordinateSet.get((double)i).size(); j++)
                    {
                        double distanceFromHome = distanceBetweenCoordinates(coordinateSet.get((double)i).get(j),currentCentroid);
                        double min = distanceFromHome;
                        int index = -1;
                        for(int k=0; k< numberOfClusters; k++)
                        {
                            if(i==k)
                            {
                                continue;
                            }
                            else
                            {
                                double distanceFromNeighbors = distanceBetweenCoordinates(coordinateSet.get((double)i).get(j),centroidsForClass.get(k));
                                if(distanceFromNeighbors < min)
                                {
                                    min = distanceFromNeighbors;
                                    index = k;
                                }
                            }
                        }
                        if(index!=-1)
                        {
                            coordinateSet.get((double)index).add(coordinateSet.get((double)i).get(j));
                            coordinateSet.get((double)i).remove(j);
                            j--;
                            numberOfSwitches+=1;
                        }
                    }
                }

                for(int i=0; i<numberOfClusters; i++)
                {
                    if(centroidsForClass.get(i).size()!=0)
                    {
                        ArrayList<Double> newCentroid = updateCentroidCoordinate(coordinateSet.get((double)i));
                        centroidsForClass.set(i,newCentroid);
                    }
                }
            }
            GenerateScatterPlot.addToCurrent(centroidsForClass);
            centroids.put(key, centroidsForClass);
        }
        displayGraph();
        System.out.println("Centroids:"+centroids);
    }

    private static double distanceBetweenCoordinates(ArrayList<Double> point, ArrayList<Double> centroid)
    {
        double distance = Math.sqrt(Math.pow(point.get(0)-centroid.get(0),2)+Math.pow(point.get(1)-centroid.get(1),2));
        return distance;
    }

    private static ArrayList<Double> updateCentroidCoordinate(ArrayList<ArrayList<Double>> cluster)
    {
        double size = cluster.size();
        ArrayList<Double> centroid = new ArrayList<Double>();
        double sumX = 0;
        double sumY = 0;
        for(ArrayList<Double> point:cluster)
       {
           sumX = sumX + point.get(0);
           sumY = sumY + point.get(1);
       }

        centroid.add(sumX/size);
        centroid.add(sumY/size);

        return centroid;
    }
    private static void displayGraph()
    {
        GenerateScatterPlot scatter = new GenerateScatterPlot("Scatter");
        scatter.pack();
        RefineryUtilities.centerFrameOnScreen(scatter);
        scatter.setVisible(true);
    }
	private static void oneD_Kmeans(Scanner getInput) {
		ArrayList<Double> numberArray = new ArrayList<Double>();
		ArrayList<Double> centroids;
		ArrayList<ArrayList<Double>> setOfIntegers;
		String numbers = "";
		int numberOfCluster;
		int numberOfSwitches = -1;
		
		System.out.println("1 Dimensional Data: K Means");
		System.out.println("---------------------------\n");
		
		System.out.print("Enter numbers:\t");
		getInput.nextLine();
		numbers = getInput.nextLine();
		
		StringTokenizer getIntegers = new StringTokenizer(numbers);
		while(getIntegers.hasMoreTokens())
		{
			numberArray.add(Double.parseDouble(getIntegers.nextToken()));
		}
		
		System.out.print("Number of Clusters to form:\t");
		numberOfCluster = Integer.parseInt(getInput.next());
		
		centroids = new ArrayList<Double>();
		setOfIntegers = new ArrayList<ArrayList<Double>>(numberOfCluster);
		
		for(int i = 0; i<numberOfCluster; i++)
		{
			centroids.add(numberArray.get(0));
//			numberArray.remove(0);
			setOfIntegers.add(new ArrayList<Double>());
		}

        System.out.println(centroids);
        System.out.println(setOfIntegers);

        int numberOfPoints = numberArray.size();

        for(int i =0; i<numberOfPoints; i++)
        {
            double min = Double.MAX_VALUE;
            double diff = 0;
            int index = -1;

            for(int j=0; j<numberOfCluster;j++)
            {
                diff = distance(numberArray.get(0),centroids.get(j));
                if(diff<min)
                {
                    min = diff;
                    index = j;
                }

            }
            if(index!=-1) {
                setOfIntegers.get(index).add(numberArray.get(0));
                double newCentroid = updateCentroid(setOfIntegers.get(index));
                centroids.set(index,newCentroid);
            }
            numberArray.remove(0);
        }

        System.out.println("Set :" + setOfIntegers);
        System.out.println("Centroid: " + centroids);

        //now i need to scan each of the cluster and compare its distance from its centroid to other centroids
        while(numberOfSwitches!=0) {
            numberOfSwitches = 0;
            for (int i = 0; i < numberOfCluster; i++) {
                double currentSetCentroid = centroids.get(i);

                for(int j=0; j<setOfIntegers.get(i).size();j++)
                {
                    double distanceFromCurrentCentroid = distance(setOfIntegers.get(i).get(j),currentSetCentroid);
                    double min = distanceFromCurrentCentroid;
                    int index = -1;
                    for(int k=0; k<numberOfCluster;k++)
                    {
                        if(k==i)
                        {
                            continue;
                        }
                        else
                        {
                            double distanceFromOtherCentroid = distance(setOfIntegers.get(i).get(j), centroids.get(k));

                            if(distanceFromOtherCentroid<min)
                            {
                                min = distanceFromCurrentCentroid;
                                index = k;
                            }
                        }
                    }
                    if(index != -1)
                    {
                        setOfIntegers.get(index).add(setOfIntegers.get(i).get(j));
                        setOfIntegers.get(i).remove(j);
                        j--;
                        numberOfSwitches+=1;
                    }
                }
            }
            //update the centroids
            for(int i=0; i<numberOfCluster;i++)
            {
                if(setOfIntegers.get(i).size()!=0) {
                    double newCentroid = updateCentroid(setOfIntegers.get(i));
                    centroids.set(i, newCentroid);
                }
            }
        }

        System.out.println("\n\nDONE");
        System.out.println(centroids);
        System.out.println(setOfIntegers);
    }
    private static double updateCentroid(ArrayList<Double> set)
    {
        double sizeOfSet = set.size();
        double sum = 0;
        for(int i=0; i<sizeOfSet; i++)
        {
            sum =sum+set.get(i);
        }

        return sum/sizeOfSet;
    }

    private static double distance(double point, double centroid)
    {
        return Math.abs(Math.abs(point) - Math.abs(centroid));
    }

	private static void singleLayerPerceptron(Scanner getInput) {
		
		int numberOfInputs;
		int numberOfOutputs;
		String bufferForFileNames;
		int size = 0;
		ArrayList<ArrayList<Double>> trainingSets = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> targetValues = new ArrayList<ArrayList<Double>>();
		
		ArrayList<ArrayList<Neuron>> network = new ArrayList<ArrayList<Neuron>>();
		
		
		System.out.print("Number of inputs\t:");
		numberOfInputs = getInput.nextInt();
		
		System.out.print("Number Of outputs\t:");
		numberOfOutputs = getInput.nextInt();
		
		ArrayList<Neuron> layer =  new ArrayList<Neuron>(); //Neuron class has an activation function as below:
		
		for(int i=0 ;i<numberOfInputs+1; i++)
		{
			//neuron constructor takes numberOfOutputs and its index in current layer as input param
			 Neuron n = new Neuron(numberOfOutputs, i);
			 layer.add(n);
		}
		
		network.add(layer);
		
		layer= new ArrayList<Neuron>();
		
		for(int i =0;i < numberOfOutputs;i++)
		{
			Neuron n = new Neuron(0, i);
			layer.add(n);
		}
		
		network.add(layer);
		
		System.out.print("Enter the data files\t:");
		getInput.nextLine();
		bufferForFileNames = getInput.nextLine();
		System.out.println("buffer"+bufferForFileNames);
		StringTokenizer tokens = new StringTokenizer(bufferForFileNames,",");
		int count = 0;
		
		while(tokens.hasMoreElements())
		{
			List<String> entries = new ArrayList<String>();
			ArrayList<ArrayList<Double>> tempSetForPlot = new ArrayList<ArrayList<Double>>();
			
			try {
				entries = Files.readLines(new File(tokens.nextToken().trim()), Charsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ArrayList<Double> output = processOutput(entries.get(0));
			
			entries.remove(0);
			StringTokenizer tokenizeEntries;
			size = size+entries.size();
			for (String entry : entries) {
				ArrayList<Double> trainingSet = new ArrayList<Double>();
				tokenizeEntries = new StringTokenizer(entry.replace("[", "").replace("]", ""),",");
				
				while(tokenizeEntries.hasMoreElements())
				{
					trainingSet.add(Double.parseDouble(tokenizeEntries.nextToken().trim()));
				}
				
				tempSetForPlot.add(trainingSet);
				trainingSets.add(trainingSet);
				targetValues.add(output);
			}
			
			GenerateScatterPlot.addToSet(tempSetForPlot, "class"+count);
			count += 1;
		}
		
		GenerateScatterPlot scatter = new GenerateScatterPlot("Scatter");
		scatter.pack();
		RefineryUtilities.centerFrameOnScreen(scatter);
		scatter.setVisible(true);
		
		int numberOfTests = 10000;
		
		for(int i =0;i<numberOfTests; i++)
		{
			int random = (int)(Math.random()*1000)%size;
			ArrayList<Double> input = trainingSets.get(random);
			ArrayList<Double> output = targetValues.get(random);
			System.out.println(i+""+input+"=>"+output);
			
			for(int j=0; j<numberOfInputs; j++)
			{
				network.get(0).get(j).setOutputVal(input.get(j));
			}
			
			network.get(0).get(numberOfInputs).setOutputVal(-1*1.0);
			
			for(int j=0; j<numberOfOutputs;j++)
			{
				System.out.println("in neuron: "+j);
				double sum = 0.0;
				
				for(int k=0; k<numberOfInputs+1;k++)
				{
					sum = sum + network.get(0).get(k).getWeightsForOutputs().get(j)*network.get(0).get(k).getOutputVal();
				}
				
				double activationValue = network.get(1).get(j).stepwiseActivation(sum);
				double error = output.get(j) -activationValue;
				
				System.out.println("Error: "+error);
				if(i%100==0)
				{
					GenerateGraph.addToSet(error, ""+j, i);
				}
				
				for(int k=0; k<numberOfInputs+1; k++)
				{
					double oldWeight = network.get(0).get(k).getWeightsForOutputs().get(j);
					double newWeight = oldWeight + 0.15*error*network.get(0).get(k).getOutputVal();
					System.out.println("Weight Change = "+oldWeight+"=>"+newWeight);
					network.get(0).get(k).getWeightsForOutputs().set(j, newWeight);
				}
			}
			
		}
		GenerateGraph chart = new GenerateGraph("Error");
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	      
	}

	private static void backpropagateNeuralNetwork(Scanner getInput) {
		int numberOfInputs;
		int numberOfOutput;
		int numberOfHiddenLayers;
		String bufferForFileNames;
		ArrayList<String> trainingDataFiles = new ArrayList<String>();
		
		ArrayList<Integer> structure = new ArrayList<Integer>();

		System.out.println("\nBack-propagation Neural Network: ");
		System.out.println("--------------------------------\n");
		
		System.out.print("Number Of inputs\t\t:");
		numberOfInputs = Integer.parseInt(getInput.next());
		
		System.out.print("Number of Outputs\t\t:");
		numberOfOutput = Integer.parseInt(getInput.next());
		
		System.out.print("Number Of hidden layers\t\t:");
		numberOfHiddenLayers = Integer.parseInt(getInput.next());
		
		structure.add(numberOfInputs);
		
		for(int i=0; i<numberOfHiddenLayers; i++)
		{
			System.out.print("\tNumber of Neurons in hidden layer "+i+":");
			structure.add(Integer.parseInt(getInput.next()));
		}
		
		structure.add(numberOfOutput);
		
		System.out.print("Enter Training Data Files[Separated by comma]\t:");
		getInput.nextLine();
		bufferForFileNames = getInput.nextLine();

		executeNeuralNetwork(structure,bufferForFileNames,getInput);
	}

	private static void executeNeuralNetwork(ArrayList<Integer> structure, String bufferForFileNames, Scanner getInput) {
		
		ArrayList<ArrayList<Double>> trainingSets = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> targetValues = new ArrayList<ArrayList<Double>>();
		NeuralNet net = new NeuralNet(structure);
		int size = 0;

		StringTokenizer tokens = new StringTokenizer(bufferForFileNames,",");
		int count = 0;
		
		while(tokens.hasMoreElements())
		{
			List<String> entries = new ArrayList<String>();
			ArrayList<ArrayList<Double>> tempSetForPlot = new ArrayList<ArrayList<Double>>();
			
			try {
				entries = Files.readLines(new File(tokens.nextToken().trim()), Charsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ArrayList<Double> output = processOutput(entries.get(0));
			
			entries.remove(0);
			StringTokenizer tokenizeEntries;
			size = size+entries.size();
			for (String entry : entries) {
				ArrayList<Double> trainingSet = new ArrayList<Double>();
				tokenizeEntries = new StringTokenizer(entry.replace("[", "").replace("]", ""),",");
				
				while(tokenizeEntries.hasMoreElements())
				{
					trainingSet.add(Double.parseDouble(tokenizeEntries.nextToken().trim()));
				}
				
				tempSetForPlot.add(trainingSet);
				trainingSets.add(trainingSet);
				targetValues.add(output);
			}
			
			GenerateScatterPlot.addToSet(tempSetForPlot, "class"+count);
			count += 1;
		}
		
		displayGraph();


//t1.txt,t2.txt,t3.txt,t4.txt,t5.txt,t6.txt
		for(int i = 0 ;i < 1000000; i++)
		{	
			int randNum = ((int)(Math.random()*10000000))%size;
			System.out.println(i+""+trainingSets.get(randNum) + "=>" + targetValues.get(randNum));
			net.feedForwardNN(trainingSets.get(randNum));
			net.backPropagateNN(targetValues.get(randNum),i);
		}
		
		GenerateGraph chart = new GenerateGraph("Error");
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	      
		testNN(net,getInput,structure.get(0));
	}
	
	private static void testNN(NeuralNet net, Scanner getInput, Integer numberOfInput) {
		System.out.println("\nTesting Neural Network");
		System.out.println("----------------------\n");
		
		String choice = "";
		System.out.println("You want to continue with testing? :\t");
		
		choice = getInput.next();
		
		ArrayList<Double> input;
		
		while(!choice.equalsIgnoreCase("n"))
		{
			getInput.nextLine();
			
			input = new ArrayList<Double>();
			for(int i = 0; i<numberOfInput; i++)
			{
				System.out.print("Enter input: \t");
				input.add(Double.parseDouble(getInput.next()));
			}
			System.out.println("Query: "+input);
			net.feedForwardNN(input);
			NeuronLayer outLayer = net.getNeuronLayers().get(net.getNeuronLayers().size()-1);
			int sizeOfLayer = outLayer.getNeuronVector().size();
			
			ArrayList<Double> o = new ArrayList<Double>();
			for(int j=0; j<sizeOfLayer-1; j++)
			{
				o.add(outLayer.getNeuronVector().get(j).getOutputVal());
			}
			System.out.println("Output: "+o);
			
			System.out.print("Do you want to continue??:\t");
			choice = getInput.next();
		}
	}

	private static ArrayList<Double> processOutput(String outputString) {
		ArrayList<Double> output = new ArrayList<Double>();
		for(int i = 0;i<outputString.length();i++)
		{
			output.add(Double.parseDouble(""+outputString.charAt(i)));
		}
		return output;
	}

	
	private static void generateTrainingData(Scanner getInput) {
		int numberOfTrainingSets;
		int numberOfInputs;
		int numberOfOutput;
		String output;
		String nameOfFile;
		
		ArrayList<ArrayList<Double>> ranges = new ArrayList<ArrayList<Double>>();
		
		TestGenerator gen = new TestGenerator();
		
		getInput = getInput.skip("\\s");
		
		System.out.print("Size of Training data \t\t:");
		numberOfTrainingSets = Integer.parseInt(getInput.next());
		
		System.out.print("Number of Inputs \t\t:");
		numberOfInputs = Integer.parseInt(getInput.next());
		
		System.out.print("Number of Outputs \t\t:");
		numberOfOutput = Integer.parseInt(getInput.next());
		
		System.out.print("Output for following Test data \t:");
		output = getInput.next();

		System.out.print("Name of the text file\t\t:");
		nameOfFile = getInput.next();
		
		if(output.length() != numberOfOutput)
		{
			System.out.println("Wrong output string!!");
			return;
		}
		
		for(int i =0; i<numberOfInputs; i++)
		{
			ArrayList<Double> range = new ArrayList<Double>();
			
			System.out.println("Range for Input "+i+" :");
			System.out.println("-------------------");
			System.out.print("\tRange Start: \t");
			range.add(Double.parseDouble(getInput.next()));
			
			System.out.print("\tRange End: \t");
			range.add(Double.parseDouble(getInput.next()));

			ranges.add(range);
		}

		TestGenerator generator = new TestGenerator();
		generator.generateTest(numberOfTrainingSets, numberOfInputs, numberOfOutput, output, ranges, nameOfFile);
	}

}
