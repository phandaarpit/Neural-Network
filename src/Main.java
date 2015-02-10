import java.util.ArrayList;


public class Main {

	public static void main(String[] args) {

		//input will in the form of an array describing the structure of the neural net
		//[4,3,2,1] -> this tells we want 4 layers with first layer with 4 neuron and second hidden layer with 3 neurons and so on with 
		//output layer with 1 neuron 
		ArrayList<Integer> structureOfNN = new ArrayList<Integer>();
		ArrayList<Double> targetValues = new ArrayList<Double>();
		targetValues.add(0.0);
		targetValues.add(1.0);
		targetValues.add(1.0);
		targetValues.add(0.0);
		
		ArrayList<ArrayList<Double>> inputs = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> input = new ArrayList<Double>();
		input.add(0.0);
		input.add(0.0);
		inputs.add(input);
		
		input = new ArrayList<Double>();
		input.add(0.0);
		input.add(1.0);
		inputs.add(input);
		
		input = new ArrayList<Double>();
		input.add(1.0);
		input.add(0.0);
		inputs.add(input);
		
		input = new ArrayList<Double>();
		input.add(1.0);
		input.add(1.0);
		inputs.add(input);
		
		structureOfNN.add(2); //input layer
		structureOfNN.add(3); //hidden layer
		structureOfNN.add(1); //output layer
	}

}
