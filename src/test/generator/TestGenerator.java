package test.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Files;

public class TestGenerator {

	public void generateTest(int numberOfTests, int numberOfInputs, int numberOfOutput, String output, ArrayList<ArrayList<Double>> ranges, String nameOfFile) {

		if(ranges.size() != numberOfInputs)
		{
			System.out.println("Wrong configuration size ");
			return;
		}
		
		ArrayList<ArrayList<Double>> trainingSet = new ArrayList<ArrayList<Double>>();
		
		for(int i=0 ;i < numberOfTests; i++)
		{
			ArrayList<Double> test = new ArrayList<Double>();
			
			for (ArrayList<Double> range : ranges) {
				double start = range.get(0);
				double end = range.get(1);
				
				if(start >= end)
				{
					return;
				}
				
				double difference = end - start;
				double num = Math.random();
				double random = (num*1000)%difference+start;
				random = (double)Math.round(random*100)/100.0;
				test.add(random);
				
			}
			trainingSet.add(test);
		}
		
		writeToFile(trainingSet,nameOfFile,output);
	}

	private void writeToFile(ArrayList<ArrayList<Double>> trainingSet, String nameOfFile, String output) {
		try {
			Files.write(output+"\n", new File(nameOfFile), Charsets.UTF_8);
			Files.append(Joiner.on("\n").join(trainingSet), new File(nameOfFile), Charsets.UTF_8);
//			Files.write(Joiner.on("=>"+output+"\n").join(trainingSet), new File(nameOfFile), Charsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Writing failed");
		}
	}
}
