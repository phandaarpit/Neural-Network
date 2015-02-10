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
		System.out.println("\t\tNeural Net with Back-propagation");
		System.out.println("\t\t--------------------------------");
		
		//instantiate neuronLayers to hold each layer; number of neurons in each layer are specified in the structure of NN passed in constructor
		this.neuronLayers = new ArrayList<NeuronLayer>();		
		
		//number of layers is the size of the arraylist passed.
		//ex: [5,4,3] means 3 layers with 1 hidden layer
		this.numberOfLayers = structureOfNN.size();
		
		System.out.println("Size of the Neural Network: "+numberOfLayers);
		
		//number of hidden layers is the total layers i.e. the size of the array-2 layers( one for input and one for output) 
		this.numberOfHiddenLayers = structureOfNN.size()-2;
		System.out.println("number of hidden layers: "+numberOfHiddenLayers);
		System.out.println("---------------------------------===-------------------------------------------");
	
		NeuronLayer layer;
		
		//initialise each layer
		for(int i=0; i<numberOfLayers; i++)
		{
			System.out.println("--> Creating neuron layer number "+(i+1)+" with "+(structureOfNN.get(i)+1)+" neurons");
			
			//TODO: specify the reason of bias in PPT
			//if its not the output layer then we need to initialise a new neuron layer with an extra neuron, bias neuron.
			//also pass the next layer for weights 
			if(i < numberOfLayers-1)
			{
				 layer = new NeuronLayer(structureOfNN.get(i)+1,structureOfNN.get(i+1)+1); 
			}
			else
			{
				//since for output layer there are no neurons in next layer
				layer = new NeuronLayer(structureOfNN.get(i)+1,0); 
			}

			//add the layer to arraylist of neuron layers
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
		
		for(int i=0; i<inputs.size(); i++)
		{
			System.out.println("==> Input Layer: "+neuronLayers.get(0).getNeuronVector().get(i).getOutputVal());
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
		
		System.out.println("======== OUTPUT ==========");
		NeuronLayer out  = neuronLayers.get(numberOfLayers-1);
		for(int i = 0; i<out.getNeuronVector().size()-1;i++)
		{
			System.out.println(out.getNeuronVector().get(i).getOutputVal());
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
		System.out.println("Size of output layer: "+sizeOfOutputLayer);
		
		//iterate over output layer neurons and find the difference between the desired values and actual values
		for(int i=0; i<sizeOfOutputLayer-1; i++)
		{
			error = error+ Math.pow((desiredValues.get(i)-outputLayer.getNeuronVector().get(i).getOutputVal()),2) ;
		}
		
		double rms = Math.sqrt(error/(sizeOfOutputLayer-1));
		
		//output gradient
		for(int i=0; i<sizeOfOutputLayer-1; i++)
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
		for (int i = neuronLayers.size() - 1; i > 0; i--) {
	        NeuronLayer layer = neuronLayers.get(i);
	        NeuronLayer prevLayer = neuronLayers.get(i-1);

	        for (int j = 0; j < layer.getNeuronVector().size() - 1; j++) {
	            layer.getNeuronVector().get(j).updateWeights(prevLayer);
	        }
	    }
	}

	//will return the results of the training
	public ArrayList<Double> getOutputResult()
	{
		ArrayList<Double> result = new ArrayList<Double>();
		return result;
	}

}

