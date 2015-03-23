import java.util.ArrayList;


/**
 * @author Arpit Phanda
 */
public class NeuronLayer {
	
	private int numberOfNeurons;
	
	public int getNumberOfNeurons() {
		return numberOfNeurons;
	}

	private ArrayList<Neuron> neuronVector;

	public ArrayList<Neuron> getNeuronVector() {
		return neuronVector;
	}

	
	public NeuronLayer(int numberOfNeurons, int numberOfNeuronsInNextLayer) {
		
		this.numberOfNeurons = numberOfNeurons;
		this.neuronVector = new ArrayList<Neuron>();

		int i;
		for( i=0; i<numberOfNeurons ; i++)
		{
			Neuron neuron = new Neuron(numberOfNeuronsInNextLayer, i); 
			neuronVector.add(neuron);
		}
		neuronVector.get(i-1).setOutputVal(1.0);
	}
	
	@Override
	public String toString()
	{
		return neuronVector.toString();
	}
}



/**
  @Override
	public String toString()
	{
		String str = "";
		for(int i=0; i<this.numberOfNeurons; i++)
		{
			System.out.println("Neuron "+i);
			System.out.println("Weights: "+this.neuronVector.get(i).getWeightsForOutputs());
			System.out.println("Delta weights: "+this.neuronVector.get(i).getDeltaWeights());
			System.out.println("Input: "+this.neuronVector.get(i).getInput());
			System.out.println("Output: "+this.neuronVector.get(i).getOutputVal());
			System.out.println("------------------------------------------");
		}
		
		return str;
	}
 * **/
