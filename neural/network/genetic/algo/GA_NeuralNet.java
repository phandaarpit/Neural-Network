package neural.network.genetic.algo;

import java.util.ArrayList;

/**
 * Created by arpit on 12/4/15.
 */
public class GA_NeuralNet {

    private int numberOfHiddenLayers;
    private int numberOfLayers;

    private ArrayList<GA_NeuronLayer> neuronLayers;

    public ArrayList<GA_NeuronLayer> getNeuronLayers() {
        return neuronLayers;
    }

    public GA_NeuralNet(ArrayList<Integer> structureOfNN)
    {
        this.neuronLayers = new ArrayList<GA_NeuronLayer>();
        this.numberOfLayers = structureOfNN.size();
        this.numberOfHiddenLayers = structureOfNN.size()-2;

        GA_NeuronLayer layer;

        for(int i=0; i<numberOfLayers; i++)
        {
            if(i < numberOfLayers-1)
            {
                layer = new GA_NeuronLayer(structureOfNN.get(i)+1,  structureOfNN.get(i+1));
            }
            else
            {
                layer = new GA_NeuronLayer(structureOfNN.get(i)+1, 0);
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
            GA_NeuronLayer layer = neuronLayers.get(i);
            GA_NeuronLayer prevLayer = neuronLayers.get(i-1);

            int numberOfNeuronsInCurrentLayer = layer.getNumberOfNeurons();

            for(int j = 0; j<numberOfNeuronsInCurrentLayer-1; j++)
            {
                layer.getNeuronVector().get(j).feedForward(prevLayer.getNeuronVector());
            }
        }
    }

    public double getNetworkError(ArrayList<Double> targetValues)
    {
        GA_NeuronLayer lastLayer = neuronLayers.get(numberOfLayers-1);
        double error = 0;

        for(int i=0; i<lastLayer.getNeuronVector().size()-1;i++)
        {
            error = Math.abs(lastLayer.getNeuronVector().get(i).getError());
        }
        return error;
    }
}
