import java.util.ArrayList;


public class Main {

	public static void main(String[] args) {

		//input will in the form of an array describing the structure of the neural net
		//[4,3,2,1] -> this tells we want 4 layers with first layer with 4 neuron and second hidden layer with 3 neurons and so on with 
		//output layer with 1 neuron 
		ArrayList<Integer> structureOfNN = new ArrayList<Integer>();
		ArrayList<Double> targetValues = new ArrayList<Double>();
		structureOfNN.add(3);
		structureOfNN.add(2);
		structureOfNN.add(1);
		
		NeuralNet net = new NeuralNet(structureOfNN);
		ArrayList<Double> inputs = new ArrayList<Double>();
		inputs.add(1.3);
		inputs.add(2.5);
		inputs.add(3.6);
		net.feedForwardNN(inputs);
	}

}
