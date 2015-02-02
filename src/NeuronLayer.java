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

	//constructor; takes numberOfNeurons as input and initializes the neuronLayer 
	public NeuronLayer(int numberOfNeurons, int numberOfNeuronsInNextLayer) {
		
		this.numberOfNeurons = numberOfNeurons;
		this.neuronVector = new ArrayList<Neuron>();

		System.out.println("Inside neuron Layer");
		System.out.println("Number of neurons to create: "+this.numberOfNeurons);
		
		
		for(int i=0; i<numberOfNeurons ; i++)
		{
			//TODO: for now input to neuron is i itself, change it to proper input
			Neuron neuron = new Neuron(numberOfNeuronsInNextLayer, i); // i is the position in current layer
			System.out.println("added a neuron");
			neuronVector.add(neuron);
		}
		
	}
}
