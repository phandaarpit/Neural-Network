import graph.generator.GenerateGraph;

import java.util.ArrayList;

public class Neuron {
	
	private final double learningRate = 0.15;
	private final double momentum = 0.9;
	
	private double output;
	private double input;
	private int indexInLayer;
	private double gradient;

	private ArrayList<Double> weightsForOutputs;
	private ArrayList<Double> changeInWeights;

	private double error;
	
	
	public double getGradient() {
		return gradient;
	}
	
	public ArrayList<Double> getWeightsForOutputs() {
		return weightsForOutputs;
	}
	
	public ArrayList<Double> getDeltaWeights() {
		return changeInWeights;
	}
	
	public Neuron(int numberOfOutputs, int indexInLayer)
	{
		this.indexInLayer = indexInLayer;
		
		this.weightsForOutputs = new ArrayList<Double>();
		this.changeInWeights = new ArrayList<Double>();
		
		for(int i=0; i<numberOfOutputs; i++)
		{
			weightsForOutputs.add(Math.random());
			changeInWeights.add(0.0);
		}
	}
	
	public double activationFunction(double input)
	{
		this.input = input;
		double activationValue = 1/(Math.exp(-1*input)+1);
		return activationValue;
	}
	
	public double activationFunctionDerivative(double input)
	{
		double derivativeActivationVal = activationFunction(input)*(1-activationFunction(input));
		return derivativeActivationVal;
	}
	
	public void setOutputVal(double value)
	{
		this.output = value;
	}
	
	public double getOutputVal()
	{
		return this.output;
	}
	
	public void feedForward(NeuronLayer layer)
	{
		double sum = 0.0;
		int sizeOfPrevLayer = layer.getNeuronVector().size();
		
		for (int i = 0; i<sizeOfPrevLayer; i++)
		{
			sum = sum + (layer.getNeuronVector().get(i).getOutputVal()*layer.getNeuronVector().get(i).getWeightsForOutputs().get(this.indexInLayer));
		}
		
		this.output = activationFunction(sum);
	}

	public void setGradient(Double targetVal, int currentTestNumber) {
		double error = targetVal-output;
		if(currentTestNumber%100==0)
		{
			GenerateGraph.addToSet(error, ""+this.indexInLayer, currentTestNumber);
		}	
		this.gradient = error*activationFunctionDerivative(this.input);
	}

	public void setHiddenGradient(NeuronLayer nextLayer) {
		double sumOfErrorsInNextLayer = sumOfDerivative(nextLayer);
		this.gradient = sumOfErrorsInNextLayer*activationFunctionDerivative(this.input);
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
			double prevDeltaWeight = neuron.getDeltaWeights().get(this.indexInLayer);

			double newDeltaWeight = learningRate*neuron.getOutputVal()*getGradient();

			neuron.changeInWeights.set(indexInLayer,newDeltaWeight);
			neuron.weightsForOutputs.set(indexInLayer, neuron.weightsForOutputs.get(indexInLayer)+newDeltaWeight);
		}
	}

	public String getInput() {
		return ""+this.input;
	}
	
	public int stepwiseActivation(double input)
	{
		if(input > 0)
		{
			return 1;
		}
		else
		{
		return 0;
		}
	}

	public void setError(double d) {
		this.error = d;
	}
	
	public double getError()
	{
		return this.error;
	}
	
	@Override
	public String toString()
	{
		return "Input: "+this.input+" Output: "+this.output+" Error: "+this.error+"\n";
	}
	
}
