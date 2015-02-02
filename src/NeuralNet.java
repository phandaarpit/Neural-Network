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
		
		NeuronLayer layer;
		
		for(int i=0; i<numberOfLayers; i++)
		{
			System.out.println("Creating "+(i+1)+" neuron layer with "+(structureOfNN.get(i)+1)+" neurons");
			if(i < numberOfLayers-1)
			{
				 layer = new NeuronLayer(structureOfNN.get(i)+1,structureOfNN.get(i+1)); //an extra input for bias
			}
			else
			{
				//since for output layer there are no neurons in next layer
				layer = new NeuronLayer(structureOfNN.get(i)+1,0); //an extra input for bias
			}
			neuronLayers.add(layer);
		}
	}

	//Takes inputs and train the feedForward Neural Network
	public void feedForwardNN(ArrayList<Double> inputs)
	{
		//we need to have initial weights too
		//after each iteration these "weights" will be modified <--- training the neural network
		//feed forward function just read the values from inputs and feeds them to input neurons
		System.out.println("Input size: "+inputs.size());
		System.out.println("First layer size: "+neuronLayers.get(0).getNumberOfNeurons());

		if(inputs.size() != neuronLayers.get(0).getNumberOfNeurons()-1)
		{
			System.out.println("There is some error");
		}
		
		//next step is to feed these "inputs" to "input neurons"
		//take the ith neurons in the first neuron layer and set its output value to the ith value of the input
		//since feedForward for the first layer is to just set the input to its output, thats it
		for(int i=0; i<inputs.size(); i++)
		{
			neuronLayers.get(0).getNeuronVector().get(i).setOutputVal(inputs.get(i));
		}
		
		//after the input layer has been fed forward its time to call feed forward on every neuron of each layer
		for(int i = 1; i<neuronLayers.size(); i++)
		{
			NeuronLayer layer = neuronLayers.get(i);
			NeuronLayer prevLayer = neuronLayers.get(i-1);
			int sizeOfLayer = layer.getNeuronVector().size();
			
			for(int j = 0; j<sizeOfLayer-1; j++)
			{
				//reference to previous layer 
				layer.getNeuronVector().get(j).feedForward(prevLayer);
			}
		}
	}
	
	//Takes "target" Values as input since they will be used to find the error difference and that difference
	//will be propagated to adjust the weights again
	public void backPropagateNN(ArrayList<Double> desiredValues)
	{
		//calculate the error rate
		//using the Root mean square error
		double error = 0;
		NeuronLayer outputLayer = neuronLayers.get(this.numberOfLayers-1);
		int sizeOfOutputLayer = outputLayer.getNeuronVector().size();
		//iterate over output layer neurons and find the difference between the desired values and actual values
		for(int i=0; i<sizeOfOutputLayer-1; i++)
		{
			error = error+ Math.pow((desiredValues.get(i)-outputLayer.getNeuronVector().get(i).getOutputVal()),2) ;
		}
		
		double rms = Math.sqrt(error/(sizeOfOutputLayer-1));
		
		//output gradient
		for(int i=0; i<sizeOfOutputLayer; i++)
		{
			outputLayer.getNeuronVector().get(i).setGradient(desiredValues.get(i));
		}
		
		//calculate difference in hidden layers
			//calculate gradient for hidden layer
		for(int i=neuronLayers.size()-2; i>0;i--)
		{
			NeuronLayer currentLayer =  neuronLayers.get(i);
			NeuronLayer nextLayer = neuronLayers.get(i+1);
			
			for(int j=0; j<currentLayer.getNeuronVector().size();j++)
			{
				currentLayer.getNeuronVector().get(j).setHiddenGradient(nextLayer);
			}
		}
		
		
		//update the weights from first layer to outputs
		
	}

	//will return the results of the training
	public ArrayList<Double> getOutputResult()
	{
		ArrayList<Double> result = new ArrayList<Double>();
		return result;
	}
	
	
	//concept of epoch

}

