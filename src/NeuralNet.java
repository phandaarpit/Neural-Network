import java.util.ArrayList;

public class NeuralNet {

	private int numberOfHiddenLayers;
	private int numberOfLayers;
	
	private ArrayList<NeuronLayer> neuronLayers;
	
	public ArrayList<NeuronLayer> getNeuronLayers() {
		return neuronLayers;
	}

	NeuralNet(ArrayList<Integer> structureOfNN)
	{
		this.neuronLayers = new ArrayList<NeuronLayer>();		
		this.numberOfLayers = structureOfNN.size();
		this.numberOfHiddenLayers = structureOfNN.size()-2;
	
		NeuronLayer layer;
		
		for(int i=0; i<numberOfLayers; i++)
		{
			if(i < numberOfLayers-1)
			{
				 layer = new NeuronLayer(structureOfNN.get(i)+1,  structureOfNN.get(i+1)); 
			}
			else
			{
				layer = new NeuronLayer(structureOfNN.get(i)+1, 0); 
			}

			neuronLayers.add(layer);
		}
	}

	public void feedForwardNN(ArrayList<Double> inputs)
	{
		if(inputs.size() != neuronLayers.get(0).getNumberOfNeurons()-1)
		{
			System.out.println("There is some error");
		}
		
		for(int i=0; i<inputs.size(); i++)
		{
			neuronLayers.get(0).getNeuronVector().get(i).setOutputVal(inputs.get(i));
		}
		
		for(int i = 1; i<numberOfLayers; i++)
		{
			NeuronLayer layer = neuronLayers.get(i);
			NeuronLayer prevLayer = neuronLayers.get(i-1);
			
			int numberOfNeuronsInCurrentLayer = layer.getNumberOfNeurons();
			
			for(int j = 0; j<numberOfNeuronsInCurrentLayer-1; j++)
			{
				layer.getNeuronVector().get(j).feedForward(prevLayer);
			}
		}
	}
	
	public void backPropagateNN(ArrayList<Double> desiredValues, int currentTestNumber)
	{
		NeuronLayer outputLayer = neuronLayers.get(this.numberOfLayers-1);
		
		int sizeOfOutputLayer = outputLayer.getNeuronVector().size();

		for(int i=0; i<sizeOfOutputLayer-1; i++)
		{
			outputLayer.getNeuronVector().get(i).setGradient(desiredValues.get(i),currentTestNumber);
		}
		
		for(int i=neuronLayers.size()-2; i>0;i--)
		{
			NeuronLayer currentLayer =  neuronLayers.get(i);
			NeuronLayer nextLayer = neuronLayers.get(i+1);
			
			for(int j=0; j<currentLayer.getNeuronVector().size();j++)
			{
				currentLayer.getNeuronVector().get(j).setHiddenGradient(nextLayer);
			}
		}
		
		for (int i = neuronLayers.size() - 1; i > 0; i--)
		{
			NeuronLayer layer = neuronLayers.get(i);
	        NeuronLayer prevLayer = neuronLayers.get(i-1);

	        for (int j = 0; j < layer.getNeuronVector().size()-1; j++) {
	            layer.getNeuronVector().get(j).updateWeights(prevLayer);
	        }
	    }
	}

	public ArrayList<Double> getOutputResult()
	{
		ArrayList<Double> result = new ArrayList<Double>();
		return result;
	}

}

