import java.util.ArrayList;

public class Neuron {

	//will generate a single output which will be fed to each neuron in forward direction
	private double output;
	
	//stores the index/position of neuron in the layer for computing and getting the fedd from the previous layer
	private int indexInLayer;
	
	//arrayList to store the weights of the outputs to neurons in next layer
	private ArrayList<Double> weightsForOutputs;

	private double gradient;
	
	private ArrayList<Double> changeInWeights;
	
	//learning rate decides the convergence of learning
	private double learningRate = 0.15;
	private double momentum = 0.5;
	
	
	public double getGradient() {
		return gradient;
	}
	
	public ArrayList<Double> getWeightsForOutputs() {
		return weightsForOutputs;
	}
	
	public ArrayList<Double> getDeltaWeights() {
		return changeInWeights;
	}
	
	//it does need to know number of neurons in next layer, for feeding purpose
	public Neuron(int numberOfOutputs, int indexInLayer)
	{
		//setting local variables
		this.indexInLayer = indexInLayer;
		System.out.println("\t\t++Index of Neuron in layer: "+indexInLayer);
		
		this.weightsForOutputs = new ArrayList<Double>();
		this.changeInWeights = new ArrayList<Double>();
		
		for(int i=0; i<numberOfOutputs; i++)
		{
			//initial weights have to be random
			weightsForOutputs.add(Math.random() - Math.random());
			//initial deltaweights set to 0.0
			changeInWeights.add(0.0);
		}
		
		System.out.println("\t\t++Weight Arraylist: "+weightsForOutputs);
		System.out.println("\t\t++DeltaWeights Arraylist: "+changeInWeights);
	}
	
	//return the value of the sigmoid activation function
	public double activationFunction(double input)
	{
		double activationValue = 1/(Math.exp(-1*input)+1);
		System.out.println("\t++Activation Function value: "+activationValue);
		return activationValue;
	}
	
	//derivative of activation value
	public double activationFunctionDerivative(double input)
	{
		double derivativeActivationVal = (-1*Math.exp(-1*input))/(Math.pow((Math.exp(-1*input)+1), 2));
		System.out.println("Derivative of activation value: "+derivativeActivationVal);
		return derivativeActivationVal;
	}
	
	//setter function
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
			System.out.println("-----------------------------------");
			System.out.println(layer.getNeuronVector().get(i).getOutputVal());
			System.out.println(layer.getNeuronVector().get(i).getWeightsForOutputs());
			System.out.println(this.indexInLayer);
			System.out.println(layer.getNeuronVector().get(i).getWeightsForOutputs().get(this.indexInLayer));
			System.out.println("-----------------------------------");
			sum = sum + (layer.getNeuronVector().get(i).getOutputVal()*layer.getNeuronVector().get(i).getWeightsForOutputs().get(this.indexInLayer));
		}
		System.out.println("\t++Sum = "+sum);
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
	
	public void updateWeights(NeuronLayer previousLayer)
	{
		int sizeOfPrevLayer = previousLayer.getNumberOfNeurons();
		for(int i = 0 ; i<sizeOfPrevLayer; i++)
		{
			Neuron neuron = previousLayer.getNeuronVector().get(i);
			double prevDeltaWeight = neuron.getDeltaWeights().get(indexInLayer);
			
			double newDeltaWeight = learningRate*neuron.getOutputVal()*getGradient() + momentum*prevDeltaWeight;
			
			neuron.changeInWeights.set(indexInLayer,newDeltaWeight);
			neuron.weightsForOutputs.set(indexInLayer, neuron.weightsForOutputs.get(indexInLayer)+newDeltaWeight);
		}
	}
}
