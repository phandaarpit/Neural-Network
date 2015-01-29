import java.util.ArrayList;

public class Neuron {

	//each neuron will be fed by all the neurons from previous layer+bias
	private ArrayList<Double> inputs;
	
	//will generate a single output which will be fed to each neuron in forward direction
	private double output;
	
	//arrayList to store the weights of the outputs to neurons in next layer
	private ArrayList<Double> weightsForOutputs;

	private ArrayList<Double> changeInWeights;
	
	public Neuron(ArrayList<Double> input)
	{
		this.inputs = new ArrayList<Double>();
		this.weightsForOutputs = new ArrayList<Double>();
		this.changeInWeights = new ArrayList<Double>();
		
		this.inputs = input;
	}
	
	public void activationFunction()
	{
		
	}
	
}
