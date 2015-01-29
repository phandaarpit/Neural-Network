import java.util.ArrayList;

/**
 *During training feedForward and backtraining will be run in a loop
 *until they meet the termination condition which can be either the number 
 *of desired loops or just the percentage error rate (Desired error rate is reached or less
 *than that)
 **/
public class NeuralNet {

	private int numberOfHiddenLayers;
	private int numberOfLayers;
	
	//accessing particular neuron of a layer -> neuronLayer.get(index of layer).get(index of neuron)
	private ArrayList<NeuronLayer> neuronLayers;
	
	//constructor
	NeuralNet(ArrayList<Integer> structureOfNN)
	{
		System.out.println("Time to create a neural net");
		//instantiate neuronLayers to hold each layer; number of neurons in each layer are specified in the structure of NN passed in constructor
		this.neuronLayers = new ArrayList<NeuronLayer>();
		
		this.numberOfLayers = structureOfNN.size();
		System.out.println("number of neuron layers: "+numberOfLayers);
		
		this.numberOfHiddenLayers = structureOfNN.size()-2;
		System.out.println("number of hidden layers: "+numberOfHiddenLayers);
		
		for(int i=0; i<numberOfLayers; i++)
		{
			System.out.println("Creating "+(i+1)+" neuron layer with "+(structureOfNN.get(i)+1)+" neurons");
			NeuronLayer layer = new NeuronLayer(structureOfNN.get(i)+1); //an extra input for bias
			neuronLayers.add(layer);
		}
	}

	//Takes inputs and train the feedForward Neural Network
	public void feedForwardNN(ArrayList<Double> inputs)
	{
		//we need to have initial weights too
		//after each iteration these "weights" will be modified <--- training the neural network
		//feed forward function just read the values from inputs and feeds them to input neurons
		
		
	}
	
	//Takes "target" Values as input since they will be used to find the error difference and that difference
	//will be propagated to adjust the weights again
	public void backPropagateNN(ArrayList<Double> desiredValues)
	{
		
	}

	//will return the results of the training
	public ArrayList<Double> getOutputResult()
	{
		ArrayList<Double> result = new ArrayList<Double>();
		return result;
	}
	
	
	//concept of epoch

}

