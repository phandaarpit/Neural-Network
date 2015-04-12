package neural.network.genetic.algo;

import java.util.ArrayList;

/**
 * Created by arpit on 12/4/15.
 */
public class GA_NeuronLayer {
    private int numberOfNeurons;

    public int getNumberOfNeurons() {
        return numberOfNeurons;
    }

    private ArrayList<GA_Neuron> neuronVector;

    public ArrayList<GA_Neuron> getNeuronVector() {
        return neuronVector;
    }


    public GA_NeuronLayer(int numberOfNeurons, int numberOfNeuronsInNextLayer) {

        this.numberOfNeurons = numberOfNeurons;
        this.neuronVector = new ArrayList<GA_Neuron>();

        int i;
        for( i=0; i<numberOfNeurons ; i++)
        {
            GA_Neuron neuron = new GA_Neuron(numberOfNeuronsInNextLayer, i);
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
