import java.util.ArrayList;

public class Neuron {

	//each neuron will be fed by all the neurons from previous layer+bias
	private ArrayList<Double> inputs;
	
	//will generate a single output which will be fed to each neuron in forward direction
	private double output;
	
	//stores the index/position of neuron in the layer for computing and getting the fedd from the previous layer
	private int indexInLayer;
	
	//arrayList to store the weights of the outputs to neurons in next layer
	private ArrayList<Double> weightsForOutputs;

	private double gradient;
	
	public double getGradient() {
		return gradient;
	}

	public ArrayList<Double> getWeightsForOutputs() {
		return weightsForOutputs;
	}

	private ArrayList<Double> changeInWeights;
	
	//it does need to know number of neurons in next layer
	//just for implementation sake
	
	public Neuron(int numberOfOutputs, int indexInLayer)
	{
		//setting local variables
		this.indexInLayer = indexInLayer;
		
		//initialization
		this.inputs = new ArrayList<Double>();
		this.weightsForOutputs = new ArrayList<Double>();
		this.changeInWeights = new ArrayList<Double>();
		
		for(int i=0; i<numberOfOutputs; i++)
		{
			//initial weights have to be random
			weightsForOutputs.add(Math.random() - Math.random());
		}
		
	}
	
	//return the value of the sigmoid activation function
	public double activationFunction(double input)
	{
		return (1/(Math.exp(-input)+1));
	}
	
	public double activationFunctionDerivative(double input)
	{
		return (-1*Math.exp(-input))/(Math.pow((Math.exp(-input)+1), 2));
	}
	
	public void setOutputVal(double value)
	{
		this.output = value;
	}
	
	public double getOutputVal()
	{
		return this.output;
	}
	
	//doesnt modifies the value in prev layer
	public void feedForward(NeuronLayer layer)
	{
		double sum = 0.0; //variable to hold sum of all the inputs from previous layer
		
		//looping on previous layer neurons, fetching their outputs and summing them
		//how to get the weights?? 
		
		for (int i = 0; i<layer.getNeuronVector().size(); i++)
		{
			sum = sum + (layer.getNeuronVector().get(i).getOutputVal()*layer.getNeuronVector().get(i).getWeightsForOutputs().get(this.indexInLayer));
		}
		
		this.output = activationFunction(sum);
	}

	public void setGradient(Double targetVal) {
		double error = targetVal-output;
		this.gradient = error*activationFunctionDerivative(output);
	}

	public void setHiddenGradient(NeuronLayer nextLayer) {
		double sumOfErrorsInNextLayer = sumOfDerivative(nextLayer);
		this.gradient = sumOfErrorsInNextLayer*activationFunctionDerivative(output);
	}

	private double sumOfDerivative(NeuronLayer nextLayer) {
		double sum = 0;
		for(int i=0; i<nextLayer.getNeuronVector().size()-1; i++)//excluding the bias in next layer
		{
			 sum = sum + weightsForOutputs.get(i)*nextLayer.getNeuronVector().get(i).getGradient();
		}
		
		return sum;
	}
	
	public void updateWeights()
	{
		
	}
}
