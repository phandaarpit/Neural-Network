package neural.network.genetic.algo;

import graph.generator.GenerateGraph;

import java.util.ArrayList;

/**
 * Created by arpit on 11/4/15.
 */
public class GA_Neuron {
    ArrayList<Double> weightsForOutputs;
    int indexInLayer;
    double input;
    double output;
    double error;

    public ArrayList<Double> getWeightsForOutputs() {
        return weightsForOutputs;
    }

    public GA_Neuron(int numberOfOutputs, int indexInLayer)
    {
        this.indexInLayer = indexInLayer;
        this.weightsForOutputs = new ArrayList<Double>();

        for(int i=0; i<numberOfOutputs; i++)
        {
            weightsForOutputs.add(Math.random());
        }
    }

    public double activationFunction(double input)
    {
        this.input = input;
        double activationValue = 1/(Math.exp(-1*input)+1);
        return activationValue;
    }

    public void setOutputVal(double value)
    {
        this.output = value;
    }

    public double getOutputVal()
    {
        return this.output;
    }

    public void feedForward(ArrayList<GA_Neuron> layer)
    {
        double sum = 0.0;
        int sizeOfPrevLayer = layer.size();

        for (int i = 0; i<sizeOfPrevLayer; i++)
        {
            sum = sum + (layer.get(i).getOutputVal()*layer.get(i).getWeightsForOutputs().get(this.indexInLayer));
        }

        this.output = activationFunction(sum);
    }

    public void setGradient(Double targetVal, int currentTestNumber) {
        this.error = targetVal-output;
        GenerateGraph.addToSet(error, "" + this.indexInLayer, currentTestNumber);
    }

    public String getInput() {
        return ""+this.input;
    }

    public void setError(double d) {
        this.error = d;
    }

    public double getError()
    {
        return this.error;
    }

    public void setWeightsForOutputs(ArrayList<Double> newWeights)
    {
        weightsForOutputs = newWeights;
    }

    @Override
    public String toString()
    {
        return "Input: "+this.input+" Output: "+this.output+" Error: "+this.error+"\n";
    }

}
